package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final List<String> notesList;
    private final NoteClickListener editClickListener;
    private final NoteClickListener deleteClickListener;

    public NotesAdapter(List<String> notesList, NoteClickListener editClickListener, NoteClickListener deleteClickListener) {
        this.notesList = notesList;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String note = notesList.get(position);
        holder.noteTextView.setText(note);

        // Edit button click
        holder.editButton.setOnClickListener(v -> editClickListener.onClick(position, note));

        // Delete button click
        holder.deleteButton.setOnClickListener(v -> deleteClickListener.onClick(position, null));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTextView;
        ImageView editButton;
        ImageView deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    interface NoteClickListener {
        void onClick(int position, String noteContent);
    }
}
