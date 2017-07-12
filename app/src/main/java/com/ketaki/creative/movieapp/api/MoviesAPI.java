package com.ketaki.creative.movieapp.api;

import com.ketaki.creative.movieapp.Model.MovieVideoResults;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by gunke001 on 7/8/17.
 */

public interface MoviesAPI {

    //get video info

    @GET("https://api.themoviedb.org/3/movie/{movieID}/videos?api_key=c686878f028a9e37c69d0d5077c4bdfd&language=en-US")
    Call<MovieVideoResults> getVideoInfo(
            @Path("movieID") String movieID);
}