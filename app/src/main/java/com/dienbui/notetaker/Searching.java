package com.dienbui.notetaker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Searching extends Activity {
    private Database db;
    private ListView list;
    private ArrayList<Integer> ids; private ArrayList<String> t;
    private ArrayList<Integer> gIDs; private ArrayList<String> cont;
    private int p;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.search);
        list = (ListView) findViewById(R.id.note_list);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        db = new Database(this);
        db.open();

        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_search, null);
        SearchView search = (SearchView) customView.findViewById(R.id.action_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                displayNotes(db.queryNote(s));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                displayNotes(db.queryNote(s));
                return true;
            }
        });

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Searching.this, EditingNote.class);
                Bundle b = new Bundle();
                b.putInt("i", ids.get(position));
                b.putString("t", t.get(position));
                b.putInt("g", gIDs.get(position));
                b.putString("c", cont.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(1);
                return true;
            }
        });
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
                                startActivity(new Intent(Searching.this, MainMenu.class));
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

        list.setAdapter(new Custom2Adapter(this, R.layout.note_row, t, d));
    }
}
