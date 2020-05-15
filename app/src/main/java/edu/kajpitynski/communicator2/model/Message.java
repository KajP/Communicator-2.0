package edu.kajpitynski.communicator2.model;

public interface Message {
    long getId();

    long getConversationId();

    void setConversationId(long id);
    String getSenderName();
    String getContent();
}
