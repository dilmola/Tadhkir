package com.example.tadhkirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();


        start =findViewById(R.id.startbtn);

        start.setOnClickListener(view -> {
            Intent in = new Intent(StartActivity.this,LoginActivity.class);
            startActivity(in);
        });
    }
}