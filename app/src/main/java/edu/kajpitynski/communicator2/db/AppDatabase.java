package edu.kajpitynski.communicator2.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;

@Database(entities = {ConversationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
}
