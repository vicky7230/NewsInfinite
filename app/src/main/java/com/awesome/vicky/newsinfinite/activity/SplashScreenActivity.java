package com.awesome.vicky.newsinfinite.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.awesome.vicky.newsinfinite.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private String fontPath;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fontPath = "fonts/NimbusRomNo9L-Reg.otf";
        typeface = Typeface.createFromAsset(getAssets(), fontPath);
        ((TextView)findViewById(R.id.news_label)).setTypeface(typeface);
        ((TextView)findViewById(R.id.infinte_label)).setTypeface(typeface);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                Intent intent;
                if (isFirstStart) {
                    intent = new Intent(SplashScreenActivity.this, IntroActivity.class);
                    SharedPreferences.Editor editor = getPrefs.edit();
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                } else
                    intent = new Intent(SplashScreenActivity.this, ContainerActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
