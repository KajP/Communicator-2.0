package edu.kajpitynski.communicator2.ui.history;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.kajpitynski.communicator2.BasicApp;
import edu.kajpitynski.communicator2.MessageRepository;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HistoryViewModel extends ViewModel {
    private static final String TAG = "HistoryViewModel";

    private LiveData<List<ConversationEntity>> liveConversations;
    private MessageRepository repository;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    public HistoryViewModel(@NonNull Application application) {
        repository = ((BasicApp) application).getRepository();

        liveConversations = repository.getConversations();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }

    public LiveData<List<ConversationEntity>> getConversations() {
        return liveConversations;
    }

    public void deleteAllConversations() {
        mDisposable.add(repository.deleteAllConversations().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i(TAG, "Conversations deleted");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Error while deleting conversations", throwable);
                    }
                }));
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
