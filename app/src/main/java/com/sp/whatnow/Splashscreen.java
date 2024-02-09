package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Handler handler = new Handler();
        Glide.init(this, new GlideBuilder());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(Splashscreen.this,StartupActivity.class);
                startActivity(intent);
                Splashscreen.this.finish();

            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        intent = new Intent(Splashscreen.this,StartupActivity.class);
        startActivity(intent);
        Splashscreen.this.finish();
    }
}