package edu.kajpitynski.communicator2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.kajpitynski.communicator2.model.Conversation;
import edu.kajpitynski.communicator2.ui.history.HistoryFragment;
import edu.kajpitynski.communicator2.ui.history.HistoryViewModel;

public class HistoryActivity extends AppCompatActivity
        implements HistoryFragment.OnListFragmentInteractionListener {
    private static String TAG = "HistoryActivity";

    private HistoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        HistoryViewModel.Factory viewModelFactory = new HistoryViewModel.Factory(getApplication());
        mViewModel = new ViewModelProvider(this, viewModelFactory)
                .get(HistoryViewModel.class);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(HistoryActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mViewModel.deleteAllConversations();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListFragmentInteraction(Conversation conversation) {
        Log.d(TAG, conversation.toString());
        Intent intent = new Intent(this, ChatHistoryActivity.class);
        intent.putExtra("conversationId", conversation.getId());
        startActivity(intent);
    }
}
