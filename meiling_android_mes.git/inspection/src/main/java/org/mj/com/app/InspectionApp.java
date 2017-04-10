package org.mj.com.app;

import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;

import org.mj.com.app.client.NetService;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * 备注：改功能模块为公共模块，提供岗位扫描和员工工牌号扫描功能。
 * 作者：wangzs on 2016/2/19 10:22
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */

public class InspectionApp extends Application {

    private static InspectionApp app = null;

    private static NetService netService;

    public static String userCode = "";        //岗位ID
    public static String postCode = "";        //员工ID
    public static String functionType = "";    //功能点类型
    public static String sessionId = "";       //session信息

    public static InspectionApp getApp(){
        if(app == null){
            app = new InspectionApp();
        }
        return app;
    }


    public static Map<Context, NetService> netServiceMap = new HashMap<Context,NetService>();

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


}
