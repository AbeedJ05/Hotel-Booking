package com.example.hotelbooking.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hotelbooking.Activities.MainActivity;
import com.example.hotelbooking.Fragments.HomeFragment;
import com.example.hotelbooking.R;

public class intro_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_admin);

        Button adminButton = findViewById(R.id.admin);
        Button userModeButton = findViewById(R.id.userMode);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  Create an intent to start the TargetActivity
                Intent intent = new Intent( intro_Admin.this, Admin.class);
                startActivity(intent);
            }
        });

        userModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the TargetActivity
                Intent intent = new Intent( intro_Admin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    }
