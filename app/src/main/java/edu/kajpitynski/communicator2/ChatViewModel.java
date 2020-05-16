package edu.kajpitynski.communicator2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.entity.MessageEntity;
import edu.kajpitynski.communicator2.item.MessageItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {
    private static final String TAG = "ChatViewModel";

    private MessageRepository repository;

    private final String recipient;
    private ArrayList<MessageEntity> messageEntities = new ArrayList<>();

    private CompositeDisposable mDisposable = new CompositeDisposable();

    public ChatViewModel(@NonNull Application application, String recipient) {
        repository = ((BasicApp) application).getRepository();
        this.recipient = recipient;
    }

    public void addMessage(MessageItem messageItem) {
        messageEntities.add(new MessageEntity(0, 0, messageItem.user,
                messageItem.content));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.add(repository
                .addConversationWithMessages(new ConversationEntity(0, recipient),
                        messageEntities.toArray(new MessageEntity[0]))
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

    private void convertMessages() {

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;
        private String recipient;

        public Factory(@NonNull Application application, String recipient) {
            mApplication = application;
            this.recipient = recipient;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChatViewModel(mApplication, recipient);
        }
    }
}
