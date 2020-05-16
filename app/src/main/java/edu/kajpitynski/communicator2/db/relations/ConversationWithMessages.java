package edu.kajpitynski.communicator2.db.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.entity.MessageEntity;

public class ConversationWithMessages {
    @Embedded
    public ConversationEntity conversation;
    @Relation(parentColumn = "id", entityColumn = "conversationId")
    public List<MessageEntity> messages;
}
