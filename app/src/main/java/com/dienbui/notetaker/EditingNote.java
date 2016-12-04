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

public class EditingNote extends Activity {
    private int selectedGroup;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_note);
        Bundle bundle = getIntent().getExtras();
        final int id = bundle.getInt("i"), gID = bundle.getInt("g");
        String t = bundle.getString("t"), c = bundle.getString("c");
        setTitle(t);

        final Database db = new Database(this);
        db.open();

        final EditText title = (EditText) findViewById(R.id.add_title);
        title.setText(t);
        final EditText content = (EditText) findViewById(R.id.add_content);
        content.setText(c);
        Button edit = (Button) findViewById(R.id.add_note_button);
        edit.setText("Edit");

        Cursor cursor = db.getAllGroup();
        final ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> groups = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ids.add(cursor.getInt(cursor.getColumnIndex("_id")));
            groups.add(cursor.getString(cursor.getColumnIndex("name")));
        }

        final Spinner group = (Spinner) findViewById(R.id.group_choice);
        ArrayAdapter<String> spin = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, groups);
        group.setAdapter(spin);
        for (byte i = 0; i < ids.size(); i++) {
            if (ids.get(i) == gID) {
                group.setSelection(i);
                break;
            }
        }

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroup = ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateNote(id, title.getText().toString(), selectedGroup,
                        content.getText().toString());
                db.close();
                Toast.makeText(EditingNote.this, "Note is edited successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
