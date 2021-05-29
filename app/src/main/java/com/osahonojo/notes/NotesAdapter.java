package com.osahonojo.notes;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView textView;
        public TextView dateTextView;
        public int id;
        //public PopupWindow popupWindow;

        NotesViewHolder (View view) {
            super(view);
            linearLayout = view.findViewById(R.id.note_preview_row);
            textView = view.findViewById(R.id.note_preview_row_text);
            dateTextView = view.findViewById(R.id.note_preview_date);
        }
    }

    Context context;
    private List<Note> notes = new ArrayList<>();

    public NotesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.note_preview, parent, false);
        final NotesViewHolder holder = new NotesViewHolder(view);
        // final keyword allows you to change the properties of an object
        // but does not allow you to reassign the variable of the object instance to another instance or object

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id  = (Integer) holder.linearLayout.getTag();
                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                intent.putExtra("id", id);
                view.getContext().startActivity(intent);
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final int id  = (Integer) holder.linearLayout.getTag();
                String title = MainActivity.noteDatabase.getNoteDao().getTitle(id);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title)
                        .setItems(R.array.activity_main_dialog, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                                            builder2.setTitle(R.string.confirmation_question)
                                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            MainActivity.noteDatabase.getNoteDao().delete(id);
                                                            reload();
                                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id){}
                                                    });
                                            builder2.create().show();
                                            break;

                                    case 1: Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                                            break;

                                    default:
                                        break;
                                }

                            }
                        });
                builder.create().show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.noteTitle);
        holder.dateTextView.setText(current.noteDateTime);
        holder.linearLayout.setTag(current.id);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload() {
        notes = MainActivity.noteDatabase.getNoteDao().getAllNotes();
        notifyDataSetChanged();
    }

}