package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie2.Model.MovieItems;
import com.example.movie2.Model.ResponseObject;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GenreActivity extends AppCompatActivity {

    private TextView txtGenre;
    private RecyclerView genreMovieRecView;

    private Utils utils;
    private MovieItemAdapter movieItemAdapter;

    private ArrayList<MovieItems> genreMovies;

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        genreMovies = new ArrayList<>();

        txtGenre = (TextView) findViewById(R.id.GenreName);
        genreMovieRecView = (RecyclerView) findViewById(R.id.movieByGenreRecView);

        Intent intent = getIntent();
        int id = intent.getIntExtra("incomingGenre", 0);
        String name = intent.getStringExtra("GenreName");
        utils = new Utils(this);
        movieItemAdapter = new MovieItemAdapter(this);
        genreMovieRecView.setLayoutManager(new GridLayoutManager(this, 2));
        genreMovieRecView.setAdapter(movieItemAdapter);

        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseObject responseObject = gson.fromJson(response, ResponseObject.class);
                //Log.d(TAG, "onResponse: GenreMovies " + responseObject.toString());
                genreMovies = responseObject.getResults();
                movieItemAdapter.setItems(genreMovies);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.start();


        txtGenre.setText(name);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Back) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
