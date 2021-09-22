package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.Model.ResponseObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private EditText searchStringEditText;
    private ImageView btnSearch;
    private BottomNavigationView bottomNavigationView;
    private SearchSecondFragment searchSecondFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        searchSecondFragment = new SearchSecondFragment();

        initBottomNavigation();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SearchFirstFragment());
        transaction.commit();

        final Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("searchQuery", searchStringEditText.getText().toString());
                startActivity(intent);

            }
        });


        searchStringEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Bundle bundle = new Bundle();
                bundle.putString("searchQuery", searchStringEditText.getText().toString());
                searchSecondFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, searchSecondFragment);
                transaction.commit();
            }
        });

        searchStringEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: called");

                String searchQuery = String.valueOf(s);

                searchQuery = searchQuery.replace(" ", "%20");
                String querySearchUrl = "https://api.themoviedb.org/3/search/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&query=" +
                        searchQuery + "&page=1&include_adult=false";


                final Gson gson = new Gson();


                //28%2C12

                StringRequest stringRequest = new StringRequest(Request.Method.GET, querySearchUrl, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResponseObject responseObject = gson.fromJson(response, ResponseObject.class);
                        Log.d(TAG, "onResponse called searchResults : responseObject" + responseObject.toString());
                        ArrayList<MovieItem> searchResult = responseObject.getResults();
                        searchSecondFragment.setSearchResults(searchResult);

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
                requestQueue.add(stringRequest);
                requestQueue.start();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: created");
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new SearchFirstFragment());
                        transaction.commit();
                        break;
                    case R.id.myList:
                        Intent intent = new Intent(SearchActivity.this, WantToWatch.class);
                        startActivity(intent);

                        break;
                    case R.id.homeActivity:
                        Intent intent1 = new Intent(SearchActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }


        });
    }



    private void initViews() {
        Log.d(TAG, "initViews: called");

        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        searchStringEditText = (EditText) findViewById(R.id.SearchString);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

    }


}
