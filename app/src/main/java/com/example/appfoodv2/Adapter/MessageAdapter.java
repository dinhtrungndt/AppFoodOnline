package com.example.appfoodv2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodv2.Model.MessageModels;
import com.example.appfoodv2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageAdapter extends FirebaseRecyclerAdapter<MessageModels, MessageAdapter.MessageViewHolder> {
    private static final int MESSAGE_TYPE_LEFT = 0;
    private static final int MESSAGE_TYPE_RIGHT = 1;

    private FirebaseUser currentUser;

    public MessageAdapter(@NonNull FirebaseRecyclerOptions<MessageModels> options) {
        super(options);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull MessageModels model) {
        holder.bind(model);
    }

    @Override
    public int getItemViewType(int position) {
        MessageModels message = getItem(position);
        if (message.getSenderId().equals(currentUser.getUid())) {
            return MESSAGE_TYPE_RIGHT;
        } else {
            return MESSAGE_TYPE_LEFT;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left, parent, false);
        }
        return new MessageViewHolder(view);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessageText;
        private TextView textViewTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessageText = itemView.findViewById(R.id.text_view_message_text);
            textViewTimestamp = itemView.findViewById(R.id.text_view_timestamp);
        }

        public void bind(MessageModels message) {
            textViewMessageText.setText(message.getMessageText());
            textViewTimestamp.setText(getFormattedTimestamp(message.getTimestamp()));
        }

        private String getFormattedTimestamp(long timestamp) {
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
            return formatter.format(date);
        }
    }
}
