package com.krinotech.trackkit;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.krinotech.data.Subreddit;
import com.krinotech.data.TrackkitRepository;
import com.krinotech.data.room.TrackkitDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TrackkitWidgetService extends RemoteViewsService {
    @Inject
    TrackkitRepository trackkitRepository;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        AndroidInjection.inject(this);
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent, trackkitRepository);
    }


    public static class StackRemoteViewsFactory implements RemoteViewsFactory {
        private List<Subreddit> subreddits = new ArrayList<>();
        private Context context;
        private int appWidgetId;
        private TrackkitRepository trackkitRepository;


        public StackRemoteViewsFactory(Context context, Intent intent, TrackkitRepository trackkitRepository) {
            this.context = context;
            this.appWidgetId =
                    intent.getIntExtra(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID);

            this.trackkitRepository = trackkitRepository;
        }
        public void onCreate() {
            LiveData<List<Subreddit>> subredditLiveData = trackkitRepository.getTrackingSubreddits();
            subredditLiveData.observeForever(new Observer<List<Subreddit>>() {
                @Override
                public void onChanged(List<Subreddit> subreddits) {
                    StackRemoteViewsFactory.this.subreddits = subreddits;
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] appWidgetIds =
                            appWidgetManager.getAppWidgetIds(new ComponentName(context, TrackkitWidgetProvider.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_stack_view);
                    TrackkitWidgetProvider.onUpdateSubreddits(context, appWidgetManager, subreddits, appWidgetIds);
                }
            });
        }
        public void onDestroy() {
            TrackkitDatabase trackkitDatabase = TrackkitDatabase.getRoomDatabase(context);
            trackkitDatabase.close();
        }

        public int getCount() {
            if(subreddits != null) {
                return subreddits.size();
            }
            return 0;
        }
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

            rv.setTextViewText(R.id.widget_item_text, subreddits.get(position).getTitle());

            return rv;
        }


        public RemoteViews getLoadingView() {
            return null;
        }
        public int getViewTypeCount() {
            return 1;
        }
        public long getItemId(int position) {
            return position;
        }
        public boolean hasStableIds() {
            return true;
        }
        public void onDataSetChanged() { }
    }
}