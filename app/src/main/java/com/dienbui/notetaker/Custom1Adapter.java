package com.dienbui.notetaker;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom1Adapter extends BaseAdapter {
    private int layout;
    private Context context;
    private ArrayList<String> groups;

    public Custom1Adapter(Context context, int layout, ArrayList<String> groups) {
        this.layout = layout;
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(layout, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.group_title);
        title.setText(groups.get(position));
        return convertView;
    }
}
