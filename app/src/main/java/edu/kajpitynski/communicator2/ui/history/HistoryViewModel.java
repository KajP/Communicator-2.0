package edu.kajpitynski.communicator2.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.kajpitynski.communicator2.entity.ConversationEntity;
import edu.kajpitynski.communicator2.model.Conversation;

public class HistoryViewModel extends ViewModel {
    private ArrayList<ConversationEntity> conversations;
    private MutableLiveData<ArrayList<ConversationEntity>> liveConversations;

    public HistoryViewModel() {
        conversations = new ArrayList<>();
        ArrayList<String> mess = new ArrayList<>();
        mess.add("asdfsadf");
        conversations.add(new ConversationEntity(1, 23, mess));

        liveConversations = new MutableLiveData<>();
        liveConversations.setValue(conversations);
    }

    public LiveData<ArrayList<ConversationEntity>> getLiveConversations() {
        return liveConversations;
    }
}
