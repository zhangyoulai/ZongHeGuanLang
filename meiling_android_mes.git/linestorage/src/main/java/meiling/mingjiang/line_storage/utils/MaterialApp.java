package meiling.mingjiang.line_storage.utils;

import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * 备注：
 * 作者：wangzs on 2016/2/20 10:40
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MaterialApp extends Application{

    public  Map<String,String> materialMap = null;
    public SessionObj sessionObj = null;
    public static String sessionId = "";       //session信息


    private static MaterialApp app = null;
    private static NetService netService;
    private static Map<Context,NetService> netServiceMap = new HashMap<Context,NetService>();

    public static MaterialApp getApp(){
        if(app == null){
            app = new MaterialApp();
        }
        return app;
    }

    public NetService getNetService(Context context) {
        NetService retNetService = netServiceMap.get(context);
        if( retNetService != null){
            return retNetService;
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
