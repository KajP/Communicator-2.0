package edu.kajpitynski.communicator2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.item.MessageItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {
    private static final String TAG = "ChatViewModel";

    private MessageRepository repository;
    private ArrayList<MessageItem> messages = new ArrayList<>();

    private CompositeDisposable mDisposable = new CompositeDisposable();

    public ChatViewModel(@NonNull Application application) {
        repository = ((BasicApp) application).getRepository();
    }

    public void addMessage(MessageItem messageItem) {
        messages.add(messageItem);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // repository.addConversationWithMessages
        mDisposable.add(repository.addConversation(new ConversationEntity(0, "sdasdaf"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "Conversation history added to db");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Error while saving the conversation", throwable);
                    }
                }));
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
