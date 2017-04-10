package com.mingjiang.android.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mingjiang.android.app.bean.SessionObj;
import com.mingjiang.android.app.service.NetService;
import com.mingjiang.android.app.service.TestService;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.android.mingjiang.com.logapi.recordlog.log.manager.LogManager;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends MultiDexApplication {

    private static AppContext application;
    private static Context context;

    public Map<String, String> materialMap = null;
    public SessionObj sessionObj = null;
    public static String sessionId = "";       //session信息

    private static NetService netService;
    private static TestService testService;

    public static AppContext getApp() {
        if (application == null) {
            application = new AppContext();
        }
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        application = AppContext.this;
        //日志注册
        LogManager.getManager(getApplicationContext()).registerCrashHandler();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //日志取消注册
        LogManager.getManager(getApplicationContext()).unregisterCrashHandler();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Map<Context, NetService> netServiceMap = new HashMap<Context, NetService>();
    public static Map<Context,TestService> testServiceMap=new HashMap<>();
    public static NetService getNetService(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30 * 1000, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30 * 1000, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30*1000,TimeUnit.SECONDS);
        netService = netServiceMap.get(context);
        if (netService != null)
            return netService;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(com.mingjiang.android.base.util.Config.getBaseUrl(context))
                .setLogLevel(RestAdapter.LogLevel.FULL)//如果设置RestAdapter.LogLevel.FULL则会出现大图片下载不下来问题。
                .setClient(new OkClient(okHttpClient))
                .build();
        netService = restAdapter.create(NetService.class);
        netServiceMap.put(context,netService);
        return netService;

    }

    public static TestService getTestService(Context context) {
        testService = testServiceMap.get(context);
        if (testService != null)
            return testService;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.0.102:8072")
                .setLogLevel(RestAdapter.LogLevel.FULL)//如果设置RestAdapter.LogLevel.FULL则会出现大图片下载不下来问题。
                .build();
        testService = restAdapter.create(TestService.class);
        testServiceMap.put(context,testService);
        return testService;

    }

    public Context getContext() {
        context = application.getApplicationContext();
        return context;
    }
}
