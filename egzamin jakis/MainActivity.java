package com.example.egz;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNote = findViewById(R.id.editTextNote);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        ListView listViewNotes = findViewById(R.id.listViewNotes);

        notes = new ArrayList<>();
        notes.add("Zakupy: chleb, masło, ser");
        notes.add("Do zrobienia: obiad, umyć podłogi");
        notes.add("Weekend: kino, spacer z psem");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listViewNotes.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = editTextNote.getText().toString().trim();
                if (!newNote.isEmpty()) {
                    notes.add(newNote);
                    adapter.notifyDataSetChanged();
                    editTextNote.setText("");
                }
            }
        });
    }
}
