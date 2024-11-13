package com.example.notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText edtName, edtContent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edtName = findViewById(R.id.edtName);
        edtContent = findViewById(R.id.edtContent);
        Button btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String content = edtContent.getText().toString().trim();

            if (name.isEmpty() || content.isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                sharedPreferences.edit().putString(name, content).apply();
                Toast.makeText(AddNoteActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
