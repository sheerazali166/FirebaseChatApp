package com.example.firebasechat.addcontact;

import com.example.firebasechat.addcontact.events.AddContactEvent;
import com.example.firebasechat.addcontact.ui.AddContactView;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.google.common.eventbus.Subscribe;

public class AddContactPresenterImpl implements AddContactPresenter{

    EventBus eventBus;
    AddContactView addContactView;
    AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView _addContactView) {
        this.eventBus =  GreenRobotEventBus.getInstance();
        this.addContactView = _addContactView;
        this.addContactInteractor = new AddContactInteractorImpl(new AddContactRepositoryImpl());

    }


    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {

        addContactView = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {

        addContactView.hideInput();
        addContactView.showProgress();
        this.addContactInteractor.addContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {

        if (addContactView != null) {

            addContactView.hideProgress();
            addContactView.showInput();

            if (event.isError()) {

                addContactView.contactNotAdded();
            } else {
                addContactView.contactAdded();
            }

        }

    }
}
