package com.example.jugid.skybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.jugid.skybuddy.Activities.StartActivity;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imgSplash = findViewById(R.id.imageSplash);

        Animation animSplash = AnimationUtils.loadAnimation(this, R.anim.splash_screen);
        imgSplash.startAnimation(animSplash);

        final Intent i = new Intent(this,StartActivity.class);

        Thread waitSplash = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        waitSplash.start();
    }
}
