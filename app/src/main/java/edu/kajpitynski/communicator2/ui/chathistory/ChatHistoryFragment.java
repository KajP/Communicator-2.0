package edu.kajpitynski.communicator2.ui.chathistory;

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

import java.util.ArrayList;

import edu.kajpitynski.communicator2.R;
import edu.kajpitynski.communicator2.adapter.MyMessageRecyclerViewAdapter;
import edu.kajpitynski.communicator2.db.relations.ConversationWithMessages;
import edu.kajpitynski.communicator2.item.MessageItem;
import edu.kajpitynski.communicator2.model.Message;

public class ChatHistoryFragment extends Fragment {
    private static final String ARG_ID = "id";

    private long id;

    private ChatHistoryViewModel viewModel;
    private RecyclerView recyclerView;

    public static ChatHistoryFragment newInstance(long id) {
        ChatHistoryFragment fragment = new ChatHistoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.messages);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ChatHistoryViewModel.Factory factory = new ChatHistoryViewModel.Factory(requireActivity()
                .getApplication(), id);
        viewModel = new ViewModelProvider(this, factory).get(ChatHistoryViewModel.class);

        viewModel.getConversation().observe(getViewLifecycleOwner(),
                new Observer<ConversationWithMessages>() {
                    @Override
                    public void onChanged(ConversationWithMessages conversation) {
                        if (conversation != null) {
                            ArrayList<MessageItem> messageItems = new ArrayList<>();
                            for (Message message :
                                    conversation.messages) {
                                messageItems.add(new MessageItem(message.getSenderName(),
                                        message.getContent()));
                            }
                            recyclerView.setAdapter(new MyMessageRecyclerViewAdapter(messageItems,
                                    null));
                        }
                    }
                });
    }
}
