package com.example.karthi.gtsword;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by Karthi on 04-Sep-17.
 */

public class SplashScreen extends Activity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        videoView =(VideoView) findViewById(R.id.videoView);

        Uri video=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.giphy);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        });

        videoView.start();
    }


}

