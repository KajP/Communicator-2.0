package edu.kajpitynski.communicator2.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.kajpitynski.communicator2.model.Conversation;

@Entity
public class ConversationEntity implements Conversation {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String recipient;
    private long lastTime;

    public ConversationEntity(long id, String recipient) {
        this.id = id;
        this.recipient = recipient;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public long getLastTime() {
        return lastTime;
    }

    @Override
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}
