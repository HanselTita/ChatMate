package com.softhans.chatmate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;

    private Button LoginBtn, PhoneLoginBtn;

    private EditText UserEmail, Userpwd;
    private TextView NeednewAccountLink, ForgetPwdLink;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth=FirebaseAuth.getInstance();



        InitializeFiends();

        NeednewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              sendUserToRegisterActivity();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin()

    {
        String email = UserEmail.getText().toString();
        String password = Userpwd.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Signing In...");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                sendUserToMainActivity();
                                Toast.makeText(LoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    private void InitializeFiends()
    {

        LoginBtn = (Button) findViewById(R.id.login_button);
        PhoneLoginBtn = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText)findViewById(R.id.login_email);
        Userpwd = (EditText)findViewById(R.id.login_password);


        NeednewAccountLink = (TextView) findViewById(R.id.create_an_account_link);
        ForgetPwdLink = (TextView) findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);


    }



    private void sendUserToMainActivity()
    {

        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToRegisterActivity()
    {

        Intent RegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(RegisterIntent);
    }
}
