package com.example.tadhkirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    ImageView bkiblat, bprayer, bmosque, bcounter, bdoazikir;
    String st;
    TextView tv;
    ImageButton logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        getSupportActionBar().hide();


        bkiblat =findViewById(R.id.kiblatbutton);
        bprayer =findViewById(R.id.prayersbutton);
        bmosque =findViewById(R.id.mosquebutton);
        bcounter =findViewById(R.id.tasbihcounterbutton);
        bdoazikir =findViewById(R.id.doazikirbutton);
        tv = findViewById(R.id.greetuser);
        logout = findViewById(R.id.logout);

        st=getIntent().getExtras().getString("Hello");
        tv.setText(""+st);

        bkiblat.setOnClickListener(view -> {
            Intent in = new Intent(MainPageActivity.this,QiblatActivity.class);
            startActivity(in);
        });

        bprayer.setOnClickListener(view -> {
            Intent in = new Intent(MainPageActivity.this,PrayerTimesActivity.class);
            startActivity(in);
        });

        bmosque.setOnClickListener(view -> {
            Intent in = new Intent(MainPageActivity.this, MosqueLocationActivity.class);
            startActivity(in);
        });

        bcounter.setOnClickListener(view -> {
            Intent in = new Intent(MainPageActivity.this,TasbihCounterActivity.class);
            startActivity(in);
        });

        bdoazikir.setOnClickListener(view -> {
            Intent in = new Intent(MainPageActivity.this,DoaZikirActivity.class);
            startActivity(in);
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainPageActivity.this,StartActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
}