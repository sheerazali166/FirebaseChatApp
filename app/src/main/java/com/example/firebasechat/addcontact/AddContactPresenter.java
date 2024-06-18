package com.example.firebasechat.addcontact;

import com.example.firebasechat.addcontact.events.AddContactEvent;

public interface AddContactPresenter {

    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
