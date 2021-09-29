package com.example.movie2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Database.LocalStorageDb;
import com.example.movie2.Model.MovieItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WantToWatch extends AppCompatActivity implements MovieItemAdapter.DeleteMovie, MovieItemAdapter.AddMovie {
    private static final String TAG = "WantToWatch";
    private RecyclerView recyclerView;
    private MovieItemAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private LocalStorageDb localStorageDb;
    private Cursor cursor;
    private SQLiteDatabase database;

    private ArrayList<MovieItem> wantToWatchList;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_watch);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        wantToWatchList = new ArrayList<>();
        initBottomNavigation();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new MovieItemAdapter(this);
        utils = new Utils(this);


        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.setType("want to watch");
        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();

    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: created");
        bottomNavigationView.setSelectedItemId(R.id.myList);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.search:
                        Intent intent1 = new Intent(WantToWatch.this, SearchActivity.class);
                        startActivity(intent1);
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

    @Override
    public void onDeletingResult(MovieItem movie) {
        Log.d(TAG, "onDeletingResult: trying to delete movie " + movie.toString());
        localStorageDb.delete(database, movie);
        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();
        Toast.makeText(this, movie.getTitle() + " removed from your watchlist", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddingResult(MovieItem movie) {
        Log.d(TAG, "onAddingResult: movie" + movie.getTitle());
        try {
            localStorageDb.insert(database, movie);
            DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
            databaseAsyncTask.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class DatabaseAsyncTask extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clearItems();

            localStorageDb = new LocalStorageDb(WantToWatch.this);
            database = localStorageDb.getReadableDatabase();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: called");

            try {
                cursor = database.query("movies", null, null, null, null, null
                        , null);

                if (cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        MovieItem movieItem = new MovieItem();
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            if (cursor.getColumnName(j).equals("movie")) {

                                Gson gson = new Gson();
                                movieItem = gson.fromJson(cursor.getString(j), MovieItem.class);


                            }

                        }
                        Log.d(TAG, "doInBackground: movieItem " + movieItem.toString());
                        wantToWatchList.add(movieItem);
                        cursor.moveToNext();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter.setItems(wantToWatchList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
