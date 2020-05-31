package com.krinotech.trackkit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.krinotech.data.Subreddit;
import com.krinotech.trackkit.view.MainActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class TrackkitWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Subreddit> subreddits, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trackkit_widget);

        setUpServiceIntent(views, context, appWidgetId);
        PendingIntent appPendingIntent;
        if(subreddits == null) {
            views.setTextViewText(R.id.widget_title, context.getString(R.string.no_tracking_subreddits));

            appPendingIntent = setUpPendingIntent(context,appWidgetId,  0);
        }
        else {
            views.setTextViewText(R.id.widget_title, context.getString(R.string.widget_subreddits_tracking_number, subreddits.size()));

            appPendingIntent = setUpPendingIntent(context, appWidgetId, 1);
        }

        views.setOnClickPendingIntent(R.id.view_tracking_subreddits, appPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent setUpPendingIntent(Context context, int appWidgetId, int tabStart) {
        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.putExtra(context.getString(R.string.WIDGET_TAB_TRACKING_EXTRA), tabStart);
        return PendingIntent.getActivity(context, appWidgetId, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static void setUpServiceIntent(RemoteViews views, Context context, int appWidgetId) {
        Intent serviceIntent = new Intent(context, TrackkitWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
        views.setEmptyView(R.id.widget_stack_view, R.id.widget_empty_view);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, null,  appWidgetId);
        }
    }

    public static void onUpdateSubreddits(Context context, AppWidgetManager appWidgetManager, List<Subreddit> subreddits, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, subreddits, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) { }

    @Override
    public void onDisabled(Context context) { }
}

