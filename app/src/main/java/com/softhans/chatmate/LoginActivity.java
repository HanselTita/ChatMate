package com.softhans.chatmate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseUser currentUser;

    private Button LoginBtn, PhoneLoginBtn;

    private EditText UserEmail, Userpwd;
    private TextView NeednewAccountLink, ForgetPwdLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        InitializeFiends();

        NeednewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              sendUserToRegisterActivity();
            }
        });
    }


    private void InitializeFiends()
    {

        LoginBtn = (Button) findViewById(R.id.login_button);
        PhoneLoginBtn = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText)findViewById(R.id.login_email);
        Userpwd = (EditText)findViewById(R.id.login_password);


        NeednewAccountLink = (TextView) findViewById(R.id.create_an_account_link);
        ForgetPwdLink = (TextView) findViewById(R.id.forget_password_link);

    }

    @Override
    protected void onStart() {

        super.onStart();

        if(currentUser != null)
        {
            sendUserToMainActivity();



        }
    }

    private void sendUserToMainActivity()
    {

        Intent LoginIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(LoginIntent);
    }

    private void sendUserToRegisterActivity()
    {

        Intent RegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(RegisterIntent);
    }
}
