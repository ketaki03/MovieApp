package com.ketaki.creative.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketaki.creative.movieapp.Model.MovieVideoResults;
import com.ketaki.creative.movieapp.Model.VideoInfo;
import com.ketaki.creative.movieapp.api.MoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.ketaki.creative.movieapp.R.id.movie_name;

/**
 * Created by gunke001 on 7/8/17.
 */


public class MovieDetailActivity extends AppCompatActivity {

    private TextView movieNameTextView;
    private TextView ratingTextView;
    private TextView durationTextView;
    private TextView synpopsisTextView;
    private ImageView movieImageView;
    private Button button;
    private ImageView playButton;
    private String movieID;
    private String API = "https://api.themoviedb.org/3/movie/";
    private String API_URL_END = "/videos?api_key=c686878f028a9e37c69d0d5077c4bdfd";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieNameTextView = (TextView) findViewById(movie_name);
        ratingTextView = (TextView) findViewById(R.id.ratings);
        durationTextView = (TextView) findViewById(R.id.duration);
        synpopsisTextView = (TextView) findViewById(R.id.synopsis);
        movieImageView = (ImageView) findViewById(R.id.movie_image);
        playButton = (ImageView) findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVideo();
            }
        });

        popualteMovieDetails();
    }

    //populate movie details with moviename, overview,image and ratings
    public void popualteMovieDetails() {
        if (getIntent() != null && getIntent().getBundleExtra("movie_details") != null) {
            Log.d(TAG, (getIntent().getBundleExtra("movie_details").getString("movie_name")));
            movieNameTextView.setText(getIntent().getBundleExtra("movie_details").getString("movie_name"));
            synpopsisTextView.setText(getIntent().getBundleExtra("movie_details").getString("movie_overview"));
            durationTextView.setText(getIntent().getBundleExtra("movie_details").getString("movie_release_date"));
            ratingTextView.setText(getIntent().getBundleExtra("movie_details").getString("movie_rating"));
            String imageLink = "https://image.tmdb.org/t/p/w650/" + getIntent().getBundleExtra("movie_details").getString("movie_imageURL");
            Picasso
                    .with(this)
                    .load(imageLink)
                    .into(movieImageView);
            this.movieID = getIntent().getBundleExtra("movie_details").getString("movie_id");
        }

    }

    public void startVideo() {

        if (!movieID.isEmpty()) {
            String api_url = API + movieID + API_URL_END;
            Log.d(TAG, "API URL " + api_url);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(api_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MoviesAPI service = retrofit.create(MoviesAPI.class);

            Call<MovieVideoResults> call = service.getVideoInfo(movieID);

            //check if the site is Youtube
            call.enqueue(new Callback<MovieVideoResults>() {
                @Override
                public void onResponse(Response<MovieVideoResults> response, Retrofit retrofit) {
                    response.body();
                    List<VideoInfo> videoInfoList = response.body().getResults();
                    for (VideoInfo videoInfo : videoInfoList) {
                        if (videoInfo.getSite().equals("YouTube") && videoInfo.getKey() != null) {
                            //just showing the first video using Youtube channel
                            openVideo(videoInfo.getKey());
                            break;
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(TAG, t.toString());
                }
            });
        }
    }

    //start Youtube video in new window
    public void openVideo(String key) {
        Log.d(TAG, "movie clicked");
        Intent videoIntent = new Intent(this, VideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("video_key", key);
        videoIntent.putExtra("video_key_bundle", bundle);
        startActivity(videoIntent);

    }

}

