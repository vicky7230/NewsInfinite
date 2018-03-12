package com.awesome.vicky.newsinfinite.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.awesome.vicky.newsinfinite.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {


    @Override
    public void init(Bundle savedInstanceState) {
        int color = Color.parseColor("#4DAF51");
        addSlide(AppIntroFragment.newInstance("For News Junkies", "Tons of news from thegaurdian.com", R.drawable.clock, color));
        addSlide(AppIntroFragment.newInstance("Categorical news", "Pick the category and read the news", R.drawable.menu, color));
        addSlide(AppIntroFragment.newInstance("Search news", "Search from any article", R.drawable.search, color));
    }

    @Override
    public void onSkipPressed() {
        startActivity(new Intent(this, ContainerActivity.class));
        finish();

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this, ContainerActivity.class));
        finish();

    }

    @Override
    public void onSlideChanged() {

    }
}
