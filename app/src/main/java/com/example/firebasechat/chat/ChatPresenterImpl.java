package com.example.firebasechat.chat;

import com.example.firebasechat.chat.entities.ChatMessage;
import com.example.firebasechat.chat.events.ChatEvent;
import com.example.firebasechat.chat.ui.ChatView;
import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterImpl implements ChatPresenter{

    EventBus eventBus;
    ChatView chatView;
    ChatInteractor chatInteractor;
    ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView _chatView) {

        this.chatView = _chatView;
        this.eventBus = GreenRobotEventBus.getInstance();

        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();

    }

    @Override
    public void onPause() {
        chatInteractor.unsubscribeForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);

    }

    @Override
    public void onResume() {

        chatInteractor.subscriberForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {

        eventBus.register(this);
    }

    @Override
    public void onDestroy() {

        eventBus.unregister(this);
        chatInteractor.destroyChatListener();
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {

        this.chatInteractor.setRecipient(recipient);

    }

    @Override
    public void sendMessage(String message) {

        chatInteractor.sendMessage(message);

    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent chatEvent) {

        if (chatView != null) {

            ChatMessage chatMessage = chatEvent.getChatMessage();
            chatView.onMessageRecieved(chatMessage);
        }
    }
}
