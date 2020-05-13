package edu.kajpitynski.communicator2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

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
    }
}
