package com.example.firebasechat.addcontact.events;

public class AddContactEvent {

    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean _error) {
        this.error = _error;
    }
}
