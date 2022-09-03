package com.example.tadhkirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TasbihCounterActivity extends AppCompatActivity {
    ImageView count;
    Button reset;
    TextView num;
    int numberClick = 0;
    ImageButton previousbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih_counter);
        getSupportActionBar().hide();

        ImageView count = findViewById(R.id.count);
        Button reset = findViewById(R.id.reset);
        TextView num = findViewById(R.id.num);

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberClick++;
                num.setText(Integer.toString(numberClick));
            }

        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick = 0;
                num.setText(String.valueOf(numberClick));
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

    public void previous(View view) {

        Intent intent = new Intent(TasbihCounterActivity.this, MainPageActivity.class);
        startActivity(intent);
    }
}