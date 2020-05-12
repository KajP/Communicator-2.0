package edu.kajpitynski.communicator2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.kajpitynski.communicator2.ui.history.HistoryFragment;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .commitNow();
        }
    }
}
