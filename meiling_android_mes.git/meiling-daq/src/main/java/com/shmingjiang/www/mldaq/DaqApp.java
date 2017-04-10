package com.shmingjiang.www.mldaq;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;
import com.shmingjiang.www.mldaq.service.NetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import retrofit.RestAdapter;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public class DaqApp extends Application{
    private static DaqApp app;

    private static NetService netService;
    public static List<Activity> allActivity = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static DaqApp getApp(){
        if(app == null){
            app = new DaqApp();
        }
        return app;
    }

    public static Map<Context,NetService> netServiceMap = new HashMap<Context,NetService>();

    public NetService getNetService(Context context) {
        netService = netServiceMap.get(context);
        if(netService != null){
            return netService;
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.getBaseUrl(context))
                .setLogLevel(RestAdapter.LogLevel.NONE)//如果设置RestAdapter.LogLevel.FULL则会出现大图片下载不下来问题。
                .build();
        netService = restAdapter.create(NetService.class);
        netServiceMap.put(context,netService);
        return netService;
    }

    /**
     * 添加activity。
     * @param activity
     */
    public static void addActivity(Activity activity){
        if(!allActivity.contains(activity)){
            allActivity.add(activity);
        }
    }

    /**
     * 退出应用程序。
     */
    public static void exitApplication(){
       for(Activity activity : allActivity){
           activity.finish();
       }
        System.exit(0);
    }
}
