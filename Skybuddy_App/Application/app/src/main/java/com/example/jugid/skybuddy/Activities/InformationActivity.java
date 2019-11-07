package com.example.jugid.skybuddy.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jugid.skybuddy.Adapters.ViewAboutPagerAdapter;
import com.example.jugid.skybuddy.Fragments.AboutSocialFragment;
import com.example.jugid.skybuddy.Fragments.GoalFragment;
import com.example.jugid.skybuddy.Fragments.IdeaFragment;
import com.example.jugid.skybuddy.R;

public class InformationActivity extends AppCompatActivity
        implements AboutSocialFragment.OnFragmentInteractionListener, GoalFragment.OnFragmentInteractionListener, IdeaFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        ViewPager viewPager = findViewById(R.id.viewpager_about);

        ViewAboutPagerAdapter adapter = new ViewAboutPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(IdeaFragment.newInstance("Idea","Fragment"),"IdeaFragment");
        adapter.addFragment(AboutSocialFragment.newInstance("Social","Fragment"),"SocialFragment");
        adapter.addFragment(GoalFragment.newInstance("Goal","Fragment"),"GoalFragment");


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
