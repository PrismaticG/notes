package com.example.notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {
    private static final String TAG = "DeleteNoteActivity";
    private Spinner spinner;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> noteTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
        Log.d(TAG, "onCreate called");

        spinner = findViewById(R.id.spinner);
        Button btnDelete = findViewById(R.id.btnDelete);
        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);

        loadNoteTitles();

        // Spineris
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noteTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // ClickListener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedNote = (String) spinner.getSelectedItem();
                if (selectedNote != null) {
                    // Naikinti note
                    sharedPreferences.edit().remove(selectedNote).apply();
                    Toast.makeText(DeleteNoteActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                    loadNoteTitles();  // Refresh
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DeleteNoteActivity.this, "No note selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadNoteTitles() {
        Map<String, ?> allNotes = sharedPreferences.getAll();
        noteTitles = new ArrayList<>(allNotes.keySet());
    }
}
