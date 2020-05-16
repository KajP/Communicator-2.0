package edu.kajpitynski.communicator2.ui.chathistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.kajpitynski.communicator2.BasicApp;
import edu.kajpitynski.communicator2.MessageRepository;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;

public class ChatHistoryViewModel extends ViewModel {
    private MessageRepository repository;

    private LiveData<ConversationWithMessages> conversation;

    public ChatHistoryViewModel(@NonNull Application app, long id) {
        repository = ((BasicApp) app).getRepository();

        conversation = repository.getConversationWithMessages(id);
    }

    public LiveData<ConversationWithMessages> getConversation() {
        return conversation;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application app;
        private final long id;

        public Factory(Application app, long id) {
            this.app = app;
            this.id = id;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChatHistoryViewModel(app, id);
        }
    }
}
