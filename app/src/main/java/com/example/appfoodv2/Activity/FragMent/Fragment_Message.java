package com.example.appfoodv2.Activity.FragMent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodv2.Activity.Admin.ChatActivity;
import com.example.appfoodv2.Adapter.MessageAdapter;
import com.example.appfoodv2.Model.MessageModels;
import com.example.appfoodv2.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
public class Fragment_Message extends Fragment {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private EditText editTextMessage;
    private Button buttonSend;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String recipientId;

    public Fragment_Message() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editTextMessage = view.findViewById(R.id.edit_text_message);
        buttonSend = view.findViewById(R.id.sendButton);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getArguments();
        if (bundle != null) {
            recipientId = bundle.getString("recipientId");
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        if (recipientId != null) {
            loadMessages(recipientId);
        }

        return view;
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();

        if (!TextUtils.isEmpty(messageText)) {
            String senderId = currentUser.getUid();
            String recipientId_senderId = recipientId + "_" + senderId;
            MessageModels message = new MessageModels(null, senderId, recipientId, messageText, System.currentTimeMillis(), recipientId_senderId);

            String messageId = databaseReference.child("messages").push().getKey();
            message.setMessageId(messageId);

            databaseReference.child("messages").child(messageId).setValue(message)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        editTextMessage.setText("");
    }

    private void loadMessages(String recipientId) {
        Query query = databaseReference.child("messages")
                .orderByChild("recipientId_senderId").equalTo(recipientId + "_" + currentUser.getUid());

        FirebaseRecyclerOptions<MessageModels> options =
                new FirebaseRecyclerOptions.Builder<MessageModels>()
                        .setQuery(query, MessageModels.class)
                        .build();

        messageAdapter = new MessageAdapter(options);
        recyclerView.setAdapter(messageAdapter);

        messageAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (messageAdapter != null) {
            messageAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (messageAdapter != null) {
            messageAdapter.stopListening();
        }
    }
}
