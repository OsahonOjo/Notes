package com.osahonojo.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static NoteDatabase noteDatabase;

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // normally, you'd do:
        // noteDatabase = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class, "Note-Database").build();
        // but to allow the database queries to run in the main thread, do
         noteDatabase = Room
                .databaseBuilder(getApplicationContext(), NoteDatabase.class, "Note-Database")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new NotesAdapter(MainActivity.this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewNoteActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        adapter.reload();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}