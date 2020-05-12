package edu.kajpitynski.communicator2.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import edu.kajpitynski.communicator2.model.Conversation;

@Entity
public class ConversationEntity implements Conversation {
    @PrimaryKey
    private int id;
    private int recipient;
    private List<String> messages;

    public ConversationEntity(int id, int recipient, List<String> messages) {
        this.id = id;
        this.recipient = recipient;
        this.messages = messages;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getRecipient() {
        return null;
    }

    @Override
    public List<String> getMessages() {
        return null;
    }
}
