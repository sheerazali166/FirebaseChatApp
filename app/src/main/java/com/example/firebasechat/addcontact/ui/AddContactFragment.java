package com.example.firebasechat.addcontact.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.firebasechat.R;
import com.example.firebasechat.addcontact.AddContactPresenter;
import com.example.firebasechat.addcontact.AddContactPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {


    @BindView(R.id.editTxtEmail)
    EditText inputEmail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private AddContactPresenter addContactPresenter;



    public AddContactFragment() {

        addContactPresenter = new AddContactPresenterImpl(this);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addcontact_message_title)
                .setPositiveButton(R.string.addcontact_message_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.addcontact_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View rootView = layoutInflater.inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, rootView);

        builder.setView(rootView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(this);


        return alertDialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

        final AlertDialog alertDialog = (AlertDialog) getDialog();

        if (alertDialog != null) {

            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negetiveButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContactPresenter.addContact(inputEmail.getText().toString());
                }
            });

            negetiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        addContactPresenter.onShow();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addContactPresenter.onDestroy();
    }

    @Override
    public void showInput() {

        inputEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {

        inputEmail.setVisibility(View.GONE);
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
    public void contactAdded() {

        Toast.makeText(getActivity(), R.string.addcontact_message_contactadded, Toast.LENGTH_SHORT).show();
        dismiss();

    }

    @Override
    public void contactNotAdded() {

        inputEmail.setText("");
        inputEmail.setError(getString(R.string.addcontact_error_message));
    }
}