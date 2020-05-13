package edu.kajpitynski.communicator2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.kajpitynski.communicator2.db.entity.MessageEntity;
import edu.kajpitynski.communicator2.network.ChatManager;
import edu.kajpitynski.communicator2.adapter.MyMessageRecyclerViewAdapter;
import edu.kajpitynski.communicator2.item.MessageItem;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";

    private ChatManager chatManager;

    private ArrayList<MessageItem> messageItems = new ArrayList<>();
    private ArrayList<MessageEntity> messageEntities = new ArrayList<>();

    private MyMessageRecyclerViewAdapter adapter;

    private ChatViewModel mViewModel;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChatFragment.
     */
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.messages);
        adapter = new MyMessageRecyclerViewAdapter(messageItems, null);
        recyclerView.setAdapter(adapter);

        final TextView chatLine = view.findViewById(R.id.chatLine);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatManager != null) {
                    chatManager.write(chatLine.getText().toString().getBytes());
                    pushMessage("Me: ", chatLine.getText().toString());
                    Log.d(TAG, "Written");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
    }

    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public void pushMessage(String user, String message) {
        messageItems.add(new MessageItem(user, message));
        adapter.notifyDataSetChanged();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MessageItem item);
    }
}
