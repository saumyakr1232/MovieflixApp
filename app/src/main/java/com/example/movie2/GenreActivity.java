package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.MovieItems;

import java.util.ArrayList;

public class GenreActivity extends AppCompatActivity {

    private TextView txtGenre;
    private RecyclerView genreMovieRecView;

    private Utils utils;
    private MovieItemAdapter movieItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        txtGenre = (TextView) findViewById(R.id.GenreName);
        genreMovieRecView = (RecyclerView) findViewById(R.id.movieByGenreRecView);

        Intent intent = getIntent();
        int id = intent.getIntExtra("incomingGenre", 0);
        String name = intent.getStringExtra("GenreName");
        utils = new Utils(this);
        movieItemAdapter = new MovieItemAdapter(this);
        genreMovieRecView.setLayoutManager(new GridLayoutManager(this, 2));
        genreMovieRecView.setAdapter(movieItemAdapter);

        ArrayList<MovieItems> items = utils.getMoviesByGenres(String.valueOf(id));

        movieItemAdapter.setItems(items);
        txtGenre.setText(name);


    }
}
