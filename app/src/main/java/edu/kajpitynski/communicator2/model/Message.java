package edu.kajpitynski.communicator2.model;

public interface Message {
    int getId();
    int getConversationId();
    String getSenderName();
    String getContent();
}
