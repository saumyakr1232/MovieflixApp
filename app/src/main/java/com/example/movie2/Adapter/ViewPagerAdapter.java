package com.example.movie2.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.movie2.Fragments.MoviesFragment;
import com.example.movie2.Fragments.MyListFragment;
import com.example.movie2.Fragments.TVShowFragment;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int totalTabs = 0;
    private ArrayList<String> tabNames = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
        tabNames.add("Movies");
        tabNames.add("TV shows");
        tabNames.add("My List");


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MoviesFragment();
            case 1:
                return new TVShowFragment();
            case 2:
                return new MyListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
