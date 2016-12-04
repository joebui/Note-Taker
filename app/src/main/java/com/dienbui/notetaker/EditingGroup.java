package com.dienbui.notetaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditingGroup extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_group);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("name");
        final int id = bundle.getInt("id");

        final EditText newGroup = (EditText) findViewById(R.id.add_group_textbox);
        newGroup.setText(title);
        Button button = (Button) findViewById(R.id.add_group_button);
        button.setText("Edit");
        final Database db = new Database(this);
        db.open();

        // edit the group
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newGroup.getText().toString();
                if (value.equals("") || value.equals(null)) {
                    Toast.makeText(EditingGroup.this, "Enter group title to proceed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.updateGroup(id, newGroup.getText().toString());
                    db.close();
                    Toast.makeText(EditingGroup.this, "Group is updated successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
