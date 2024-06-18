package com.example.firebasechat.chat;

public interface ChatInteractor {

    void sendMessage(String message);
    void setRecipient(String recipient);

    void destroyChatListener();
    void subscriberForChatUpdates();
    void unsubscribeForChatUpdates();

}
