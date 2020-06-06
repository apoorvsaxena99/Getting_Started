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
            Intent mainActivity=new Intent(getApplicationContext(),Home.class);
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
        mList.add(new ScreenItem("Driving Intellects", "E-school mainly pays attention on the welfare of students providing them the best of everything.", R.drawable.e_1));
        mList.add(new ScreenItem("Placement Assistance", "We inaugurated a new hub and our techno world began. Software developments, free lancing, hiring interns, placement of our trainees. All in one bowl. A huge success for us.", R.drawable.e_2));
        mList.add(new ScreenItem("Easy Learning", "Be it the training program, be it placement, be it infrastructure, or be it faculty", R.drawable.e_3));


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
                Intent in=new Intent(GettingStarted.this,Home.class);
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
