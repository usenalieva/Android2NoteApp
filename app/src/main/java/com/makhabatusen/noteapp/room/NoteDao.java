package com.makhabatusen.noteapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.makhabatusen.noteapp.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY createdAt DESC")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void upDateItem(Note note);

    @Query("SELECT * FROM note ORDER BY title ASC")
    List<Note> sortByTitle();

    @Query("SELECT * FROM note ORDER BY createdAt ASC")
    List<Note> sortByDate();


}
