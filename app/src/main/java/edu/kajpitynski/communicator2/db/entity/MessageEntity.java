package edu.kajpitynski.communicator2.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.kajpitynski.communicator2.model.Message;

@Entity
public class MessageEntity implements Message {
    @PrimaryKey
    private int id;
    @ForeignKey(entity = ConversationEntity.class, parentColumns = "id",
            childColumns = "conversationId", onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private int conversationId;
    private String senderName;
    private String content;

    public MessageEntity(int id, int conversationId, String senderName, String content) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderName = senderName;
        this.content = content;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getContent() {
        return content;
    }
}
