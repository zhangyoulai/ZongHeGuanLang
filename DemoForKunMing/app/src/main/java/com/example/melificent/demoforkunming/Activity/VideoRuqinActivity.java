package com.example.melificent.demoforkunming.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/10/27.
 */
public class VideoRuqinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final VideoView videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/Pixels1.mp4");

        videoView.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
