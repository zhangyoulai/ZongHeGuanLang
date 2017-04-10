package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.melificent.xuweizongheguanlang.R;

/**
 * Created by p on 2017/2/22.
 * play video to simulation the camera video
 */

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setVideoPath(Environment.getExternalStorageDirectory()+"/Pixels.mp4");
        videoView.start();
    }
}
