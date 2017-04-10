package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.MyConstants;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
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

import java.io.BufferedReader;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/1/19.
 * Use Imageloader to load image to avoid OOM
 */

public class NewOprationActivity extends AppCompatActivity {
//    @InjectView(R.id.opration_details_spread)
//    ImageView spread;
//    @InjectView(R.id.opration_doc)
//    LinearLayout doc;
//    @InjectView(R.id.opration_line)
//    LinearLayout line;
//    @InjectView(R.id.opration_equipment)
//    LinearLayout equipment;
//    @InjectView(R.id.opration_energy)
//    LinearLayout energy;
//    @InjectView(R.id.opration_task)
//    LinearLayout task;
//    @InjectView(R.id.repair_task)
//    LinearLayout repair;
//    @InjectView(R.id.opration_alert)
//    LinearLayout alert;
//    @InjectView(R.id.opration_help)
//    LinearLayout help;
//    @InjectView(R.id.opration_knowlege_library)
//    LinearLayout library;
//    @InjectView(R.id.opration_search_input)
//    EditText input;
//    @InjectView(R.id.opration_search_details)
//    LinearLayout details;
//    private boolean IS_FIRST_CLICK = true;
    @InjectView(R.id.routing_manage)
    ImageView routing_manage;
    @InjectView(R.id.repairandmaintain_manage)
    ImageView repairandmaintian_manage;
    String imageuri_routing ;
    String imageuri_repairandmaintain_manage;
    ImageLoader imageloader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe_activity);
        ButterKnife.inject(this);
//        details.setVisibility(View.GONE);

//        setListener();

//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//        ImageLoader.getInstance().init(configuration);
        CreateOptions();
         imageloader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.xunjian)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        ImageLoadingListener listener = new AnimateFirstDisplayListener();

        imageuri_routing ="drawable://"+R.drawable.xunjian;
        imageuri_repairandmaintain_manage = "drawable://"+R.drawable.weibao;
        imageloader.displayImage(imageuri_routing,routing_manage,options,listener);
        imageloader.displayImage(imageuri_repairandmaintain_manage,repairandmaintian_manage,options,listener);


        setImageviewClickListener();

    }

    private void setImageviewClickListener() {
        routing_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewOprationActivity.this,InspectionActivity.class));
            }
        });
        repairandmaintian_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewOprationActivity.this,RepairAndMaintainManageActivity.class));
            }
        });
    }

    private void CreateOptions() {
        File cacheDir = new File(Environment.getExternalStorageDirectory(),"imageLoader.cache");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(720,1080)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private  static  class  AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static  final List<String> displayedImages = Collections.synchronizedList(
                new LinkedList<String>()
        );
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageloader.clearMemoryCache();
        imageloader.clearDiskCache();
    }
}
