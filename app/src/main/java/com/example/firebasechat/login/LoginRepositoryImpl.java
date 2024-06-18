package com.example.firebasechat.login;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.firebasechat.R;
import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.domain.FirebaseHelper;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.example.firebasechat.login.events.LoginEvent;
import com.example.firebasechat.login.ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginRepositoryImpl implements LoginRepository{

    private FirebaseHelper firebaseHelper;
    private DatabaseReference databaseReference;
    private DatabaseReference myUserReference;



    public LoginRepositoryImpl() {

        firebaseHelper = FirebaseHelper.getInstance();
        databaseReference = firebaseHelper.getDataReference();
        myUserReference = firebaseHelper.getMyUserReference();
    }

    @Override
    public void signUp(String email, String password) {

        /*

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                postEvent(LoginEvent.onSignUpError, "Enable to sign up %s");
                            } else {

                                postEvent(LoginEvent.onSignUpSuccess);
                                signIn(email, password);
                            }
                        }
                    });

        */

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            postEvent(LoginEvent.onSignUpSuccess);
                            signIn(email, password);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            postEvent(LoginEvent.onSignUpError, exception.getMessage());
                        }
                    });


        }



    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);

        if (errorMessage != null) {

            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);

    }

    @Override
    public void signIn(String email, String password) {

        /*
        try {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                postEvent(LoginEvent.onSignInError, "Enable to sign up %s");

                            } else {

                                myUserReference = firebaseHelper.getMyUserReference();
                                myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        initSignIn(dataSnapshot);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        postEvent(LoginEvent.onSignInError, databaseError.getMessage());
                                    }
                                });


                            }
                        }
                    });

        } catch (Exception exception) {
            postEvent(LoginEvent.onSignInError, exception.getMessage());
        }
        */

            try {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                myUserReference = firebaseHelper.getMyUserReference();
                                myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        initSignIn(dataSnapshot);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        postEvent(LoginEvent.onSignInError, databaseError.getMessage());
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                postEvent(LoginEvent.onSignInError, exception.getMessage());
                            }
                        });

            } catch (Exception exception) {
                postEvent(LoginEvent.onSignInError, exception.getMessage());
            }


        }


    private void registerNewUser() {
        String email = firebaseHelper.getAuthUserEmail();

        if (email != null) {
            User currentUser = new User(email, true, null);
            myUserReference.setValue(currentUser);
        }
    }

    private void initSignIn(DataSnapshot dataSnapshot){

        User currentUser = dataSnapshot.getValue(User.class);

        if (currentUser == null) {

            registerNewUser();
        }
        firebaseHelper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.onSignInSuccess);

    }

    @Override
    public void checkAlreadyAuthenticated() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            myUserReference = firebaseHelper.getMyUserReference();
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    initSignIn(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    postEvent(LoginEvent.onSignInError, databaseError.getMessage());
                }
            });

        } else {

            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }
}
