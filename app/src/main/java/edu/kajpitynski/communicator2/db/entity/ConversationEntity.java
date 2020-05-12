package edu.kajpitynski.communicator2.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

import edu.kajpitynski.communicator2.model.Conversation;

@Entity
public class ConversationEntity implements Conversation {
    @PrimaryKey
    private int id;
    private String recipient;

    public ConversationEntity(int id, String recipient) {
        this.id = id;
        this.recipient = recipient;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }
}
