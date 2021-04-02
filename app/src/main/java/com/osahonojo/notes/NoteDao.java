package com.osahonojo.notes;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("INSERT INTO notes (title, datetime, contents) VALUES (:title, :dateTime, :contents)")
    void create(String title, String dateTime, String contents);

    @Query("UPDATE notes SET title = :title, datetime = :dateTime, contents = :contents WHERE id = :id")
    void save(int id, String title, String dateTime, String contents);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("SELECT title FROM notes WHERE id = :id")
    String getTitle(int id);

    @Query("SELECT contents FROM notes WHERE id = :id")
    String getContents(int id);

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);
}
