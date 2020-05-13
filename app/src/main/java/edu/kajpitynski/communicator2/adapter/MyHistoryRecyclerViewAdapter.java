package edu.kajpitynski.communicator2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.kajpitynski.communicator2.R;
import edu.kajpitynski.communicator2.model.Conversation;
import edu.kajpitynski.communicator2.ui.history.HistoryFragment;

public class MyHistoryRecyclerViewAdapter
        extends RecyclerView.Adapter<MyHistoryRecyclerViewAdapter.MyViewHolder> {
    private List<? extends Conversation> conversations;

    private HistoryFragment.OnListFragmentInteractionListener mListener;

    public MyHistoryRecyclerViewAdapter(List<? extends Conversation> conversations,
                                        HistoryFragment
                                                .OnListFragmentInteractionListener listener) {
        this.conversations = conversations;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.mItem = conversations.get(position);
        holder.mName.setText(holder.mItem.getRecipient());
        holder.mDetails.setText("asdfsdf");
        holder.mTime.setText("asddsf");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mDetails;
        public final TextView mTime;
        public Conversation mItem;

        public MyViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.nameTextView);
            mDetails = view.findViewById(R.id.detailsTextView);
            mTime = view.findViewById(R.id.timeTextView);
        }
    }
}
