package edu.kajpitynski.communicator2.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.kajpitynski.communicator2.MessageRepository;
import edu.kajpitynski.communicator2.entity.ConversationEntity;
import edu.kajpitynski.communicator2.model.Conversation;

public class HistoryViewModel extends ViewModel {
    private LiveData<ArrayList<ConversationEntity>> liveConversations;
    private MessageRepository repository;

    public HistoryViewModel() {
        repository = MessageRepository.getInstance();

        liveConversations = repository.getConversations();
    }

    public LiveData<ArrayList<ConversationEntity>> getConversations() {
        return liveConversations;
    }
}
