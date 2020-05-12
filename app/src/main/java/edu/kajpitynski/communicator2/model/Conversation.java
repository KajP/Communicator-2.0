package edu.kajpitynski.communicator2.model;

import java.util.List;

public interface Conversation {
    public int getId();
    public String getRecipient();
    public List<String> getMessages();
}
