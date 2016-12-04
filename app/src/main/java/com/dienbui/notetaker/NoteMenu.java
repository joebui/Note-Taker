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

import java.util.ArrayList;

public class NoteMenu extends Activity {
    private GridView grid;
    private Database db;
    private ArrayList<Integer> ids; private ArrayList<String> t;
    private ArrayList<Integer> gIDs; private ArrayList<String> cont;
    private int p;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.note_menu);
        grid = (GridView) findViewById(R.id.note_list);

        // open database
        db = new Database(this);
        db.open();
        notes();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(NoteMenu.this, EditingNote.class);
                Bundle b = new Bundle();
                b.putInt("i", ids.get(p));
                b.putString("t", t.get(p));
                b.putInt("g", gIDs.get(p));
                b.putString("c", cont.get(p));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(1);
                return true;
            }
        });
    }

    // restart the activity
    @Override
    public void onRestart() {
        super.onRestart();
        notes();
    }

    @Override
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this)
                        .setTitle("Delete this note?")
                        .setItems(R.array.note_choice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteNote(ids.get(p));
                                startActivity(new Intent(NoteMenu.this, MainMenu.class));
                            }
                        }).create();
        }

        return null;
    }

    /* display all notes on the grid view */
    public void displayNotes(Cursor c) {
        ArrayList<String> d = new ArrayList<>();
        ids = new ArrayList<>(); t = new ArrayList<>();
        gIDs = new ArrayList<>(); cont = new ArrayList<>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ids.add(c.getInt(c.getColumnIndex("_id")));
            t.add(c.getString(c.getColumnIndex("title")));
            gIDs.add(c.getInt(c.getColumnIndex("groupID")));
            cont.add(c.getString(c.getColumnIndex("content")));
            d.add(c.getString(c.getColumnIndex("date")));
        }

        grid.setAdapter(new Custom2Adapter(this, R.layout.note_row, t, d));
    }

    public void notes() {
        Bundle bundle = getIntent().getExtras();
        try {
            // get input sent from previous activity
            int g = bundle.getInt("id");
            setTitle(bundle.getString("group"));
            displayNotes(db.getNoteByGroup(g));
        } catch (Exception e) {
            displayNotes(db.getAllNote());
        }
    }
}
