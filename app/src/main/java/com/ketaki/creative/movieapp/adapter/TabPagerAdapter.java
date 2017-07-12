package com.ketaki.creative.movieapp.adapter;

/**
 * Created by gunke001 on 7/8/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ketaki.creative.movieapp.PopularMovieFragment;
import com.ketaki.creative.movieapp.TopRatedMovieFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.tabCount = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PopularMovieFragment popularMovieFragment = new PopularMovieFragment();
                return popularMovieFragment;
            case 1:
                TopRatedMovieFragment topRatedMovieFragment = new TopRatedMovieFragment();
                return topRatedMovieFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}