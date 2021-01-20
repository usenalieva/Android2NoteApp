package com.makhabatusen.noteapp;


import com.makhabatusen.noteapp.models.Note;

public interface OnItemClickListener {
    void onCLick(int pos, Note note);
    void onLongClick(int pos, Note note);

}
