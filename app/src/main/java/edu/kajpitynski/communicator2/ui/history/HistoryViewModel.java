package edu.kajpitynski.communicator2.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.BasicApp;
import edu.kajpitynski.communicator2.MessageRepository;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;

public class HistoryViewModel extends ViewModel {
    private LiveData<ArrayList<ConversationEntity>> liveConversations;
    private MessageRepository repository;

    public HistoryViewModel(@NonNull Application application) {
        repository = ((BasicApp) application).getRepository();

        liveConversations = repository.getConversations();
    }

    public LiveData<ArrayList<ConversationEntity>> getConversations() {
        return liveConversations;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HistoryViewModel(mApplication);
        }
    }
}
