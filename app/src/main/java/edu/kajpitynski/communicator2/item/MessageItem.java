package edu.kajpitynski.communicator2.item;

public class MessageItem {
    public final String user;
    public final String content;

    public MessageItem(String user, String content) {
        this.user = user;
        this.content = content;
    }


    @Override
    public String toString() {
        return content;
    }
}
