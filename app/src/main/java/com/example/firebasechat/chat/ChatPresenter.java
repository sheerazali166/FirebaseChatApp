package com.example.firebasechat.chat;

import com.example.firebasechat.chat.events.ChatEvent;

public interface ChatPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);

    void sendMessage(String message);
    void onEventMainThread(ChatEvent chatEvent);
}
