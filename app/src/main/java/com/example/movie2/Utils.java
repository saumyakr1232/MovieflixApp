package com.example.movie2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie2.Model.Credits;
import com.example.movie2.Model.MovieDetails;
import com.example.movie2.Model.MovieItems;
import com.example.movie2.Model.ResponseObject;
import com.example.movie2.Model.Review;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    private static final String TAG = "Utils";
    public static final String DATABASE_NAME = "fake_database";
    private Context context;

    private ArrayList<MovieItems> wantToWatchItems = new ArrayList<>();

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public Utils(Context context) {
        this.context = context;


    }

    public void initDataBase() {
        Log.d(TAG, "initDataBase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType(); //creating this type just to pass in gson
        initAllItems();


    }

    public void initAllItems() {
        Log.d(TAG, "initAllItems: created");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<ResponseObject> getAllMovies = retrofitClient.getAllMovies();
        getAllMovies.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                Log.d(TAG, "onResponse: onGettingAllMovies:" + response.code());
                ResponseObject responseObject = response.body();

                Gson gson = new Gson();

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<MovieItems> allItems = new ArrayList<>();
                allItems = responseObject.getResults();
                Log.d(TAG, "onResponse: allitems" + allItems.toString());
                String finalString = gson.toJson(allItems);
                editor.putString("allItems", finalString);
                editor.commit();


            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingAllMovies : t" + t.getMessage());
            }
        });
        Call<ResponseObject> getTrendingMovies = retrofitClient.getTrendingMovies();
        getTrendingMovies.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                Log.d(TAG, "onResponse: onGettingTrendingMovies :" + response.code());
                ResponseObject responseObject1 = response.body();

                Gson gson = new Gson();

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<MovieItems> allTrendingItems = new ArrayList<>();
                allTrendingItems = responseObject1.getResults();
                Log.d(TAG, "onResponse: allTrendingMovies" + allTrendingItems.toString());
                String finalString1 = gson.toJson(allTrendingItems);
                editor.putString("allTrending", finalString1);
                editor.commit();


            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingTrendingMovies : t" + t.getMessage());
            }
        });
        Call<ResponseObject> getNewMovies = retrofitClient.getNewMovies();
        getNewMovies.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                Log.d(TAG, "onResponse: onGettingNewMovies :" + response.code());
                ResponseObject responseObject2 = response.body();

                Gson gson = new Gson();

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<MovieItems> allNewMovies = new ArrayList<>();
                allNewMovies = responseObject2.getResults();
                Log.d(TAG, "onResponse: allNewMovies" + allNewMovies.toString());
                String finalString2 = gson.toJson(allNewMovies);
                editor.putString("allNewMovies", finalString2);
                editor.commit();

            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingNewMovies : t" + t.getMessage());
            }
        });


    }

    public ArrayList<MovieItems> getAllItems(Context context) {
        Log.d(TAG, "getAllItems: called");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        return allItems;

    }

    public ArrayList<MovieItems> getNewItems(Context context) {
        Log.d(TAG, "getAllItems: called");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> newItems = gson.fromJson(sharedPreferences.getString("allNewMovies", null), type);
        return newItems;

    }

    public ArrayList<MovieItems> getTrendingItems(Context context) {
        Log.d(TAG, "getAllItems: called");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> trendingItems = gson.fromJson(sharedPreferences.getString("allTrending", null), type);
        return trendingItems;

    }

    public void findSimilarMovies(final String id) {
        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseObject responseObject = gson.fromJson(response, ResponseObject.class);
                Log.d(TAG, "onResponse getSimilarItems : responseObject" + responseObject.toString());

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                ArrayList<MovieItems> similarMovies = responseObject.getResults();
                String finalString2 = gson.toJson(similarMovies);
                editor.putString("similarMovies" + id, finalString2);
                editor.apply();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public ArrayList<MovieItems> getSimilarItems(String id) {
        Log.d(TAG, "getSimilarItems: called");
        findSimilarMovies(id);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> similarItems = gson.fromJson(sharedPreferences.getString("similarMovies" + id, null), type);
        Log.d(TAG, "getSimilarItems: similarMovies delta" + similarItems.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("similarMovies" + id);
        editor.apply();

        return similarItems;

    }

    public void findMoviesDetails(final String id) {
        Log.d(TAG, "getMoviesDetails: called");

        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=12cd0a8a7f3fab830b272438df172ea8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MovieDetails movieDetails = null;
                try {
                    movieDetails = gson.fromJson(response, MovieDetails.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: check " + movieDetails.toString());
                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String finalString2 = gson.toJson(movieDetails);

                editor.putString("movieDetails", finalString2);
                editor.apply();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    public MovieDetails getMovieDetails(String id) {
        Log.d(TAG, "getMovieDetails: called");
        findMoviesDetails(id);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<MovieDetails>() {
        }.getType();
        MovieDetails details = gson.fromJson(sharedPreferences.getString("movieDetails", null), MovieDetails.class);
        Log.d(TAG, "getMovieDetails: movieDetails " + details.toString());
        return details;


    }

    public void findReviews(final String id) {
        Log.d(TAG, "findReviews: called");
        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1";
        //String url = "https://api.themoviedb.org/3/movie/12/reviews?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: reviews " + response);

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("reviewOfMovie", response);
                editor.apply();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.start();


    }

    public Review getReviews(String id) {
        findReviews(id);
        Log.d(TAG, "getReviews: called");
        findMoviesDetails(id);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String response = sharedPreferences.getString("reviewOfMovie", null);
        Review reviews = gson.fromJson(response, Review.class);
        Log.d(TAG, "getReviews: reviews " + id + reviews.toString());
        return reviews;

    }

    public void findCredits(final String id) {
        Log.d(TAG, "findReviews: called");
        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=12cd0a8a7f3fab830b272438df172ea8";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Credits credits = gson.fromJson(response, Credits.class);
                Log.d(TAG, "onResponse: " + credits.toString() + "\n\n\n");

                SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String finalString2 = gson.toJson(credits);
                editor.putString("creditOfMovie" + id, finalString2);
                editor.apply();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.start();


    }

    public Credits getCredits(String id) {
        Log.d(TAG, "getReviews: called");
        findCredits(id);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        Credits credits = gson.fromJson(sharedPreferences.getString("creditOfMovie" + id, null), Credits.class);
        Log.d(TAG, "getCredits: credits " + id + credits.toString() + "\n\n\n");
        return credits;

    }


    public void addToWantToWatchMovies(MovieItems movieItem) {
        Log.d(TAG, "addToWantToWatchMovies: called");
        wantToWatchItems.add(movieItem);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String string = sharedPreferences.getString("WantToWatch", null);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> items = gson.fromJson(string, type);
        if (null != items) {
            wantToWatchItems.addAll(items);
        }

        String finalString = gson.toJson(wantToWatchItems);
        editor.putString("WantToWatch", finalString);
        editor.apply();


    }

    public ArrayList<MovieItems> getWantToWatchMovies() {
        Log.d(TAG, "getWantToWatchMovies: called");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String string = sharedPreferences.getString("WantToWatch", null);
        Type type = new TypeToken<ArrayList<MovieItems>>() {
        }.getType();
        ArrayList<MovieItems> items = gson.fromJson(string, type);
        if (items != null) {
            Log.d(TAG, "getWantToWatchMovies: called");
        }

        return items;
    }


    public boolean removeWantToWatchBooks(MovieItems movieItem) {
        Log.d(TAG, "removeWantToWatchBooks: called");
        ArrayList<MovieItems> items = getWantToWatchMovies();
        ArrayList<MovieItems> improvedItems = new ArrayList<>();
        for (MovieItems item : items
        ) {
            if (item.getId() != movieItem.getId()) {
                improvedItems.add(item);
            }
        }
        Log.d(TAG, "removeWantToWatchBooks: removed " + movieItem.toString() + "\n\n\n\n");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String finalString = gson.toJson(improvedItems);
        editor.putString("WantToWatch", finalString);
        editor.apply();

        return true;
    }
}
