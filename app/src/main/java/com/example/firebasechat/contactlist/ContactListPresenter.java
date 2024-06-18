package com.example.firebasechat.contactlist;

import com.example.firebasechat.contactlist.events.ContactListEvent;

public interface ContactListPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent contactListEvent);
}
