package edu.kajpitynski.communicator2.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.kajpitynski.communicator2.db.AppDatabase;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.db.entity.MessageEntity;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class MessageRepository {
    private static String TAG = "MessageRepository";
    private static MessageRepository sInstance;

    private AppDatabase mDatabase;

    public static MessageRepository getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MessageRepository(database);
                }
            }
        }
        return sInstance;
    }

    private MessageRepository(AppDatabase database) {
        mDatabase = database;
    }

    public LiveData<List<ConversationEntity>> getConversations() {
        return mDatabase.conversationDao().getAllConversations();
    }

    public Completable addConversation(final ConversationEntity conversationEntity) {
        return mDatabase.conversationDao().insertConversation(conversationEntity).ignoreElement();
    }

    public Completable addConversationWithMessages(ConversationEntity conversationEntity,
                                                   final MessageEntity... messageEntities) {
        conversationEntity.setLastTime(System.currentTimeMillis());
        return mDatabase.conversationDao().insertConversation(conversationEntity)
                .flatMapCompletable(new Function<Long, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Long conversationId) {
                        for (MessageEntity message :
                                messageEntities) {
                            message.setConversationId(conversationId);
                        }
                        return mDatabase.messageDao().insertMessages(messageEntities);
                    }
                });
    }

    public LiveData<ConversationWithMessages> getConversationWithMessages(long id) {
        return mDatabase.conversationDao().getConversationWithMessages(id);
    }

    public Completable deleteAllConversations() {
        return mDatabase.conversationDao().deleteAllConversation();
    }
}
