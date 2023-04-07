package com.example.appfoodv2.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodv2.Model.ChatModel;
import com.example.appfoodv2.Model.MessageModels;
import com.example.appfoodv2.R;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<ChatModel> mChatList;
    private String mCurrentUserId;

    public ChatAdapter(List<ChatModel> chatList, String currentUserId) {
        mChatList = chatList;
        mCurrentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel = mChatList.get(position);
        if (chatModel.getSenderId().equals(mCurrentUserId)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = mChatList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chatModel);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatModel);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mChatList != null) {
            return mChatList.size();
        } else {
            return 0;
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeTextView;

        public SentMessageHolder(View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.sent_message_text_view);
            timeTextView = itemView.findViewById(R.id.sent_message_time_text_view);
        }

        void bind(ChatModel chatModel) {
            messageTextView.setText(chatModel.getMessage());
            timeTextView.setText(getFormattedTime(chatModel.getTimestamp()));
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeTextView;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.receivedMessageTextView);
            timeTextView = itemView.findViewById(R.id.receivedTimeTextView);
        }

        void bind(ChatModel chatModel) {
            messageTextView.setText(chatModel.getMessage());
            timeTextView.setText(getFormattedTime(chatModel.getTimestamp()));
        }
    }

    private String getFormattedTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
