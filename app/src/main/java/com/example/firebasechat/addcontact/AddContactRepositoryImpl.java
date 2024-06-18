package com.example.firebasechat.addcontact;


import androidx.annotation.NonNull;

import com.example.firebasechat.addcontact.events.AddContactEvent;
import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.domain.FirebaseHelper;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class AddContactRepositoryImpl implements AddContactRepository{
    @Override
    public void addContact(String email) {

        final String key = email.replace(".", "_");
        FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
        final DatabaseReference userReference = firebaseHelper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                AddContactEvent event = new AddContactEvent();

                if (user != null) {

                    boolean online = user.isOnline();
                    FirebaseHelper _firebaseHelper = FirebaseHelper.getInstance();
                    DatabaseReference userContactsReference = _firebaseHelper.getMyContactsReferences();
                    userContactsReference.child(key).setValue(online);

                    String currentUserEmailKey = _firebaseHelper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".", "_");
                    DatabaseReference reverseUserContactsReference = _firebaseHelper.getContactsReference(email);
                    reverseUserContactsReference.child(currentUserEmailKey).setValue(true);

                } else {

                    event.setError(true);
                }

               EventBus eventBus =  GreenRobotEventBus.getInstance();
               eventBus.post(event);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
