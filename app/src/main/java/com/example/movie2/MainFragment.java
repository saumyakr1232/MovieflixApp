package com.example.movie2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private BottomNavigationView bottomNavigationView;

    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView, trendingRecView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentt_main, container, false);
        initViews(view);

        initBottomNavigation();

        return view;
    }

    @Override
    public void onResume() {
        updateRecView();
        super.onResume();
    }

    private void updateRecView() {
        //TODO: update recyclar views
    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: created");
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //TODO: logic for navigation
                    case R.id.search:
                        Toast.makeText(getContext(), "search clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myList:
                        Toast.makeText(getContext(), "cart clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.homeActivity:
                        Toast.makeText(getContext(), "home clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: created");
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        newItemsRecView = (RecyclerView) view.findViewById(R.id.newItemsRecView);
        popularItemsRecView = (RecyclerView) view.findViewById(R.id.popularMovies);
        suggestedItemsRecView = (RecyclerView) view.findViewById(R.id.suggestedItems);
        trendingRecView = (RecyclerView) view.findViewById(R.id.trendingRecView);
    }
}
