package com.example.firebasechat.addcontact;

public class AddContactInteractorImpl implements AddContactInteractor{

    AddContactRepositoryImpl addContactRepository;

    public AddContactInteractorImpl(AddContactRepositoryImpl _addContactRepository) {

        this.addContactRepository = _addContactRepository;
    }

    @Override
    public void addContact(String email) {

        addContactRepository.addContact(email);
    }
}
