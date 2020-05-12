package edu.kajpitynski.communicator2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.kajpitynski.communicator2.R;
import edu.kajpitynski.communicator2.model.Conversation;

public class MyHistoryRecyclerViewAdapter
        extends RecyclerView.Adapter<MyHistoryRecyclerViewAdapter.MyViewHolder> {
    private List<? extends Conversation> conversations;

    public MyHistoryRecyclerViewAdapter(List<? extends Conversation> conversations) {
        this.conversations = conversations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mItem = conversations.get(position);
        holder.mName.setText(holder.mItem.getRecipient());
        holder.mDetails.setText("asdfsdf");
        holder.mTime.setText("asddsf");
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
