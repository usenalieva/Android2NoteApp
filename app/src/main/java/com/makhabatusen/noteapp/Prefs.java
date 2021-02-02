package com.makhabatusen.noteapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void avatarUrl(String value) {
        preferences
                .edit()
                .putString("avatarUrl", value)
                .apply();
    }

    public String avatarUrl(){
        return preferences.getString("avatarUrl", "");
    }

    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }

    public void saveBoardStatus() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public void clearPrefs() {
        preferences
                .edit()
                .clear()
                .apply();
    }

    public boolean isSortedByTitle() {
        return preferences.getBoolean("sortedByTitle", false);
    }

    public void sortByTitle() {
        preferences.edit().putBoolean("sortedByTitle", true).apply();
    }

    public void notSortByTitle() {
        preferences.edit().putBoolean("sortedByTitle", false).apply();
    }

    public boolean isSortedByDate() {
        return preferences.getBoolean("sortedByTitle", false);
    }

    public void sortByDate() {
        preferences.edit().putBoolean("sortedByTitle", true).apply(); }
    public void notSortByDate() {
        preferences.edit().putBoolean("sortedByTitle", false).apply();
    }
}
