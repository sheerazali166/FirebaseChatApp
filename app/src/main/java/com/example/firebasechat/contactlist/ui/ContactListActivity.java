package com.example.firebasechat.contactlist.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.AndroidChatApplication;
import com.example.firebasechat.R;
import com.example.firebasechat.addcontact.ui.AddContactFragment;
import com.example.firebasechat.chat.ui.ChatActivity;
import com.example.firebasechat.contactlist.ContactListPresenter;
import com.example.firebasechat.contactlist.ContactListPresenterImpl;
import com.example.firebasechat.contactlist.adapters.ContactListAdapter;
import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.lib.ImageLoader;
import com.example.firebasechat.login.ui.LoginActivity;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerViewContacts)
    RecyclerView recyclerView;

    private ContactListAdapter contactListAdapter;
    ContactListPresenter contactListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);


        contactListPresenter = new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();

        toolbar.setSubtitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);

        setupAdapter();
        setupRecyclerView();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        contactListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contactListPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactListPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            contactListPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupAdapter() {
        AndroidChatApplication androidChatApplication = (AndroidChatApplication) getApplication();
        ImageLoader imageLoader = androidChatApplication.getImageLoader();
        contactListAdapter = new ContactListAdapter(new ArrayList<User>(), imageLoader, this);

    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);
    }

    @OnClick(R.id.fab)
    public void addContact() {

        AddContactFragment addContactFragment = new AddContactFragment();
        addContactFragment.show(getSupportFragmentManager(), "");

    }


    @Override
    public void onContactAdded(User user) {

        contactListAdapter.add(user);
    }

    @Override
    public void onContactChange(User user) {

        contactListAdapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {

        contactListAdapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ChatActivity.EMAIL_KEY, user.getEmail());
        intent.putExtra(ChatActivity.ONLINE_KEY, user.isOnline());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User user) {

        contactListPresenter.removeContact(user.getEmail());
    }

}