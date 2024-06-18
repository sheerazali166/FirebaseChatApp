package com.example.firebasechat.contactlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.contactlist.events.ContactListEvent;

import com.example.firebasechat.domain.FirebaseHelper;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ContactListRepositoryImpl implements ContactListRepository{

    private FirebaseHelper firebaseHelper;
    private ChildEventListener childEventListener;

    public ContactListRepositoryImpl() {

        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {

        firebaseHelper.signOff();
    }

    @Override
    public String getCurrentEmail() {

        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {

        String currentUserEmail = firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOneContactReference(currentUserEmail, email).removeValue();
        firebaseHelper.getOneContactReference(email, currentUserEmail).removeValue();

    }

    @Override
    public void destroyContactListListener() {
        childEventListener = null;
    }

    @Override
    public void subscribeForContactListUpdates() {

        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactAdded, user);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactChanged, user);

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactRemoved, user);

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
        }

        firebaseHelper.getMyContactsReferences().addChildEventListener(childEventListener);
    }

    private void postEvent(int type, User user) {

        ContactListEvent contactListEvent = new ContactListEvent();
        contactListEvent.setEventType(type);
        contactListEvent.setUser(user);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(contactListEvent);
    }

    @Override
    public void unSubscribeForContactListUpdates() {

        if (childEventListener != null) {

            firebaseHelper.getMyContactsReferences().removeEventListener(childEventListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {

        firebaseHelper.changeUserConnectionStatus(online);

    }
}
