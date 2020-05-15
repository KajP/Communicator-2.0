package edu.kajpitynski.communicator2.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.kajpitynski.communicator2.model.Message;

@Entity
public class MessageEntity implements Message {
    @PrimaryKey
    private long id;
    @ForeignKey(entity = ConversationEntity.class, parentColumns = "id",
            childColumns = "conversationId", onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private long conversationId;
    private String senderName;
    private String content;

    public MessageEntity(long id, long conversationId, String senderName, String content) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderName = senderName;
        this.content = content;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getConversationId() {
        return conversationId;
    }

    @Override
    public void setConversationId(long id) {
        conversationId = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getContent() {
        return content;
    }
}
