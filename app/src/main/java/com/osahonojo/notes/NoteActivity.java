package com.osahonojo.notes;

import android.view.MenuItem;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private EditText noteTitle;
    private EditText noteContents;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        noteTitle = findViewById(R.id.note_title);
        noteContents = findViewById(R.id.note_contents);

        noteTitle.setHint("Title");

        id = getIntent().getIntExtra("id", 0);
        noteTitle.setText(MainActivity.noteDatabase.getNoteDao().getTitle(id));
        noteContents.setText(MainActivity.noteDatabase.getNoteDao().getContents(id));

        // enable back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (noteTitle.getText().toString().length() == 0 && noteContents.getText().toString().length() == 0) {
            return;
        }

        if (noteTitle.getText().toString().length() == 0 && noteContents.getText().toString().length() != 0) {
            noteTitle.setText("New note");
        }

        String dateTimeText = "";
        if (Build.VERSION.SDK_INT >= 26) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d uuuu h:m:s a");
            dateTimeText = localDateTime.format(formatter);
        }
        else {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM d yyyy hh:mm:ss a");
            dateTimeText = simpleDateFormat.format(date);
        }

        MainActivity
                .noteDatabase
                .getNoteDao()
                .save(id, noteTitle.getText().toString(), dateTimeText, noteContents.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}