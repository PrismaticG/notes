package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private ArrayList<String> notes;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called");

        listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);
        notes = new ArrayList<>();

        loadNotes();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        findViewById(R.id.btnAddNote).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });

        //Confirmation
        listView.setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            String noteKey = notes.get(position).split(":")[0];

            new AlertDialog.Builder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                        sharedPreferences.edit().remove(noteKey).apply();
                        loadNotes();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
        adapter.notifyDataSetChanged();
    }

    private void loadNotes() {
        notes.clear();
        Map<String, ?> allNotes = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            notes.add(entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}
