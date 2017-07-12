package com.ketaki.creative.movieapp;

/**
 * Created by gunke001 on 7/9/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static final String API_KEY = "AIzaSyBqVtduiZMJ7yTS8GP43vwAeMCnAYIJ22c";
    private YouTubePlayer youTubePlayer;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    private Button btnViewFullScreen;

    private String video_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_video);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        if (getIntent() != null && getIntent().getBundleExtra("video_key_bundle") != null &&
                getIntent().getBundleExtra("video_key_bundle").getString("video_key") != null) {
            video_key = getIntent().getBundleExtra("video_key_bundle").getString("video_key");
            Log.d("Video Key ", " " + video_key);

        }

        // Initializing video player with developer key
        youTubeView.initialize(API_KEY, this);

        btnViewFullScreen = (Button) findViewById(R.id.btnviewfullscreen);
        btnViewFullScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                youTubePlayer.setFullscreen(true);
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer  ", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        youTubePlayer = player;
        if (!wasRestored) {
            player.cueVideo(video_key);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}