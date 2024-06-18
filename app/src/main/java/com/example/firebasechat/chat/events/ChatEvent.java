package com.example.firebasechat.chat.events;

import com.example.firebasechat.chat.entities.ChatMessage;

public class ChatEvent {

    ChatMessage chatMessage;

    public ChatEvent(ChatMessage _chatMessage) {
        this.chatMessage = _chatMessage;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
