package com.example.appfoodv2.View.FragMent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appfoodv2.Model.UserModel;
import com.example.appfoodv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragMent_Message extends Fragment {
    private DatabaseReference mDatabase;
    private EditText editTextMessage,editTextName,editTextEmail,edtCall;
    private


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Hihih");

        editTextMessage = view.findViewById(R.id.editTextMessage);
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        edtCall = view.findViewById(R.id.edtCall);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(uid);

// Lấy thông tin từ các View trong Fragment
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String call = edtCall.getText().toString();
        String message = editTextMessage.getText().toString();

// Ghi thông tin liên hệ vào Firebase Realtime Database
        UserModel contact = new UserModel(name, email, message,call);
        mDatabase.child("contacts").push().setValue(contact);

        mDatabase.child("contacts").push().setValue(contact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Ghi dữ liệu thành công, hiển thị thông báo thành công cho người dùng
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ghi dữ liệu thất bại, hiển thị thông báo thất bại cho người dùng
                    }
                });

        return view;
    }
}
