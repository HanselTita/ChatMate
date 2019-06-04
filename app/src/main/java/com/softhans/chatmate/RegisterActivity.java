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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountBtn;

    private EditText UserEmail, Userpwd;
    private TextView AlreadyHaveAccountLink;


    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFiends();

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToLoginActivity();
            }
        });


        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
              CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount()

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

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while we create new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                           if (task.isSuccessful())
                           {

                               String currentUserID =mAuth.getCurrentUser().getUid();
                               RootRef.child("Users").child(currentUserID).setValue("");

                               sendUserToMainActivity();
                               Toast.makeText(RegisterActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                           }

                           else
                           {
                               String message = task.getException().toString();
                               Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                           }
                        }
                    });
        }

    }

    private void InitializeFiends()
    {

        CreateAccountBtn = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText)findViewById(R.id.register_email);
        Userpwd = (EditText)findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_an_account_link);

        loadingBar = new ProgressDialog(this);
    }

    private void sendUserToLoginActivity()
    {

        Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
    }

    private void sendUserToMainActivity()
    {

        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

}
