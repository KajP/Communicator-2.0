package edu.kajpitynski.communicator2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;

public class MessageRepository {
    private static MessageRepository sInstance;

    public static MessageRepository getInstance() {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MessageRepository();
                }
            }
        }
        return sInstance;
    }

    private ArrayList<ConversationEntity> conversations;
    private MutableLiveData<ArrayList<ConversationEntity>> liveConversations;

    private MessageRepository() {
        conversations = new ArrayList<>();
        ArrayList<String> mess = new ArrayList<>();
        mess.add("asdfsadf");
        List<ConversationWithMessages> conversation;
        conversations.add(new ConversationEntity(1, "Michael"));
        conversations.add(new ConversationEntity(2, "John"));

        liveConversations = new MutableLiveData<>();
        liveConversations.setValue(conversations);
    }

    public LiveData<ArrayList<ConversationEntity>> getConversations() {
        return liveConversations;
    }
}
