package com.example.plasmadonator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class forgotpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action));}
    }
}
