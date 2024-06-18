package com.example.firebasechat.chat.entities;

import com.google.firebase.database.Exclude;

public class ChatMessage {

    String message;
    String sender;
    @Exclude
    boolean sentByMe;

    public ChatMessage() {}

    public ChatMessage(String _sendor, String _message) {

        this.message = _message;
        this.sender = _sendor;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String _message) {
        this.message = _message;
    }

    public void setSender(String _sender) {
        this.sender = _sender;
    }

    public String getSender() {
        return sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean _sentByMe) {
        this.sentByMe = _sentByMe;
    }
}
