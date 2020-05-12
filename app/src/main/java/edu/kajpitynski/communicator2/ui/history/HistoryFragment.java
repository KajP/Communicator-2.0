package edu.kajpitynski.communicator2.ui.history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.R;
import edu.kajpitynski.communicator2.adapter.MyHistoryRecyclerViewAdapter;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;

public class HistoryFragment extends Fragment {

    private HistoryViewModel mViewModel;

    private RecyclerView recyclerView;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        recyclerView = view.findViewById(R.id.history_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        mViewModel.getConversations().observe(getViewLifecycleOwner(),
                new Observer<ArrayList<ConversationEntity>>() {
            @Override
            public void onChanged(ArrayList<ConversationEntity> conversationEntities) {
                recyclerView.setAdapter(new MyHistoryRecyclerViewAdapter(conversationEntities));
            }
        });
    }

}
