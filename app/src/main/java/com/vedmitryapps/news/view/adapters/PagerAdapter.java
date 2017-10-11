package com.vedmitryapps.news.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vedmitryapps.news.view.fragments.StoriesFragment;


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StoriesFragment();
            case 1:
                return new StoriesFragment();
            case 2:
                return new StoriesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "STORIES";
            case 1:
                return "VIDEO";
            case 2:
                return "FAVOURITES";
        }
        return null;
    }




}