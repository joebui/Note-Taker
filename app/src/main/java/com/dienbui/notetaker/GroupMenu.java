package com.dienbui.notetaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupMenu extends Activity {
    private Database db;
    private GridView grid;
    private ArrayList<Integer> ids;
    private ArrayList<String> groups;
    private int p;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.group_menu);
        grid = (GridView) findViewById(R.id.group_list);
        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        db = new Database(this);
        db.open();
        displayGroups();

        // this method will be called when pressing an item instantly
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(GroupMenu.this, NoteMenu.class);
                // transfer data to the next activity
                Bundle bundle = new Bundle();
                bundle.putInt("id", ids.get(position));
                bundle.putString("group", groups.get(position));
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        // this method will be called when holding on an item for long
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;
                showDialog(1);  // display dialog
                return true;
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        displayGroups();
    }

    @Override
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.action)
                        .setItems(R.array.list_choices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent i = new Intent(GroupMenu.this, EditingGroup.class);
                                    Bundle b = new Bundle();
                                    b.putInt("id", ids.get(p));
                                    b.putString("name", groups.get(p));
                                    i.putExtras(b);
                                    startActivity(i);
                                } else {
                                    db.deleteNoteByGroup(ids.get(p));
                                    db.deleteGroup(ids.get(p));
                                    startActivity(new Intent(GroupMenu.this, MainMenu.class));
                                }
                            }
                        }).create();
        }

        return null;
    }

    /* display all groups in the grid */
    public void displayGroups() {
        groups = new ArrayList<>();
        ids = new ArrayList<>();
        Cursor c = db.getAllGroup();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ids.add(c.getInt(c.getColumnIndex("_id")));
            groups.add(c.getString(c.getColumnIndex("name")));
        }

        grid.setAdapter(new Custom1Adapter(this, R.layout.group_row, groups));
    }
}
