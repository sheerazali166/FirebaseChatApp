package com.example.firebasechat.login;

public interface LoginInteractor {

    void checkAlreadyAuthenticated();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
