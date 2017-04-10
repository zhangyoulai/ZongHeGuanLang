package com.example.melificent.xuweizongheguanlang.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.example.melificent.xuweizongheguanlang.Fragment.MapFragment;
import com.example.melificent.xuweizongheguanlang.R;
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

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by p on 2017/2/22.
 * this is a utils that uses imageloader to display image on Iamgeview
 */

public class ShowImageTools {
   public static final void DisplayImageOnImageview(ImageLoader imageloader,ImageView imageView,int imageRes,  Context mcontext){

       CreateOptions(mcontext);
       imageloader = ImageLoader.getInstance();
       DisplayImageOptions options = new DisplayImageOptions.Builder()
               .showImageOnLoading(imageRes)
               .showImageForEmptyUri(R.mipmap.ic_launcher)
               .showImageOnFail(R.mipmap.ic_launcher)
               .cacheInMemory(true)
               .cacheOnDisk(true)
               .considerExifParams(true)
               .displayer(new RoundedBitmapDisplayer(20))
               .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
               .build();
       ImageLoadingListener listener = new AnimateFirstDisplayListener();

       String imageurl ="drawable://"+imageRes;

       imageloader.displayImage(imageurl,imageView,options,listener);
   };
    // ImageLoader option
    private static void CreateOptions(Context mcontext) {
        File cacheDir = new File(Environment.getExternalStorageDirectory(),"imageLoader.cache");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(mcontext)
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
                .imageDownloader(new BaseImageDownloader(mcontext,5*1000,30*1000))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    //First Display setting
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
}
