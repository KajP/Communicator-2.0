package edu.kajpitynski.communicator2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.kajpitynski.communicator2.db.AppDatabase;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;

public class MessageRepository {
    private static String TAG = "MessageRepository";
    private static MessageRepository sInstance;

    private AppDatabase mDatabase;

    public static MessageRepository getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MessageRepository(database);
                }
            }
        }
        return sInstance;
    }

    private ArrayList<ConversationEntity> conversations;
    private MutableLiveData<ArrayList<ConversationEntity>> liveConversations;

    private MessageRepository(AppDatabase database) {
        mDatabase = database;

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

    public void addConversation(final ConversationEntity conversationEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.conversationDao().insertConversation(conversationEntity);
                Log.d(TAG, "Inserted");
            }
        }).start();
    }
}
