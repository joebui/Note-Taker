package com.dienbui.notetaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom2Adapter extends BaseAdapter {
    private int layout;
    private Context context;
    private ArrayList<String> title;
    private ArrayList<String> date;

    public Custom2Adapter(Context context, int layout, ArrayList<String> title,
                          ArrayList<String> date) {
        this.layout = layout;
        this.context = context;
        this.title = title;
        this.date = date;
    }

    @Override
    public int getCount() {
        return title.size();
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

        TextView t = (TextView) convertView.findViewById(R.id.note_title);
        TextView d = (TextView) convertView.findViewById(R.id.note_date);
        t.setText(title.get(position));
        d.setText("Last modified: " + date.get(position));

        return convertView;
    }
}
