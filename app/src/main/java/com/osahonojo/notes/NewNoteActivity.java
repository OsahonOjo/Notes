package com.osahonojo.notes;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewNoteActivity extends AppCompatActivity {
    private EditText noteTitle;
    private EditText noteContents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        noteTitle = findViewById(R.id.note_title);
        noteContents = findViewById(R.id.note_contents);

        noteTitle.setHint("Title");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noteTitle.getText().toString().length() == 0 && noteContents.getText().toString().length() == 0) {
            return;
        }

        if (noteTitle.getText().toString().length() == 0 && noteContents.getText().toString().length() != 0) {
            noteTitle.setText("New note");
        }

        String dateTimeText = "";
        if (VERSION.SDK_INT >= 26) {
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
                .create(noteTitle.getText().toString(), dateTimeText, noteContents.getText().toString());
    }

}
