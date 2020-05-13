package edu.kajpitynski.communicator2.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.kajpitynski.communicator2.R;
import edu.kajpitynski.communicator2.adapter.MyHistoryRecyclerViewAdapter;
import edu.kajpitynski.communicator2.db.entity.ConversationEntity;
import edu.kajpitynski.communicator2.model.Conversation;

public class HistoryFragment extends Fragment {

    private HistoryViewModel mViewModel;

    private RecyclerView recyclerView;

    private OnListFragmentInteractionListener mListener;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnListFragmentInteractionListener) context;
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

        HistoryViewModel.Factory factory = new HistoryViewModel.Factory(requireActivity()
                .getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(HistoryViewModel.class);

        mViewModel.getConversations().observe(getViewLifecycleOwner(),
                new Observer<List<ConversationEntity>>() {
                    @Override
                    public void onChanged(List<ConversationEntity> conversationEntities) {
                        recyclerView.setAdapter(new MyHistoryRecyclerViewAdapter(conversationEntities,
                                mListener));
                    }
                });
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Conversation conversation);
    }
}
