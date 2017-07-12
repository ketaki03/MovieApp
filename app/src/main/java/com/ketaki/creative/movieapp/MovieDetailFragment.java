package com.ketaki.creative.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketaki.creative.movieapp.Model.Result;
import com.squareup.picasso.Picasso;

/**
 * Created by gunke001 on 7/8/17.
 */

public class MovieDetailFragment extends android.app.Fragment {

    private Result movie;
    private TextView movieNameTextView;
    private TextView ratingTextView;
    private TextView durationTextView;
    private TextView synpopsisTextView;
    private ImageView movieImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_movie_details, container, false);
        movieNameTextView = (TextView) view.findViewById(R.id.movie_name);
        ratingTextView = (TextView) view.findViewById(R.id.ratings);
        durationTextView = (TextView) view.findViewById(R.id.duration);
        synpopsisTextView = (TextView) view.findViewById(R.id.synopsis);
        movieImageView= (ImageView) view.findViewById(R.id.movie_image);
        popualteMovieDetails();
        return view;
    }

    public Result getMovie() {
        return movie;
    }

    public void setMovie(Result movie) {
        this.movie = movie;
    }

    public void popualteMovieDetails(){
        if(movie != null){
            movieNameTextView.setText(movie.getTitle());
            synpopsisTextView.setText(movie.getOverview());
            durationTextView.setText(movie.getRelease_date());
            ratingTextView.setText(movie.getVote_average().toString());
            String imageLink = "https://image.tmdb.org/t/p/w650/"+ movie.getPoster_path();
            Picasso
                    .with(getActivity())
                    .load(imageLink)
                    .into(movieImageView);
        }

    }


}
