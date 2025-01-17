package com.example.firebasechat.contactlist;

import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.contactlist.events.ContactListEvent;
import com.example.firebasechat.contactlist.ui.ContactListView;
import com.example.firebasechat.lib.EventBus;
import com.example.firebasechat.lib.GreenRobotEventBus;
import com.google.common.eventbus.Subscribe;

public class ContactListPresenterImpl implements ContactListPresenter {

    EventBus eventBus;
    ContactListView contactListView;
    ContactListSessionInteractor contactListSessionInteractor;
    ContactListInteractor contactListInteractor;

    public ContactListPresenterImpl(ContactListView _contactListView) {

        this.eventBus = GreenRobotEventBus.getInstance();
        this.contactListView = _contactListView;
        this.contactListSessionInteractor = new ContactListSessionInteractorImpl();
        this.contactListInteractor = new ContactListInteractorImpl();

    }

    @Override
    public void onPause() {

        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unSubscribeForContactEvents();;

    }

    @Override
    public void onResume() {

        contactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribeForContactEvents();
    }

    @Override
    public void onCreate() {

        eventBus.register(this);
    }

    @Override
    public void onDestroy() {

        eventBus.unregister(this);
        contactListInteractor.destroyContactListListener();
        contactListView = null;
    }

    @Override
    public void signOff() {

        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.destroyContactListListener();
        contactListInteractor.unSubscribeForContactEvents();
        contactListSessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListSessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {

        contactListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent contactListEvent) {

        User user = contactListEvent.getUser();

        switch (contactListEvent.getEventType()) {

            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }

    public void onContactAdded(User user) {

        if (contactListView != null) {

            contactListView.onContactAdded(user);
        }

    }

    public void onContactChanged(User user) {

        if (contactListView != null) {

            contactListView.onContactChange(user);
        }

    }

    public void onContactRemoved(User user) {

        if (contactListView != null) {

            contactListView.onContactRemoved(user);
        }

    }
}
