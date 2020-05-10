package com.example.movie2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie2.Model.Genre;
import com.example.movie2.Model.GenreResponseObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GenreItemsActivity extends AppCompatActivity {
    private static final String TAG = "GenreItemsActivity";

    private GenreItemListViewAdapter adapter;
    private ListView listView;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_items);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        listView = (ListView) findViewById(R.id.listGenre);


        final Gson gson = new Gson();
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type type = new TypeToken<ArrayList<Genre>>() {
                }.getType();
                GenreResponseObject responseObject = gson.fromJson(response, GenreResponseObject.class);
                Log.d(TAG, "onResponse: responseObject Genre :" + responseObject.toString());
                ArrayList<Genre> genres = responseObject.getGenres();
                Log.d(TAG, "onResponse: genres" + genres.toString());
                if (genres != null) {
                    adapter = new GenreItemListViewAdapter(GenreItemsActivity.this, genres);
                    listView.setAdapter(adapter);
                } else {
                    adapter = new GenreItemListViewAdapter(GenreItemsActivity.this, new ArrayList<Genre>());
                    listView.setAdapter(adapter);
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
