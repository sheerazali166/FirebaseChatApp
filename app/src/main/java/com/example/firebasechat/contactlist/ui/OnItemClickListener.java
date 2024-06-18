package com.example.firebasechat.contactlist.ui;

import com.example.firebasechat.contactlist.entities.User;

public interface OnItemClickListener {

    void onItemClick(User user);

    void onItemLongClick(User user);
}
