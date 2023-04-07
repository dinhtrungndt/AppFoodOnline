package com.example.appfoodv2.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.appfoodv2.Activity.FragMent.Fragment_Message;
import com.example.appfoodv2.Adapter.ChatAdapter;
import com.example.appfoodv2.Interface.MessageListener;
import com.example.appfoodv2.Model.ChatModel;
import com.example.appfoodv2.Model.MessageModels;
import com.example.appfoodv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class ChatActivity extends AppCompatActivity implements MessageListener {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseReference;
    private String mUserId;
    private String mReceiverId;
    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mMessageEditText;
    private ImageButton mSendButton;
    private List<ChatModel> mChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUserId = mCurrentUser.getUid();
        mReceiverId = getIntent().getStringExtra("recipientId");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chats");

        mChatList = new ArrayList<>();
        mAdapter = new ChatAdapter(mChatList, mUserId);

        mRecyclerView = findViewById(R.id.messageRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mMessageEditText = findViewById(R.id.messageEditText);
        mSendButton = findViewById(R.id.sendMessageButton);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mMessageEditText.getText().toString();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            }
        });

        mChatList = new ArrayList<>();

        ValueEventListener chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatList.clear();
                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    ChatModel chatModel = chatSnapshot.getValue(ChatModel.class);
                    if ((chatModel.getSenderId().equals(mUserId) && chatModel.getReceiverId().equals(mReceiverId))
                            || (chatModel.getSenderId().equals(mReceiverId) && chatModel.getReceiverId().equals(mUserId))) {
                        mChatList.add(chatModel);
                    }
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mChatList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mDatabaseReference.addValueEventListener(chatListener);
    }

    // Send message to Firebase database
    private void sendMessage(String message) {
        ChatModel chatModel = new ChatModel(mUserId, mReceiverId, message, System.currentTimeMillis());
        mDatabaseReference.push().setValue(chatModel);
        mMessageEditText.setText("");
    }

    @Override
    public void onMessageReceived(MessageModels message) {

    }

    // Implement MessageListener interface
    @Override
    public void onMessageReceived(ChatModel chatModel) {
        if ((chatModel.getSenderId().equals(mUserId) && chatModel.getReceiverId().equals(mReceiverId))
                || (chatModel.getSenderId().equals(mReceiverId) && chatModel.getReceiverId().equals(mUserId))) {
            mChatList.add(chatModel);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mChatList.size() - 1);
        }
    }

    private ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ChatModel chatModel = snapshot.getValue(ChatModel.class);
                if ((chatModel.getSenderId().equals(mUserId) && chatModel.getReceiverId().equals(mReceiverId))
                        || (chatModel.getSenderId().equals(mReceiverId) && chatModel.getReceiverId().equals(mUserId))) {
                    mChatList.add(chatModel);
                }
            }
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mChatList.size() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Handle errors here
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseReference.addValueEventListener(mValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseReference.removeEventListener(mValueEventListener);
    }
}
