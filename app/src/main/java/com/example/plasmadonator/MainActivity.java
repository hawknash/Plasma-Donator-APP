package com.example.plasmadonator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topAnimantion,middleAnimation;
    ImageView image;
    TextView txt,txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        txt1=findViewById(R.id.txt2);
        image=findViewById(R.id.image);
        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle);
        image.setAnimation(topAnimantion);
        txt.setAnimation(topAnimantion);
        txt1.setAnimation(middleAnimation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
