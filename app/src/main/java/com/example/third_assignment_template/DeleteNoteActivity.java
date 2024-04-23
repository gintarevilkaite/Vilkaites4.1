package com.example.third_assignment_template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        Toolbar toolbar = findViewById(R.id.toolbar_delete_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinner_notes);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        loadNotesToSpinner();

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void loadNotesToSpinner() {
        Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());
        String[] notesArray = notesSet.toArray(new String[notesSet.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, notesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onDeleteNoteClick(View view) {
        String selectedNote = (String) spinner.getSelectedItem();

        if (selectedNote != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());
            notesSet.remove(selectedNote);
            editor.putStringSet("notes", notesSet);
            editor.apply();

            loadNotesToSpinner();
            Toast.makeText(this, "Note deleted: " + selectedNote, Toast.LENGTH_SHORT).show();
        }
    }
}