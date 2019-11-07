package com.example.jugid.skybuddy.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewAboutPagerAdapter extends FragmentPagerAdapter
{

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitles = new ArrayList<>();

    public ViewAboutPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return listTitles.get(position);
    }

    public void addFragment(Fragment frag,String title){
        listFragment.add(frag);
        listTitles.add(title);
    }

}
