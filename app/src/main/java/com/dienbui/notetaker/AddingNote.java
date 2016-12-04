package com.dienbui.notetaker;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddingNote extends Activity {
    private int groupID;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_note);

        final Database db = new Database(this);
        db.open();

        // storing groups' info to array lists
        Cursor c = db.getAllGroup();
        final ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> groups = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ids.add(c.getInt(c.getColumnIndex("_id")));
            groups.add(c.getString(c.getColumnIndex("name")));
        }

        final EditText title = (EditText) findViewById(R.id.add_title);
        final EditText content = (EditText) findViewById(R.id.add_content);
        Button add = (Button) findViewById(R.id.add_note_button);
        add.setText("Add");

        // display all available groups in the dropdown choice
        final Spinner group = (Spinner) findViewById(R.id.group_choice);
        ArrayAdapter<String> spin = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, groups);
        group.setAdapter(spin);

        // action listener for the dropdown choice
        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupID = ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // action listener for button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String c = content.getText().toString();
                
                if (t.equals("") || t.equals(null)) {
                    Toast.makeText(AddingNote.this, "Enter note title to proceed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.addNote(groupID, t, c);
                    db.close();
                    Toast.makeText(AddingNote.this, "Note is added successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
