package com.makhabatusen.noteapp.room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.makhabatusen.noteapp.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();


}
