package edu.kajpitynski.communicator2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.item.MessageItem;

public class ChatViewModel extends ViewModel {
    private MessageRepository repository;
    private ArrayList<MessageItem> messages = new ArrayList<>();

    public ChatViewModel(@NonNull Application application) {
        repository = ((BasicApp) application).getRepository();
    }

    public void addMessage(MessageItem messageItem) {
        messages.add(messageItem);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.addConversation(new ConversationEntity(0, "sdasdaf"));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChatViewModel(mApplication);
        }
    }
}
