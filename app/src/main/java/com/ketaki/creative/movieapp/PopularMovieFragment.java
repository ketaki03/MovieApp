package com.ketaki.creative.movieapp;

/**
 * Created by gunke001 on 7/8/17.
 */

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
import com.ketaki.creative.movieapp.api.PopularMoviesAPI;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class PopularMovieFragment extends Fragment {

    private RecyclerView popularStoriesRecylerView;
    public static String API_URL = "https://api.themoviedb.org/3/movie/popular?api_key=c686878f028a9e37c69d0d5077c4bdfd";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.popular_movie_fragment, container, false);
        popularStoriesRecylerView = (RecyclerView) view.findViewById(R.id.popularMovies);

        getPopularMovies();
        return view;
    }

    public void getPopularMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PopularMoviesAPI service = retrofit.create(PopularMoviesAPI.class);

        Call<MovieResults> call = service.getPopularMovies();

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Response<MovieResults> response, Retrofit retrofit) {
                List<Result> results = response.body().getResults();
                MovieAdapter movieAdapter = new MovieAdapter(results, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                popularStoriesRecylerView.setLayoutManager(mLayoutManager);
                popularStoriesRecylerView.setItemAnimator(new DefaultItemAnimator());
                popularStoriesRecylerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }
}
