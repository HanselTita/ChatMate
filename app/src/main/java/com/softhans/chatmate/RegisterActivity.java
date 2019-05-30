package com.softhans.chatmate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountBtn;

    private EditText UserEmail, Userpwd;
    private TextView AlreadyHaveAccountLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFiends();

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToLoginActivity();
            }
        });

    }

    private void InitializeFiends()
    {

        CreateAccountBtn = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText)findViewById(R.id.register_email);
        Userpwd = (EditText)findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_an_account_link);

    }

    private void sendUserToLoginActivity()
    {

        Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
    }

}
