package edu.kajpitynski.communicator2.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ConversationDao {
    @Query("select * from conversationentity")
    LiveData<List<ConversationEntity>> getAllConversations();

    @Insert
    Single<Long> insertConversation(ConversationEntity conversation);

    @Query("delete from conversationentity")
    Completable deleteAllConversation();
}
