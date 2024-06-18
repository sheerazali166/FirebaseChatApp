package com.example.firebasechat.chat;

public class ChatInteractorImpl implements ChatInteractor{

    ChatRepository chatRepository;

    public ChatInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String message) {

        chatRepository.sendMessage(message);
    }

    @Override
    public void setRecipient(String recipient) {

        chatRepository.setReciever(recipient);
    }

    @Override
    public void destroyChatListener() {

        chatRepository.destroyChatListener();
    }

    @Override
    public void subscriberForChatUpdates() {

        chatRepository.subscribeForChatUpdates();
    }

    @Override
    public void unsubscribeForChatUpdates() {

        chatRepository.unSubscribeForChatUpdates();
    }
}
