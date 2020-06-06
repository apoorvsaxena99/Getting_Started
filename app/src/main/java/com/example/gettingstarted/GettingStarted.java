package com.example.gettingstarted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GettingStarted extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnnext,getstarted;
    int position=0;
    Animation btnanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on fullscreen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //when this activity is about to be launch we need to check if its opened before or not

        if(restorePrefData())
        {
            Intent mainActivity=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainActivity);
            finish();
        }


        setContentView(R.layout.activity_getting_started);

        //hide the action bar
        getSupportActionBar().hide();

        //ini views
        btnnext = findViewById(R.id.next);
        getstarted=findViewById(R.id.gettingstarted);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnanimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        //fill list items
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food", "We Ship Anywhere in the World. Get a Free Freight Quote Instantly. We Are In The Business Of Keeping Promises", R.drawable.img1));
        mList.add(new ScreenItem("Fast Delivery", "Fast, Easy & Flexible. Container Quotes Available 24/7. Try Online Quotation Today. Instant Quotes In Less Than 30 Seconds - Without Waiting. Fast & Flexible. Try it now! 24/7 Availability. Fast & Flexible. Real Time Quotes. Transparency On The Rates", R.drawable.img2));
        mList.add(new ScreenItem("Easy Payments", "In it's purest form, an act of retribution provides symmetry. The rendering payment of crimes against the innocent. But a danger on retaliation lies on the furthering cycle of violence. Still, it's a risk that must be met; and the greater offense is to allow the guilty go unpunished", R.drawable.img3));


        //setup viewpager
        screenPager = findViewById(R.id.viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);
        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click listener
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()-1) //when we reach to the last screen
                {
                    //TODO:show the getstarted button and hide the indicator next button
                    loadlastScreen();
                }
            }
        });

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1)
                {
                    loadlastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(GettingStarted.this,MainActivity.class);
                startActivity(in);
                //also we need to save a boolean value storage so next time when the user run the app
                //we could know that he is already checked the getting started activity via using sharedprefences
                savePrefsData();
                finish();
                }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isGettingStartedOpenedBefore=pref.getBoolean("isGettingStarted",false);
        return isGettingStartedOpenedBefore;
    }


    private void savePrefsData() {

        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isGettingStarted",true);
        editor.commit();
    }

    //show the getstarted button and hide the indicator next button
    private void loadlastScreen(){

        btnnext.setVisibility(View.INVISIBLE);
        getstarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //Todo : Add Animation in getstarted button

        //setup animation
        getstarted.setAnimation(btnanimation);



    }
}
