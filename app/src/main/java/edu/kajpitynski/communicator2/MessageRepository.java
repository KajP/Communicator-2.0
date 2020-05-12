package edu.kajpitynski.communicator2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.entity.ConversationEntity;

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
        conversations.add(new ConversationEntity(1, 23, mess));

        liveConversations = new MutableLiveData<>();
        liveConversations.setValue(conversations);
    }

    public LiveData<ArrayList<ConversationEntity>> getConversations() {
        return liveConversations;
    }
}
