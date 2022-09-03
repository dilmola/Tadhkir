package com.example.tadhkirapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    DBHelper DB;
    TextView regtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        DB = new DBHelper(this);
        regtxt = (TextView) findViewById(R.id.signuptxt);

        String text = "Don't have an account ? Sign-up";

        SpannableString ss = new SpannableString(text);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(LoginActivity.this,"Please Enter all Information",Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkuserpasss = DB.checkusernamepassword(user,pass);
                    if(checkuserpasss==true){
                        Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
                        intent.putExtra("Hello",user);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid",Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });



        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent in = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(in);
            }
        };

        ss.setSpan(clickableSpan, 24, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        regtxt.setText(ss);
        regtxt.setMovementMethod(LinkMovementMethod.getInstance());

    }
}