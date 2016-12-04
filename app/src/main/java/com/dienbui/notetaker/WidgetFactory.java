package com.dienbui.notetaker;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();

    public WidgetFactory(Context c, Intent i) {
        context = c;

        Database db = new Database(context);
        db.open();
        Cursor cursor = db.getAllNote();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            title.add(cursor.getString(cursor.getColumnIndex("title")));
            date.add(cursor.getString(cursor.getColumnIndex("date")));
            Log.d("widget", "yes");
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_row);
        row.setTextViewText(R.id.widget_title, title.get(i));
        row.setTextViewText(R.id.widget_group, date.get(i));
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
