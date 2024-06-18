package com.example.firebasechat.login.events;

public class LoginEvent {

    public final static int onSignInError = 0;
    public final static int onSignUpError = 1;
    public final static int onSignInSuccess = 2;
    public final static int onSignUpSuccess = 3;
    public final static int onFailedToRecoverSession = 4;

    private int eventType;
    private String errorMessage;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int _eventType) {
        this.eventType = _eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String _errorMessage) {
        this.errorMessage = _errorMessage;
    }


}
