package com.example.movie2;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.MovieItems;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private BottomNavigationView bottomNavigationView;

    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView, trendingRecView;

    private MovieItemAdapter newMoviesItemAdapter, TrendingMoviesItemAdapter, suggestedMovieItemAdapter, popularMoviesItemAdapter;

    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentt_main, container, false);
        initViews(view);

        initBottomNavigation();

        utils = new Utils(getActivity());
        utils.initDataBase();


        initRecView();


        return view;
    }

    private void initRecView() {
        Log.d(TAG, "initRecView: called");

        newMoviesItemAdapter = new MovieItemAdapter(getActivity());
        TrendingMoviesItemAdapter = new MovieItemAdapter(getActivity());
        suggestedMovieItemAdapter = new MovieItemAdapter(getActivity());
        popularMoviesItemAdapter = new MovieItemAdapter(getActivity());

        newItemsRecView.setAdapter(newMoviesItemAdapter);
        trendingRecView.setAdapter(TrendingMoviesItemAdapter);
        suggestedItemsRecView.setAdapter(suggestedMovieItemAdapter);
        popularItemsRecView.setAdapter(popularMoviesItemAdapter);

        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        trendingRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));


        updateRecView();
    }

    private void updateRecView() {
        Log.d(TAG, "updateRecView: called");

        ArrayList<MovieItems> allItems = utils.getAllItems();
        ArrayList<MovieItems> newItems = utils.getNewItems();
        ArrayList<MovieItems> trendingItems = utils.getTrendingItems();
        if (null != newItems) {
            newMoviesItemAdapter.setItems(newItems);
            suggestedMovieItemAdapter.setItems(newItems);
            suggestedMovieItemAdapter.notifyDataSetChanged();
            newMoviesItemAdapter.notifyDataSetChanged();
        }
        if (null != trendingItems) {
            TrendingMoviesItemAdapter.setItems(trendingItems);
            TrendingMoviesItemAdapter.notifyDataSetChanged();
        }

        Comparator<MovieItems> popularityComparator = new Comparator<MovieItems>() {
            @Override
            public int compare(MovieItems o1, MovieItems o2) {
                return comparePopularity(o1, o2);
            }
        };
        Comparator<MovieItems> reversePopularityPoint = Collections.reverseOrder(popularityComparator);
        if (allItems != null) {
            Collections.sort(allItems, reversePopularityPoint);
            popularMoviesItemAdapter.setItems(allItems);
            popularMoviesItemAdapter.notifyDataSetChanged();
        }
    }

    private int comparePopularity(MovieItems item1, MovieItems item2) {
        Log.d(TAG, "comparePopularity: called");
        if (item1.getPopularity() > item2.getPopularity()) {
            return 1;
        } else if (item1.getPopularity() < item2.getPopularity()) {
            return -1;

        } else {
            return 0;
        }


    }


    @Override
    public void onResume() {
        updateRecView();
        super.onResume();
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
                        Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.myList:
                        Toast.makeText(getContext(), "my list", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), WantToWatch.class);
                        startActivity(intent);

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
