package com.example.melificent.demoforkunming.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/10/27.
 */
public class VideoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //调用video view的start（）方法播放视频，首先需要初始化视频播放器，然后设置视频播源，
        final VideoView videoView = (VideoView) findViewById(R.id.videoview);
        //获取外部存储卡权限时必须要使用“/”来分隔文件，并且文件需要放置在最外层父目录下
        videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/Pixels.mp4");
        videoView.start();


    }
}
