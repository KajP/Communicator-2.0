package edu.kajpitynski.communicator2.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.kajpitynski.communicator2.db.dao.ConversationDao;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;

@Database(entities = {ConversationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "conversations-db";

    public abstract ConversationDao conversationDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
