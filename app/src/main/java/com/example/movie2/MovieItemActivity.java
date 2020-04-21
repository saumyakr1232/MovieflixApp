package com.example.movie2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Model.MovieDetails;
import com.example.movie2.Model.MovieItems;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MovieItemActivity extends AppCompatActivity {
    private static final String TAG = "MovieItemActivity";
    private Button btnAddToWatchList;
    private ImageView itemImageBack, imgItemPoster;
    private RecyclerView genreRecView, castRecView, reviewRecView, moreLikeThisRecView;
    private TextView txtTitleOfMovie, txtYOR, txtRuntime, txtDescription, txtRatingOutOf, txtNoOfpeopleRated, txtMetascore, txtDetailsDirectors, txtDetailsWriters, txtAddReview;
    private RelativeLayout rateThisRelLayout;

    private MovieItemAdapter similarMovieItemAdapter;


    private MovieDetails movieDetails;
    private MovieItems incomingItem;
    private String incomingId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        initViews();

        Intent intent = getIntent();
        try {
            incomingItem = intent.getParcelableExtra("item");
            setViewsValues(this);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();


    }

    private void setViewsValues(Context context) {
        Log.d(TAG, "setViewsValues: started");
        String url = "https://image.tmdb.org/t/p/w1280";
        String urlBack = "https://image.tmdb.org/t/p/w1066_and_h600_bestv2";
        Glide.with(this)
                .asBitmap()
                .load(url + incomingItem.getPoster_path())
                .into(imgItemPoster);
        txtTitleOfMovie.setText(incomingItem.getTitle());
        Glide.with(this)
                .asBitmap()
                .load(urlBack + incomingItem.getBackdrop_path())
                .into(itemImageBack);
        //TODO: reformat the date to year only
        txtYOR.setText(incomingItem.getRelease_date());
        //TODO: set runtime
        txtDescription.setText(incomingItem.getOverview());
        txtRatingOutOf.setText(String.valueOf(incomingItem.getVote_average()) + "\10");
        txtNoOfpeopleRated.setText(String.valueOf(incomingItem.getVote_count()));
        txtMetascore.setText(String.valueOf((int) incomingItem.getPopularity()));
        rateThisRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : RAte item
            }
        });
        Utils utils = new Utils(this);
        movieDetails = utils.getMovieDetails(String.valueOf(incomingItem.getId()));
        setRecViews();
        txtRuntime.setText(String.valueOf(movieDetails.getRuntime()));


    }


    private void setRecViews() {
        Log.d(TAG, "setRecViews: called");
        Log.d(TAG, "setRecViews: incoming item" + String.valueOf(incomingItem.getId()));
        Utils utils = new Utils(this);
        ArrayList<MovieItems> similarItems = utils.getSimilarItems(String.valueOf(incomingItem.getId()));


        similarMovieItemAdapter = new MovieItemAdapter(this);
        moreLikeThisRecView.setAdapter(similarMovieItemAdapter);
        moreLikeThisRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        if (similarItems != null) {
            similarMovieItemAdapter.setItems(similarItems);
        } else {
            utils.findSimilarMovies(String.valueOf(incomingItem.getId()));
        }
    }


    private void initViews() {
        Log.d(TAG, "initViews: called");
        btnAddToWatchList = (Button) findViewById(R.id.btnAddToWatchList);
        itemImageBack = (ImageView) findViewById(R.id.itemImage);
        imgItemPoster = (ImageView) findViewById(R.id.imgItemPoster);
        genreRecView = (RecyclerView) findViewById(R.id.genreRecView);
        castRecView = (RecyclerView) findViewById(R.id.castRecView);
        reviewRecView = (RecyclerView) findViewById(R.id.reviewRecView);
        moreLikeThisRecView = (RecyclerView) findViewById(R.id.moreLikeThisRecView);
        txtTitleOfMovie = (TextView) findViewById(R.id.txtTitleOfMovie);
        txtYOR = (TextView) findViewById(R.id.txtYOR);
        txtRuntime = (TextView) findViewById(R.id.txtRuntime);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtRatingOutOf = (TextView) findViewById(R.id.txtRatingOutOf);
        txtNoOfpeopleRated = (TextView) findViewById(R.id.txtNoOfpeopleRated);
        txtMetascore = (TextView) findViewById(R.id.txtMetascore);
        txtDetailsDirectors = (TextView) findViewById(R.id.txtDetailsDirectors);
        txtDetailsWriters = (TextView) findViewById(R.id.txtDetailsWriters);
        txtAddReview = (TextView) findViewById(R.id.txtAddReviews);
        rateThisRelLayout = (RelativeLayout) findViewById(R.id.rateThisRelLayout);


    }
}
