package com.example.deepchemocare;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class MotivationActivityDesc extends AppCompatActivity {


    private ExoPlayer exoPlayer;
    private PlayerView playerView;
    Uri videoUri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_motivation_desc);

        playerView = findViewById(R.id.playerView);


        String pose=getIntent().getStringExtra("pose");

        if (pose.equals("1")) {
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.e5);
        }
        else if (pose.equals("2"))
        {
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.e6);

        }
        else
        {
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.e7);

        }



        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}