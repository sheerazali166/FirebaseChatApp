package com.example.firebasechat.contactlist.entities;

import java.util.Map;

public class User {

    String email;
    boolean online;
    Map<String, Boolean> contacts;
    public final static boolean ONLINE = true;
    public final static boolean OFFLINE = false;

    public User() {}

    public User(String _email, boolean _online, Map<String, Boolean> _contacts) {

        this.email = _email;
        this.online = _online;
        this.contacts = _contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean _online) {
        this.online = _online;
    }

    public Map<String, Boolean> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Boolean> _contacts) {
        this.contacts = _contacts;
    }
}
