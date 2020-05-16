package edu.kajpitynski.communicator2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.kajpitynski.communicator2.ui.chathistory.ChatHistoryFragment;

public class ChatHistoryActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            long id = getIntent().getLongExtra("conversationId", 0L);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, ChatHistoryFragment.newInstance(id)).commitNow();
        }
    }
}
