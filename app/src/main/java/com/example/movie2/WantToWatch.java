package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.MovieItems;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class WantToWatch extends AppCompatActivity {
    private static final String TAG = "WantToWatch";
    private RecyclerView recyclerView;
    private MovieItemAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private ArrayList<MovieItems> wantToWatchList;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_watch);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        initBottomNavigation();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new MovieItemAdapter(this);
        utils = new Utils(this);


        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.setType("want to watch");

        wantToWatchList = utils.getWantToWatchMovies();
        if (wantToWatchList != null) {
            adapter.setItems(wantToWatchList);
        } else {
            Toast.makeText(this, "Want to Watch List is empty", Toast.LENGTH_SHORT).show();
        }

    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: created");
        bottomNavigationView.setSelectedItemId(R.id.myList);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //TODO: logic for navigation
                    case R.id.search:
                        Toast.makeText(WantToWatch.this, "search selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myList:
                        break;
                    case R.id.homeActivity:
                        Intent intent = new Intent(WantToWatch.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

}
