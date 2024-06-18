package com.example.firebasechat.login;

import com.example.firebasechat.login.events.LoginEvent;

public interface LoginPresenter {

    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void onEventMainThread(LoginEvent loginEvent);
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);


}
