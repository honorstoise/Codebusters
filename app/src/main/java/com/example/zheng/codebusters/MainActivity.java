package com.example.zheng.codebusters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView title = findViewById(R.id.imageView_title);
        ProgressBar bar = findViewById(R.id.progressBar);

        move((ImageView)findViewById(R.id.imageView_welcome));
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                fade((ImageView)findViewById(R.id.imageView_title));
                title.setVisibility(View.VISIBLE);
            }
            public void onTick(long millisUntilFinished) {

            }
        }.start();

        new CountDownTimer(3000, 1000) {
            public void onFinish() {
                bar.setVisibility(View.VISIBLE);
            }
            public void onTick(long millisUntilFinished) {

            }
        }.start();

        Random rNumber = new Random();
        new CountDownTimer(5000 + rNumber.nextInt(2000), 1000) {
            public void onFinish() {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(startIntent);
            }
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    //sets the move animation
    public void move(ImageView view){
        ImageView image = view;
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        image.startAnimation(animation);
    }

    //sets the fade animation
    public void fade(ImageView view){
        ImageView image = view;
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        image.startAnimation(animation);
    }
}