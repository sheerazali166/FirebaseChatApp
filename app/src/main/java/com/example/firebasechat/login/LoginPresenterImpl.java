package com.example.firebasechat.login;

import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.example.firebasechat.login.events.LoginEvent;
import com.example.firebasechat.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter{

    EventBus eventBus;
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView _loginView) {

        this.loginView = _loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {

        eventBus.register(this);
    }

    @Override
    public void onDestroy() {

        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {

        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkAlreadyAuthenticated();
    }

    // this should be error
    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent loginEvent) {

        switch (loginEvent.getEventType()) {
            case LoginEvent.onSignInError:
                onSignInError(loginEvent.getErrorMessage());
                break;
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(loginEvent.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;

        }
    }

    private void onSignInSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {

        if (loginView != null) {

            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error) {

        if (loginView != null) {

            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error) {

        if (loginView != null) {

            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }

    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    @Override
    public void validateLogin(String email, String password) {

        if (loginView != null) {

            loginView.disableInputs();
            loginView.showProgress();

        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {

        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }
}
