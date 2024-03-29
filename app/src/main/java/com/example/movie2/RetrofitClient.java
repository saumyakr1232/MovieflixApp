package com.example.movie2;

import com.example.movie2.Model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitClient {

    @GET("discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=5&vote_count.gte=120")
    Call<ResponseObject> getAllMovies();

    @GET("trending/movie/day?api_key=12cd0a8a7f3fab830b272438df172ea8")
    Call<ResponseObject> getTrendingMovies();

    @GET("discover/movie?api_key=12cd0a8a7f3fab830b272438df172ea8&region=IN&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&year=2020&with_original_language=hi")
    Call<ResponseObject> getNewMovies();

    @GET("movie/now_playing?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1&region=IN")
    Call<ResponseObject> getNowPlayingMovies();

    @GET("movie/top_rated?api_key=12cd0a8a7f3fab830b272438df172ea8&language=hi&page=1&region=IN")
    Call<ResponseObject> getTopRatedHindiMovies();

    @GET("movie/top_rated?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1&region=US")
    Call<ResponseObject> getTopRatedEnglishMovies();

    @GET("movie/upcoming?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US&page=1")
    Call<ResponseObject> getUpcomingEngMovies();

    @GET("movie/upcoming?api_key=12cd0a8a7f3fab830b272438df172ea8&language=hi&page=1&region=IN")
    Call<ResponseObject> getUpcomingHindiMovies();

    @GET("/movie/{movie_id}/images?api_key=12cd0a8a7f3fab830b272438df172ea8&language=en-US")
    Call<ResponseObject> getPosters(@Path("movie_id") int movie_id);
}