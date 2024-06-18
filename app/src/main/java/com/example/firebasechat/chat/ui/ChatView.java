package com.example.firebasechat.chat.ui;

import com.example.firebasechat.chat.entities.ChatMessage;

public interface ChatView {

    void sendMessage();
    void onMessageRecieved(ChatMessage message);
}
