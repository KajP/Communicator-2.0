package edu.kajpitynski.communicator2.application;

import android.app.Application;

import edu.kajpitynski.communicator2.db.AppDatabase;
import edu.kajpitynski.communicator2.repository.MessageRepository;

public class BasicApp extends Application {
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public MessageRepository getRepository() {
        return MessageRepository.getInstance(getDatabase());
    }
}
