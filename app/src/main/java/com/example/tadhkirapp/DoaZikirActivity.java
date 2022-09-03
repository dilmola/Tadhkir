package com.example.tadhkirapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DoaZikirActivity extends AppCompatActivity {

    Button  doabtn , zikirbtn;
    ImageButton previousbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_zikir);
        getSupportActionBar().hide();

        doabtn = findViewById(R.id.btnDoa);
        zikirbtn = findViewById(R.id.btnZikir);

        doabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new DoaActivity());
            }
        });
        zikirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ZikirActivity());

            }
        });

        previousbtn = findViewById(R.id.back_button);

        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    public void previous(View view) {

        Intent intent = new Intent(DoaZikirActivity.this, MainPageActivity.class);
        startActivity(intent);
    }

}