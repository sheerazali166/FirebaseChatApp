package com.example.firebasechat.contactlist.ui;

import com.example.firebasechat.contactlist.entities.User;

public interface ContactListView {

    void onContactAdded(User user);
    void onContactChange(User user);
    void onContactRemoved(User user);
}
