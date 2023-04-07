package com.example.appfoodv2.Interface;

import com.example.appfoodv2.Model.ChatModel;
import com.example.appfoodv2.Model.MessageModels;

public interface MessageListener {
    void onMessageReceived(MessageModels message);

    // Implement MessageListener interface
    void onMessageReceived(ChatModel chatModel);
}
