package com.mingjiang.android.base.service.com;

import android.content.Context;
import android.content.Intent;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.base.util.Constants;


/**
 * 备注：启动和关闭串口服务。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComServiceUtils {

    /**
     * 启动串口服务。
     */
    public static void startService(Context context,int activityCode){
        Intent intent = new Intent(context, ComService.class);
        intent.putExtra(Constants.ACTIVITY_CODE,activityCode);
        intent.putExtra(Constants.BAUD_CODE_ONE, Config.getBaseBaud1(context));
        intent.putExtra(Constants.BAUD_CODE_TWO,Config.getBaseBaud2(context));
        intent.putExtra(Constants.BAUD_CODE_THREE,Config.getBaseBaud3(context));
        intent.putExtra(Constants.BAUD_CODE_FOUR,Config.getBaseBaud4(context));
        context.startService(intent);
    }

    /**
     * 关闭串口服务。
     */
    public static void stopService(Context context){
        Intent intent = new Intent(context,ComService.class);
        context.stopService(intent);
    }
}