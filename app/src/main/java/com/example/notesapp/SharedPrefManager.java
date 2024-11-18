package com.example.notesapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SharedPrefManager {

    private static final String PREF_NAME = "NotesPref";
    private static final String KEY_NOTES = "notes";

    private final SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveNotes(List<String> notes) {
        sharedPreferences.edit().putString(KEY_NOTES, new Gson().toJson(notes)).apply();
    }

    public List<String> getNotes() {
        String notesJson = sharedPreferences.getString(KEY_NOTES, "[]");
        return new Gson().fromJson(notesJson, new TypeToken<List<String>>() {}.getType());
    }
}
