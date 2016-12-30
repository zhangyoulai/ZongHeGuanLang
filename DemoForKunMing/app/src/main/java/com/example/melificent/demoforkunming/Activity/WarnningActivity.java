package com.example.melificent.demoforkunming.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.melificent.demoforkunming.App;
import com.example.melificent.demoforkunming.Fragment.CH4Fragment;
import com.example.melificent.demoforkunming.Fragment.H2SFragment;
import com.example.melificent.demoforkunming.Fragment.O2Fragment;
import com.example.melificent.demoforkunming.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by p on 2016/10/27.
 */
public class WarnningActivity extends AppCompatActivity {
    ImageButton back;

    Button O2;
    Button CH4;
    Button H2S;
   ImageView ig_gas;

    String imageuri;
    String imageuri1;
    String imageuri2;
    ImageLoader imageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        back = (ImageButton) findViewById(R.id.button_gas_back);

        O2 = (Button) findViewById(R.id.button_yangqi);
        CH4 = (Button) findViewById(R.id.button_ch4);
        H2S = (Button) findViewById(R.id.button_h2s);
       ig_gas = (ImageView) findViewById(R.id.ig_gas);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarnningActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        setOnClickListener();

//        imageloader = ImageLoader.getInstance();
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.o2)
//                .showImageForEmptyUri(R.mipmap.ic_launcher)
//                .showImageOnFail(R.mipmap.ic_launcher)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .displayer(new RoundedBitmapDisplayer(20))
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .build();
//        ImageLoadingListener listener = new AnimateFirstDisplayListener();
//
//        imageuri ="drawable://"+R.drawable.o2;
//        imageloader.displayImage(imageuri,ig_gas,options,listener);









    }

    private  static  class  AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static  final List<String> displayedImages = Collections.synchronizedList(
                new LinkedList<String>()
        );
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            if (loadedImage==null){
                ImageView imageview  = (ImageView) view;
                boolean firstdisplay= !displayedImages.contains(imageUri);
                if (firstdisplay){
                    FadeInBitmapDisplayer.animate(imageview,500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        imageloader.clearDiskCache();
//        imageloader.clearMemoryCache();
//    }

    private void setOnClickListener() {
        O2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ig_gas.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.o2));

            }
        });
        H2S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DisplayImageOptions options1 = new DisplayImageOptions.Builder()
//                        .showImageOnLoading(R.drawable.h2s)
//                        .showImageForEmptyUri(R.mipmap.ic_launcher)
//                        .showImageOnFail(R.mipmap.ic_launcher)
//                        .cacheInMemory(true)
//                        .cacheOnDisk(true)
//                        .considerExifParams(true)
//                        .displayer(new RoundedBitmapDisplayer(20))
//                        .build();
//                ImageLoadingListener listener1= new AnimateFirstDisplayListener();
//                imageuri1 ="drawable://"+R.drawable.h2s;
//                imageloader.displayImage(imageuri1,ig_gas,options1,listener1);

                ig_gas.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.h2s));


            }
        });
        CH4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                DisplayImageOptions options1 = new DisplayImageOptions.Builder()
//                        .showImageOnLoading(R.drawable.ch4)
//                        .showImageForEmptyUri(R.mipmap.ic_launcher)
//                        .showImageOnFail(R.mipmap.ic_launcher)
//                        .cacheInMemory(true)
//                        .cacheOnDisk(true)
//                        .considerExifParams(true)
//                        .displayer(new RoundedBitmapDisplayer(20))
//                        .build();
//                ImageLoadingListener listener1= new AnimateFirstDisplayListener();
//                imageuri2="drawable://"+R.drawable.ch4;
//                imageloader.displayImage(imageuri2,ig_gas,options1,listener1);
                ig_gas.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ch4));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
