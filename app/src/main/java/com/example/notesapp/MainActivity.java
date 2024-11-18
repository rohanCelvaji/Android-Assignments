package com.example.notesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteFAB;
    private NotesAdapter notesAdapter;
    private ArrayList<String> notesList;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        addNoteFAB = findViewById(R.id.addNoteFAB);
        sharedPrefManager = new SharedPrefManager(this);

        // Load notes
        notesList = new ArrayList<>(sharedPrefManager.getNotes());

        // Setup RecyclerView
        notesAdapter = new NotesAdapter(
                notesList,
                (position, noteContent) -> showEditDialog(position, noteContent), // Edit listener
                (position, ignored) -> deleteNote(position)                      // Delete listener
        );
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(notesAdapter);

        // Add new note
        addNoteFAB.setOnClickListener(v -> showEditDialog(-1, null));
    }

    private void showEditDialog(int position, String existingNote) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null, false);
        EditText noteEditText = dialogView.findViewById(R.id.noteEditText);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        if (existingNote != null) {
            noteEditText.setText(existingNote);
        }

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            String note = noteEditText.getText().toString().trim();
            if (note.isEmpty()) {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (position == -1) {
                // Add new note
                notesList.add(note);
            } else {
                // Edit existing note
                notesList.set(position, note);
            }
            notesAdapter.notifyDataSetChanged();
            sharedPrefManager.saveNotes(notesList);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteNote(int position) {
        notesList.remove(position);
        notesAdapter.notifyDataSetChanged();
        sharedPrefManager.saveNotes(notesList);
    }
}
