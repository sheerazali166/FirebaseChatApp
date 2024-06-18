package com.example.firebasechat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.AndroidChatApplication;
import com.example.firebasechat.R;
import com.example.firebasechat.chat.ChatPresenter;
import com.example.firebasechat.chat.ChatPresenterImpl;
import com.example.firebasechat.chat.adapters.ChatAdapter;
import com.example.firebasechat.chat.entities.ChatMessage;
import com.example.firebasechat.domain.AvatarHelper;
import com.example.firebasechat.lib.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtUser)
    TextView txtUser;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.editTextMessage)
    EditText inputMessage;

    @BindView(R.id.messageRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imgAvatar)
    CircleImageView circleImageViewAvatar;

    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";
    private ChatAdapter chatAdapter;
    private ChatPresenter chatPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

         chatPresenter = new ChatPresenterImpl(this);
         chatPresenter.onCreate();

         setSupportActionBar(toolbar);
         Intent intent = getIntent();
         setToolbarData(intent);

         setupAdapter();
         setupRecyclerView();

//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatPresenter.onDestroy();
    }

    private void setToolbarData(Intent intent) {

        String recipient = intent.getStringExtra(EMAIL_KEY);
        chatPresenter.setChatRecipient(recipient);

        boolean online = intent.getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        AndroidChatApplication androidChatApplication = (AndroidChatApplication) getApplication();
        ImageLoader imageLoader = androidChatApplication.getImageLoader();
        imageLoader.load(circleImageViewAvatar, AvatarHelper.getAvatarUrl(recipient));


    }

    private void setupAdapter() {

        chatAdapter = new ChatAdapter(this, new ArrayList<ChatMessage>());

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    @OnClick(R.id.btnSendMessage)
    public void sendMessage() {

            chatPresenter.sendMessage(inputMessage.getText().toString());
            inputMessage.setText("");
    }

    @Override
    public void onMessageRecieved(ChatMessage chatMessage) {

        chatAdapter.add(chatMessage);
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

}