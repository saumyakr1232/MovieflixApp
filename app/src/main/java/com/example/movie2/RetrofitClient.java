package com.example.movie2;

import com.example.movie2.Model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitClient {

    @GET("discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=5&vote_count.gte=120")
    Call<ResponseObject> getAllMovies();

    @GET("trending/movie/week?api_key=12cd0a8a7f3fab830b272438df172ea8")
    Call<ResponseObject> getTrendingMovies();

    @GET("discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&year=2020")
    Call<ResponseObject> getNewMovies();

}