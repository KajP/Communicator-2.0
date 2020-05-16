package edu.kajpitynski.communicator2.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ConversationDao {
    @Query("select * from conversationentity")
    LiveData<List<ConversationEntity>> getAllConversations();

    @Query("select * from conversationentity where id = :id")
    @Transaction
    LiveData<ConversationWithMessages> getConversationWithMessages(long id);

    @Insert
    Single<Long> insertConversation(ConversationEntity conversation);

    @Query("delete from conversationentity")
    Completable deleteAllConversation();
}
