package com.example.movie2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movie2.Model.Cast;
import com.example.movie2.Model.Credits;
import com.example.movie2.Model.Crew;
import com.example.movie2.Model.Genre;
import com.example.movie2.Model.MovieDetails;
import com.example.movie2.Model.MovieItems;
import com.example.movie2.Model.ResponseObject;
import com.example.movie2.Model.Review;
import com.example.movie2.Model.SingleReview;
import com.google.gson.Gson;

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

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        initViews();
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();

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

        setGenre();


        txtRuntime.setText(String.valueOf(movieDetails.getRuntime()));

        castItemAdapter = new CastItemAdapter(this);

        castRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        castRecView.setAdapter(castItemAdapter);

        final Gson gson = new Gson();
        String url1 = "https://api.themoviedb.org/3/movie/" + String.valueOf(incomingItem.getId()) + "/credits?api_key=12cd0a8a7f3fab830b272438df172ea8";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Credits credits = new Credits();
                try {
                    credits = gson.fromJson(response, Credits.class);
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

                    Cast[] casts = credits.getCast();

                    ArrayList<Cast> castItems = new ArrayList<>();
                    try {
                        castItems.addAll(Arrays.asList(casts));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    castItemAdapter.setItems(castItems);

                } catch (com.google.gson.JsonSyntaxException e) {
                    e.printStackTrace();
                }


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


        btnAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (databaseHelper.insert(database, incomingItem)) {
                        Toast.makeText(MovieItemActivity.this, incomingItem.getTitle() + " is Added to your watch list successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieItemActivity.this);
                        builder.setMessage("You Already Added this Movie to your Watch List");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setCancelable(false);
                        builder.create().show();
                    }

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

            }
        });


        setReviews();
        setSimilarMovies();


    }

    private void setSimilarMovies() {
        Log.d(TAG, "setSimilarMovies: called");
        similarMovieItemAdapter = new MovieItemAdapter(this);
        moreLikeThisRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        moreLikeThisRecView.setAdapter(similarMovieItemAdapter);

        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + String.valueOf(incomingItem.getId()) + "/similar?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseObject responseObject = gson.fromJson(response, ResponseObject.class);
                //Log.d(TAG, "onResponse getSimilarItems : responseObject" + responseObject.toString());


                ArrayList<MovieItems> similarMovies = responseObject.getResults();

                if (similarMovies != null) {
                    if (!similarMovies.isEmpty()) {
                        similarMovieItemAdapter.setItems(similarMovies);
                    } else {
                        Toast.makeText(MovieItemActivity.this, "Unable to get similar movies ", Toast.LENGTH_SHORT).show();
                    }
                }


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

    }

    private void setReviews() {
        Log.d(TAG, "setReviews: called");
        reviewRecViewAdapter = new ReviewRecViewAdapter(this);
        reviewRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        reviewRecView.setAdapter(reviewRecViewAdapter);

        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + incomingItem.getId() + "/reviews?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: reviews " + response);

                Review review = gson.fromJson(response, Review.class);
                SingleReview[] reviews = review.getResults();

                Log.d(TAG, "setRecViews: reviews" + Arrays.toString(reviews));

                ArrayList<SingleReview> reviewItems = new ArrayList<>();
                try {
                    reviewItems.addAll(Arrays.asList(reviews));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (!reviewItems.isEmpty()) {
                    reviewRecViewAdapter.setItems(reviewItems);
                } else {
                    Toast.makeText(MovieItemActivity.this, "No Reviews found", Toast.LENGTH_SHORT).show();
                }

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


    }

    private void setGenre() {
        Utils utils = new Utils(this);

        genreItemAdapter = new GenreItemAdapter(this);
        genreRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genreRecView.setAdapter(genreItemAdapter);

        Genre[] genres = movieDetails.getGenres();

        ArrayList<Genre> genresItems = new ArrayList<>(Arrays.asList(genres));

        genreItemAdapter.setItems(genresItems);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
