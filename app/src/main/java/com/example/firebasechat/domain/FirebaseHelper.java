package com.example.firebasechat.domain;

import androidx.annotation.NonNull;


import com.example.firebasechat.contactlist.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseHelper {

    private DatabaseReference databaseReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    public final static String CONTACTS_PATH = "contacts";

    private static class SingletonHolder {

        private static final FirebaseHelper INSTANCE = new FirebaseHelper();

    }

    public static FirebaseHelper getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return databaseReference;
    }

    public String getAuthUserEmail() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;

        if (user != null) {

            email = user.getEmail();

        }

        return email;
    }

    public DatabaseReference getUserReference(String email) {

        DatabaseReference userReference = null;

        if (email != null) {

            String emailKey = email.replace(".", "_");
            userReference = databaseReference.getRoot().child(USERS_PATH).child(emailKey);

        }

        return userReference;

    }

    public DatabaseReference getMyUserReference() {

        return getUserReference(getAuthUserEmail());

    }

    public DatabaseReference getContactsReference(String email) {

        return getUserReference(email).child(CONTACTS_PATH);

    }

    public DatabaseReference getMyContactsReferences() {

        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail) {

        String childKey = childEmail.replace(".", "_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String reciever) {

        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReciever = reciever.replace(".", "_");


        String keyChat = keySender + SEPARATOR + keyReciever;

        if (keySender.compareTo(keyReciever) > 0) {

            keyChat = keyReciever + SEPARATOR + keySender;

        }

        return databaseReference.getRoot().child(CHATS_PATH).child(keyChat);


    }

    public void changeUserConnectionStatus(boolean online) {

        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);
        }

        notifyContactsOfConnectionChange(online);

    }


    public void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {

        final String myEmail = getAuthUserEmail();

        getMyContactsReferences().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child: snapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference _databaseReference = getOneContactReference(email, myEmail);
                    _databaseReference.setValue(online);
                }

                if (signoff) {

                    FirebaseAuth.getInstance().signOut();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    public void signOff() {
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

}


