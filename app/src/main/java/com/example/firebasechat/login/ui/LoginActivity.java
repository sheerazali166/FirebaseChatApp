package com.example.firebasechat.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebasechat.R;
import com.example.firebasechat.addcontact.ui.AddContactFragment;
import com.example.firebasechat.chat.ui.ChatActivity;
import com.example.firebasechat.contactlist.ui.ContactListActivity;
import com.example.firebasechat.login.LoginPresenter;
import com.example.firebasechat.login.LoginPresenterImpl;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.btnSignin)
    Button btnSignIn;

    @BindView(R.id.btnSignup)
    Button btnSignUp;

    @BindView(R.id.editTxtEmail)
    EditText inputEmail;

    @BindView(R.id.editTxtPassword)
    EditText inputPassword;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.main)
    RelativeLayout relativeLayoutContainer;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        }

//        );
    }



    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public void enableInputs() {

        setInputs(true);
    }

    @Override
    public void disableInputs() {

        setInputs(false);

    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progressBar.setVisibility(View.GONE);
    }


    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {


      loginPresenter.registerNewUser(inputEmail.getText().toString(), inputPassword.getText().toString());

    }

    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {

        loginPresenter.validateLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    private void setInputs(boolean enabled) {

        btnSignIn.setEnabled(true);
        btnSignUp.setEnabled(true);
        inputEmail.setEnabled(true);
        inputPassword.setEnabled(true);
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {

        inputPassword.setText("");
        String messageError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(messageError);
    }

    @Override
    public void newUserSuccess() {

        Snackbar.make(relativeLayoutContainer, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {

        inputPassword.setText("");
        String messageError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(messageError);

    }
}