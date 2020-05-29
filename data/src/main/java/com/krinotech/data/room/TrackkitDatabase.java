package com.krinotech.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.krinotech.data.PostComment;
import com.krinotech.data.PostTitle;
import com.krinotech.data.Subreddit;
import com.krinotech.data.SubredditPost;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitRoomDatabase;

@Database(entities = {
        Subreddit.class, SubredditPost.class,
        PostComment.class, PostTitle.class},
        version = 10,
        exportSchema = false)
public abstract class TrackkitDatabase extends RoomDatabase implements TrackkitRoomDatabase {
    public static final String DATABASE_NAME = "Trackkit";

    public abstract TrackkitDao trackkitDao();

    public static TrackkitDatabase getRoomDatabase(Context context) {
        return Room
                .databaseBuilder(context, TrackkitDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
