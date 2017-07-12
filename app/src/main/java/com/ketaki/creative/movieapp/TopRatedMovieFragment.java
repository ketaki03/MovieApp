package com.ketaki.creative.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ketaki.creative.movieapp.Model.MovieResults;
import com.ketaki.creative.movieapp.Model.Result;
import com.ketaki.creative.movieapp.adapter.MovieAdapter;
import com.ketaki.creative.movieapp.api.TopRatedMoviesAPI;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gunke001 on 7/8/17.
 */

public class TopRatedMovieFragment extends Fragment{
    private RecyclerView topRatedMoviesRecylerView;
    public static String API_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=c686878f028a9e37c69d0d5077c4bdfd";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_rated_movie_fragment, container, false);
        topRatedMoviesRecylerView= (RecyclerView) view.findViewById(R.id.top_rated_Movies);

        getPopularMovies();
        return view;
    }

    public void getPopularMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TopRatedMoviesAPI service = retrofit.create(TopRatedMoviesAPI.class);

        Call<MovieResults> call = service.getTopRatedMovies();

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Response<MovieResults> response, Retrofit retrofit) {
                List<Result> results = response.body().getResults();
                MovieAdapter movieAdapter = new MovieAdapter(results, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                topRatedMoviesRecylerView.setLayoutManager(mLayoutManager);
                topRatedMoviesRecylerView.setItemAnimator(new DefaultItemAnimator());
                topRatedMoviesRecylerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }

}

