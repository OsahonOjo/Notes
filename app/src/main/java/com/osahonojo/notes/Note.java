package com.osahonojo.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String noteTitle;

    @ColumnInfo(name = "datetime")
    public String noteDateTime;

    @ColumnInfo(name = "contents")
    public String noteContents;
}
