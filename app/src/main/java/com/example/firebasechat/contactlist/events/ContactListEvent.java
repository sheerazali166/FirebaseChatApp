package com.example.firebasechat.contactlist.events;


import com.example.firebasechat.contactlist.entities.User;

public class ContactListEvent {

    private User user;
    private int eventType;

    public final static int onContactAdded = 0;
    public final static int onContactChanged = 1;
    public final static int onContactRemoved = 2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int _eventType) {
        this.eventType = _eventType;
    }
}
