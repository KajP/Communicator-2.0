package edu.kajpitynski.communicator2.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;

@Dao
public interface ConversationDao {
    @Insert
    void insertConversation(ConversationEntity conversation);
}
