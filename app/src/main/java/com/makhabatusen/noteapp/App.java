package com.makhabatusen.noteapp;

import android.app.Application;
import androidx.room.Room;

import com.makhabatusen.noteapp.room.AppDataBase;

public class App extends Application {

    private static AppDataBase appDataBase;
    private static Prefs prefs;

    public static Prefs getPrefs() {
        return prefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appDataBase = Room.databaseBuilder(this,
                AppDataBase.class,
                "database")
                .allowMainThreadQueries()
                .build();
        prefs = new Prefs(this);
    }

    public static AppDataBase getAppDataBase() {
        return appDataBase;
    }
}

