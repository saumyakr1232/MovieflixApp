package com.example.movie2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Model.Cast;
import com.example.movie2.Model.Credits;
import com.example.movie2.Model.Crew;
import com.example.movie2.Model.Genre;
import com.example.movie2.Model.MovieDetails;
import com.example.movie2.Model.MovieItems;
import com.example.movie2.Model.Review;
import com.example.movie2.Model.SingleReview;

import java.util.ArrayList;
import java.util.Arrays;


public class MovieItemActivity extends AppCompatActivity {
    private static final String TAG = "MovieItemActivity";
    private Button btnAddToWatchList;
    private ImageView itemImageBack, imgItemPoster;
    private RecyclerView genreRecView, castRecView, reviewRecView, moreLikeThisRecView;
    private TextView txtTitleOfMovie, txtYOR, txtRuntime, txtDescription, txtRatingOutOf, txtNoOfpeopleRated, txtMetascore, txtDetailsDirectors, txtDetailsWriters, txtAddReview;
    private RelativeLayout rateThisRelLayout;

    private MovieItemAdapter similarMovieItemAdapter;
    private CastItemAdapter castItemAdapter;
    private GenreItemAdapter genreItemAdapter;
    private ReviewRecViewAdapter reviewRecViewAdapter;


    private MovieDetails movieDetails;
    private MovieItems incomingItem;

    Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        initViews();

        Intent intent = getIntent();
        try {
            incomingItem = intent.getParcelableExtra("item");
            setViewsValues();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    @SuppressLint("SetTextI18n")
    private void setViewsValues() {
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
        utils = new Utils(this);

        movieDetails = utils.getMovieDetails(String.valueOf(incomingItem.getId()));


        txtRuntime.setText(String.valueOf(movieDetails.getRuntime()));

        Credits credits = utils.getCredits(String.valueOf(incomingItem.getId()));
        Crew[] crews = credits.getCrew();

        if (crews != null) {
            for (Crew crew : crews
            ) {
                if (crew.getJob().equals("Director")) {
                    txtDetailsDirectors.setText(crew.getName());
                    break;
                }

            }
            for (Crew crew : crews
            ) {
                if (crew.getJob().equals("Screenplay")) {
                    txtDetailsWriters.setText(crew.getName());
                    break;
                }

            }
        }

        btnAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems item = incomingItem;

                ArrayList<MovieItems> wantToWatch = utils.getWantToWatchMovies();
                if (wantToWatch != null) {
                    Log.d(TAG, "onClick: wantToWatch " + wantToWatch.toString());


                    boolean flag = false;

                    for (MovieItems i : wantToWatch
                    ) {
                        if (i.getId() == item.getId()) {
                            flag = true;
                            break;
                        }

                    }
                    if (flag) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieItemActivity.this);
                        builder.setMessage("You Already Added this Movie to your Watch List");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setCancelable(false);
                        builder.create().show();


                    } else {
                        utils.addToWantToWatchMovies(item);
                        Toast.makeText(MovieItemActivity.this, incomingItem.getTitle() + "is added to your Watch List", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    utils.addToWantToWatchMovies(item);
                    Toast.makeText(MovieItemActivity.this, incomingItem.getTitle() + "is added to your Watch List", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setRecViews();


    }


    private void setRecViews() {
        Log.d(TAG, "setRecViews: called");
        Log.d(TAG, "setRecViews: incoming item" + String.valueOf(incomingItem.getId()));
        Utils utils = new Utils(this);

        castItemAdapter = new CastItemAdapter(this);

        castRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        castRecView.setAdapter(castItemAdapter);

        Credits credits = utils.getCredits(String.valueOf(incomingItem.getId()));

        Cast[] casts = credits.getCast();

        ArrayList<Cast> castItems = new ArrayList<>();
        try {
            castItems.addAll(Arrays.asList(casts));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        castItemAdapter.setItems(castItems);
        castItemAdapter.notifyDataSetChanged();

        ArrayList<MovieItems> similarItems = utils.getSimilarItems(String.valueOf(incomingItem.getId()));


        similarMovieItemAdapter = new MovieItemAdapter(this);
        moreLikeThisRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        moreLikeThisRecView.setAdapter(similarMovieItemAdapter);

        if (similarItems != null) {
            similarMovieItemAdapter.setItems(similarItems);
            similarMovieItemAdapter.notifyDataSetChanged();
        } else {
            utils.findSimilarMovies(String.valueOf(incomingItem.getId()));
        }

        genreItemAdapter = new GenreItemAdapter(this);
        genreRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genreRecView.setAdapter(genreItemAdapter);

        Genre[] genres = movieDetails.getGenres();

        ArrayList<Genre> genresItems = new ArrayList<>(Arrays.asList(genres));

        genreItemAdapter.setItems(genresItems);
        genreItemAdapter.notifyDataSetChanged();

        reviewRecViewAdapter = new ReviewRecViewAdapter(this);
        reviewRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        reviewRecView.setAdapter(reviewRecViewAdapter);
        Review review = utils.getReviews(String.valueOf(incomingItem.getId()));

        SingleReview[] reviews = review.getResults();

        Log.d(TAG, "setRecViews: reviews" + Arrays.toString(reviews));

        ArrayList<SingleReview> reviewItems = new ArrayList<>();
        try {
            reviewItems.addAll(Arrays.asList(reviews));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        reviewRecViewAdapter.setItems(reviewItems);
        reviewRecViewAdapter.notifyDataSetChanged();

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
