package com.ketaki.creative.movieapp.api;

import com.ketaki.creative.movieapp.Model.MovieResults;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by gunke001 on 7/8/17.
 */

public interface TopRatedMoviesAPI {
    @GET("https://api.themoviedb.org/3/movie/top_rated?api_key=c686878f028a9e37c69d0d5077c4bdfd")
    Call<MovieResults> getTopRatedMovies();
}