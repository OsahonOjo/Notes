package com.osahonojo.notes;

import android.app.ActionBar;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView textView;
        public TextView dateTextView;
        public int id;
        public PopupWindow popupWindow;

        NotesViewHolder (View view) {
            super(view);
            linearLayout = view.findViewById(R.id.note_preview_row);
            textView = view.findViewById(R.id.note_preview_row_text);
            dateTextView = view.findViewById(R.id.note_preview_date);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id  = (Integer) linearLayout.getTag();
                    Intent intent = new Intent(view.getContext(), NoteActivity.class);
                    intent.putExtra("id", id);
                    view.getContext().startActivity(intent);
                }
            });

            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    LayoutInflater layoutInflater = (LayoutInflater) view
                                                    .getContext()
                                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popup = layoutInflater.inflate(R.layout.activity_main_popup_window, null);

                    TextView headerTextView = popup.findViewById(R.id.activity_main_popup_header);
                    TextView deleteTextView = popup.findViewById(R.id.activity_main_popup_delete);

                    headerTextView.setText(textView.getText());

                    popupWindow = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    deleteTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    return true;
                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.note_preview, parent, false);
        return new NotesViewHolder(view);
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