package com.dienbui.notetaker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetController extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (byte i = 0; i < appWidgetIds.length; i++) {
            RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, AddingNote.class), 0);
            v.setOnClickPendingIntent(R.id.widget_add, pi);

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            v.setRemoteAdapter(appWidgetIds[i], R.id.widgetList, intent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], v);
        }
    }
}
