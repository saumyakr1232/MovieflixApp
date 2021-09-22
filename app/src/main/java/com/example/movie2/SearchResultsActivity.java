package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.Model.ResponseObject;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    private RecyclerView searchResultRecView;
    private Utils utils;
    private MovieItemAdapter searchMoviesItemAdapter;
    ArrayList<MovieItem> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        searchResultRecView = (RecyclerView) findViewById(R.id.searchResultRecView);

        searchMoviesItemAdapter = new MovieItemAdapter(this);

        searchResultRecView.setLayoutManager(new GridLayoutManager(this, 2));
        searchResultRecView.setAdapter(searchMoviesItemAdapter);

        utils = new Utils(this);
        Intent intent = getIntent();

        ArrayList<Integer> ids = intent.getIntegerArrayListExtra("ids");

        String searchQuery = intent.getStringExtra("searchQuery");
        Toast.makeText(this, searchQuery, Toast.LENGTH_SHORT).show();


        if (searchQuery == null) {
            searchResults = filteredSearchResults(ids);//utils.getSearchResults("28%2C12","2010-01-01","2018-01-01","180547","hi","in");
            if (searchResults != null) {
                //Toast.makeText(this, searchResults.toString(), Toast.LENGTH_SHORT).show();
                searchMoviesItemAdapter.setItems(searchResults);
                searchMoviesItemAdapter.notifyDataSetChanged();
                searchResults = new ArrayList<>();

            }

        } else {
            searchQuery = searchQuery.replace(" ", "%20");
            String querySearchUrl = "https://api.themoviedb.org/3/search/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&query=" +
                    searchQuery + "&page=1&include_adult=false";
            //28%2C12
            final Gson gson = new Gson();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, querySearchUrl, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ResponseObject responseObject = gson.fromJson(response, ResponseObject.class);
                    Log.d(TAG, "onResponse called searchResults : responseObject" + responseObject.toString());
                    ArrayList<MovieItem> searchResult = responseObject.getResults();
                    searchMoviesItemAdapter.setItems(searchResult);
                    searchMoviesItemAdapter.notifyDataSetChanged();


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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchMoviesItemAdapter.setItems(new ArrayList<MovieItem>());
    }

    private ArrayList<MovieItem> filteredSearchResults(ArrayList<Integer> ids) {
//                 "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language="
//                +lang+"&region="+country+"&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&release_date.gte="
//                +rd_gte+"&release_date.lte="+rd_lte+
//                "&with_genres="+genre+"&with_keywords="+keywords;

        String baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                "&language=hi&region=in" +
                "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                "&release_date.gte=2010-01-01" +
                "&release_date.lte=2018-01-01" +
                "&with_genres=28%2C12" +
                "&with_keywords=180547";

        String genres = "";
        String rd_gte = "";
        String rd_lte = "";
        String keywords = "";
        String lang = "";
        boolean haveDecade = false;
        boolean haveGenre = false;
        boolean haveKeyword = false;
        boolean haveLang = false;
        boolean haveCountry = false;

        ArrayList<Integer> visited = new ArrayList<>();
        if (ids != null) {
            for (Integer id : ids
            ) {
                switch (id) {
                    case R.id.chipAction:
                        haveGenre = true;
                        visited.add(R.id.chipAction);
                        if (visited.contains(R.id.comedyChip) || visited.contains(R.id.horrorChip) || visited.contains(R.id.adventureChip) || visited.contains(R.id.romanceChip)) {
                            genres += "%2C28";
                        }
                        genres += "28";
                        break;
                    case R.id.comedyChip:
                        haveGenre = true;
                        visited.add(R.id.comedyChip);
                        if (visited.contains(R.id.chipAction) || visited.contains(R.id.horrorChip) || visited.contains(R.id.adventureChip) || visited.contains(R.id.romanceChip)) {
                            genres += "%2C35";
                        }
                        genres += "35";
                        break;

                    case R.id.horrorChip:
                        haveGenre = true;
                        visited.add(R.id.horrorChip);
                        if (visited.contains(R.id.chipAction) || visited.contains(R.id.comedyChip) || visited.contains(R.id.adventureChip) || visited.contains(R.id.romanceChip)) {
                            genres += "%2C27";
                        }
                        genres += "27";

                        break;

                    case R.id.adventureChip:
                        haveGenre = true;
                        visited.add(R.id.adventureChip);
                        if (visited.contains(R.id.chipAction) || visited.contains(R.id.horrorChip) || visited.contains(R.id.comedyChip) || visited.contains(R.id.romanceChip)) {
                            genres += "%2C12";
                        }
                        genres += "12";

                        break;
                    case R.id.romanceChip:
                        haveGenre = true;
                        visited.add(R.id.romanceChip);
                        if (visited.contains(R.id.chipAction) || visited.contains(R.id.horrorChip) || visited.contains(R.id.adventureChip) || visited.contains(R.id.comedyChip)) {
                            genres += "%2C10749";
                        }
                        genres += "10749";

                        break;
                    case R.id.d1990_2000:
                        haveDecade = true;
                        rd_gte = "1990-01-01";
                        rd_lte = "2000-01-01";

                        break;
                    case R.id.d2010_2020:
                        haveDecade = true;
                        rd_gte = "2010-01-01";
                        rd_lte = "2020-01-01";

                        break;
                    case R.id.d2000_2010:
                        haveDecade = true;
                        rd_gte = "2000-01-01";
                        rd_lte = "2010-01-01";

                        break;
                    case R.id.kSuicide:
                        haveKeyword = true;
                        visited.add(R.id.kSuicide);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C236";

                        }
                        keywords = "236";
                        break;
                    case R.id.kMusical:
                        haveKeyword = true;
                        visited.add(R.id.kMusical);
                        if (visited.contains(R.id.kSuicide) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C4344";

                        }
                        keywords = "4344";

                        break;
                    case R.id.kDc:
                        haveKeyword = true;
                        visited.add(R.id.kDc);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kSuicide) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C849";

                        }
                        keywords = "849";

                        break;
                    case R.id.kMcu:
                        haveKeyword = true;
                        visited.add(R.id.kMcu);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kSuicide) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C180547";

                        }
                        keywords = "180547";

                        break;
                    case R.id.kMagic:
                        haveKeyword = true;
                        visited.add(R.id.kMagic);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kSuicide) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C2343";

                        }
                        keywords = "2343";

                        break;
                    case R.id.kRevenge:
                        haveKeyword = true;
                        visited.add(R.id.kRevenge);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kSuicide) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C9748";

                        }
                        keywords = "9748";

                        break;
                    case R.id.kSpace:
                        haveKeyword = true;
                        visited.add(R.id.kSpace);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSuicide) ||
                                visited.contains(R.id.kSuperhero)) {
                            keywords += "%2C9882";

                        }
                        keywords = "9882";

                        break;
                    case R.id.kSuperhero:
                        haveKeyword = true;
                        visited.add(R.id.kSuperhero);
                        if (visited.contains(R.id.kMusical) || visited.contains(R.id.kDc) || visited.contains(R.id.kMagic) ||
                                visited.contains(R.id.kMcu) || visited.contains(R.id.kRevenge) || visited.contains(R.id.kSpace) ||
                                visited.contains(R.id.kSuicide)) {
                            keywords += "%2C9715";

                        }
                        keywords = "9715";

                        break;

                    default:
                        break;

                }

            }

        }
        if (haveGenre && haveDecade) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                    "&language=hi&region=in" +
                    "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                    "&release_date.gte=" + rd_gte +
                    "&release_date.lte=" + rd_lte +
                    "&with_genres=" + genres;
        } else if (haveGenre && !haveCountry && haveDecade && haveKeyword && haveLang) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                    "&language=" + lang + "&region=in" +
                    "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                    "&release_date.gte=" + rd_gte +
                    "&release_date.lte=" + rd_lte +
                    "&with_genres=" + genres +
                    "&with_keywords=" + keywords;
        }
        if (haveDecade) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                    "&language=hi&region=in" +
                    "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                    "&release_date.gte=" + rd_gte +
                    "&release_date.lte=" + rd_lte;

        }
        if (haveKeyword) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                    "&language=hi&region=in" +
                    "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                    "&with_keywords=" + keywords;

        }
        if (haveLang) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8" +
                    "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
                    "&with_original_language=" + lang;

        }
        if (haveCountry) {

        }
        if (!haveCountry && !haveDecade && !haveGenre && !haveKeyword && !haveLang) {
            baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

        }
        return utils.getSearchResults(baseUrl);

    }
}
