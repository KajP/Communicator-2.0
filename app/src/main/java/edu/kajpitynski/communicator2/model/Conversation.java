package edu.kajpitynski.communicator2.model;

public interface Conversation {
    long getId();

    String getRecipient();

    long getLastTime();

    void setLastTime(long lastTime);
}
