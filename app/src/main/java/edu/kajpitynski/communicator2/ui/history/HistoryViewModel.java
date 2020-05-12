package edu.kajpitynski.communicator2.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.MessageRepository;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;

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
