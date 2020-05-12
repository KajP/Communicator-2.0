package edu.kajpitynski.communicator2.db.relations;

import androidx.room.Relation;

import java.util.List;

import edu.kajpitynski.communicator2.model.Conversation;
import edu.kajpitynski.communicator2.model.Message;

public class ConversationWithMessages {
    public Conversation conversation;
    @Relation(parentColumn = "id", entityColumn = "conversationId")
    public List<Message> messages;
}
