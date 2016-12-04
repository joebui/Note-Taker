package com.dienbui.notetaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class Database {
    private static final String DB1 = "groups";
    private static final int DB1_V = 1;
    private static final String DB2 = "notes";
    private static final int DB2_V = 1;

    private NoteDB helper_1;
    private NoteDB helper_2;
    private SQLiteDatabase db_1;
    private SQLiteDatabase db_2;
    private Context context;

    public static class NoteDB extends SQLiteOpenHelper {
        public NoteDB(Context c, String n, SQLiteDatabase.CursorFactory cf, int version) {
            super(c, n, cf, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store group's data
            db.execSQL("CREATE TABLE groups ( " +
                    "_id integer PRIMARY KEY AUTOINCREMENT, " +
                    "name text NOT NULL );");
            db.execSQL("INSERT INTO groups(name) VALUES ( " +
                    "'Personal' );");
            db.execSQL("INSERT INTO groups(name) VALUES ( " +
                    "'Work' );");

            // create table to store note data
            db.execSQL("CREATE TABLE notes ( " +
                    "_id integer PRIMARY KEY AUTOINCREMENT, " +
                    "groupID integer REFERENCES groups(_id), " +
                    "title text NOT NULL, " +
                    "content text, " +
                    "date text );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("DROP TABLE IF EXISTS groups");
            onCreate(db);
        }
    }

    public Database (Context context) {
        this.context = context;
    }

    public Database open() {
        helper_1 = new NoteDB(context, DB1, null, DB1_V);
        helper_2 = new NoteDB(context, DB2, null, DB2_V);
        db_1 = helper_1.getWritableDatabase();
        db_2 = helper_2.getWritableDatabase();
        return this;
    }

    public void close() {
        helper_1.close();
        helper_2.close();
    }

    /********** GROUP QUERY **********/

    public Cursor getAllGroup() {
        return db_1.rawQuery("SELECT * FROM groups", null);
    }

    public long addGroup(String name) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        return db_1.insert(DB1, null, cv);
    }

    public int updateGroup(int id, String name) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        return db_1.update(DB1, cv, "_id LIKE ?", new String[] {String.valueOf(id)});
    }

    public boolean deleteGroup(int id) {
        return db_1.delete(DB1, "_id = " + id, null) > 0;
    }

    /********** NOTE QUERY **********/

    public Cursor getAllNote() {
        return db_2.rawQuery("SELECT * FROM notes", null);
    }

    public long addNote(int group, String title, String content) {
        ContentValues cv = new ContentValues();
        cv.put("groupID", group);
        cv.put("title", title);
        cv.put("content", content);
        Date d = new Date();
        cv.put("date", d.toLocaleString());

        return db_2.insert(DB2, null, cv);
    }

    public Cursor getNoteByGroup(int groupID) {
        return db_2.rawQuery("SELECT * FROM notes WHERE groupID = " + groupID, null);
    }

    public Cursor queryNote(String title) {
        return db_2.rawQuery("SELECT * FROM notes WHERE title LIKE \'%" + title + "%\'", null);
    }

    public int updateNote(int noteID, String title, int groupID, String content) {
        ContentValues cv = new ContentValues();
        cv.put("groupID", groupID);
        cv.put("title", title);
        cv.put("content", content);
        Date d = new Date();
        cv.put("date", d.toLocaleString());

        return db_2.update(DB2, cv, "_id LIKE ?", new String[] {String.valueOf(noteID)});
    }

    public boolean deleteNoteByGroup(int groupID) {
        return db_2.delete(DB2, "groupID = " + groupID, null) > 0;
    }

    public boolean deleteNote(int id) {
        return db_2.delete(DB2, "_id = " + id, null) > 0;
    }
}
