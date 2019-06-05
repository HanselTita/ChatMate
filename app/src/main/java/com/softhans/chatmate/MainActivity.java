package com.softhans.chatmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private ViewPager myViewPAger;
    private TabLayout myTabLayout;
    private TabsAccessorAdapter myTabAccessorAdapter;

    private FirebaseUser currentUser;
    private FirebaseAuth mAtuh;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAtuh = FirebaseAuth.getInstance();
        currentUser = mAtuh.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();


        mtoolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("ChatMate");


        myViewPAger = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPAger.setAdapter(myTabAccessorAdapter);

        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPAger);
    }

    @Override
    protected void onStart() {

        super.onStart();

        if(currentUser ==null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            VerifyUserExistance();
        }
    }

    private void VerifyUserExistance()
    {
        String currentUserId = mAtuh.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("name").exists()))
                {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendUserToSettingsActivity();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.main_logout_option)
         {
            mAtuh.signOut();
            sendUserToLoginActivity();

         }

        if(item.getItemId() == R.id.main_settings_option)
        {
            sendUserToSettingsActivity();
        }

        if(item.getItemId() == R.id.main_find_friends_option)
        {

        }

        return true;
    }


    private void sendUserToLoginActivity() {
        Intent LoginIntent = new Intent(MainActivity.this,LoginActivity.class);
        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LoginIntent);
        finish();
    }

    private void sendUserToSettingsActivity() {
        Intent SettingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
        SettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SettingsIntent);
        finish();
    }

}
