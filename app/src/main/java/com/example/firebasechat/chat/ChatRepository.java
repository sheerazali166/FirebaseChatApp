package com.example.firebasechat.chat;

public interface ChatRepository {

    void sendMessage(String message);
    void setReciever(String reciever);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();

    void changeUserConnectionStatus(boolean online);


}
