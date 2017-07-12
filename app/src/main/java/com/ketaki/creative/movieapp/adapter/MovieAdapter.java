package com.ketaki.creative.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketaki.creative.movieapp.Model.Result;
import com.ketaki.creative.movieapp.MovieDetailActivity;
import com.ketaki.creative.movieapp.listener.OnItemClickListener;
import com.ketaki.creative.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gunke001 on 7/8/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Result> movies;
    private Context context;

    private static final String TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(List<Result> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_view, parent, false);

        return new MovieViewHolder(itemView);
    }

    public int getItemCount() {
        return movies.size();
    }

    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        Result movie = movies.get(position);

        //get poster link
        String imageLink = "https://image.tmdb.org/t/p/w650/" + movie.getPoster_path();
        Picasso
                .with(context)
                .load(imageLink)
                .into(holder.movieImage);
        Log.d(TAG, "Poster link is " + imageLink);

        //set UI data
        holder.movieName.setText(movie.getTitle());
        holder.bind(movie, new OnItemClickListener() {
            public void onItemClick(Result movie, int position) {
                Log.d("TAG", "movie clicked");
                Intent movieIntent = new Intent(context, MovieDetailActivity.class);
                Bundle bundle = new Bundle();

                //send movie data to bundle
                bundle.putString("movie_name", movie.getTitle());
                bundle.putString("movie_overview", movie.getOverview());
                bundle.putString("movie_release_date", movie.getRelease_date());
                bundle.putString("movie_rating", movie.getVote_average().toString());
                bundle.putString("movie_imageURL", movie.getPoster_path());
                bundle.putString("movie_id", Integer.toString(movie.getId()));
                Log.d("movie id", " " + movie.getId());

                movieIntent.putExtra("movie_details", bundle);
                context.startActivity(movieIntent);
            }

        }, position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName;

        public MovieViewHolder(View view) {
            super(view);
            movieName = (TextView) view.findViewById(R.id.movie_name);
            movieImage = (ImageView) view.findViewById(R.id.movie_image);
        }

        public void bind(final Result result, final OnItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onItemClick(result, position);
                }
            });
        }
    }
}
