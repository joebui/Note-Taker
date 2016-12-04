package com.dienbui.notetaker;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetFactory(this.getApplicationContext(), intent));
    }
}