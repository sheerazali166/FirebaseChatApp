package com.example.firebasechat.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasechat.chat.entities.ChatMessage;
import com.example.firebasechat.chat.events.ChatEvent;
import com.example.firebasechat.domain.FirebaseHelper;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ChatRepositoryImpl implements ChatRepository{

    private String reciever;
    private FirebaseHelper firebaseHelper;
    private ChildEventListener childEventListener;

    public ChatRepositoryImpl() {

        firebaseHelper = FirebaseHelper.getInstance();

    }

    @Override
    public void sendMessage(String message) {

        String keySendor = firebaseHelper.getAuthUserEmail().replace(".", "_");
        ChatMessage chatMessage = new ChatMessage(keySendor, message);
        DatabaseReference databaseReference = firebaseHelper.getChatsReference(reciever);
        databaseReference.push().setValue(chatMessage);
    }

    @Override
    public void setReciever(String _reciever) {
        this.reciever = _reciever;
    }

    @Override
    public void destroyChatListener() {

        childEventListener = null;
    }

    @Override
    public void subscribeForChatUpdates() {

        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String messageSender = chatMessage.getSender();
                    messageSender = messageSender.replace("_", ".");

                    String currentUserEmail = firebaseHelper.getAuthUserEmail();
                    chatMessage.setSentByMe(messageSender.equals(currentUserEmail));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            firebaseHelper.getChatsReference(reciever).addChildEventListener(childEventListener);
        }
    }

    @Override
    public void unSubscribeForChatUpdates() {

        if (childEventListener != null) {

            firebaseHelper.getChatsReference(reciever).removeEventListener(childEventListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {

        firebaseHelper.changeUserConnectionStatus(online);
    }
}
