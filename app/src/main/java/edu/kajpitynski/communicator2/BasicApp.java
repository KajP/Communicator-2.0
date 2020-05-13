package edu.kajpitynski.communicator2;

import android.app.Application;

import edu.kajpitynski.communicator2.db.AppDatabase;

public class BasicApp extends Application {
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public MessageRepository getRepository() {
        return MessageRepository.getInstance(getDatabase());
    }
}
