package edu.kajpitynski.communicator2.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import edu.kajpitynski.communicator2.db.entity.MessageEntity;
import io.reactivex.Completable;

@Dao
public interface MessageDao {
    @Insert
    Completable insertMessages(MessageEntity... messages);
}
