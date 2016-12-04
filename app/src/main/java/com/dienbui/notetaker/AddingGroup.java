package com.dienbui.notetaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddingGroup extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_group);
        setTitle("New Group");

        final EditText newGroup = (EditText) findViewById(R.id.add_group_textbox);
        Button button = (Button) findViewById(R.id.add_group_button);
        button.setText("Create");
        final Database db = new Database(this);
        db.open();

        // add new group to the database
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newGroup.getText().toString();
                if (value.equals("") || value.equals(null)) {
                    Toast.makeText(AddingGroup.this, "Enter group title to proceed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.addGroup(value);
                    db.close();
                    Toast.makeText(AddingGroup.this, "Group is added successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
