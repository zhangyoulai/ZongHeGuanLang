package com.mingjiang.android.app.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mingjiang.android.app.ui.MainMenuActivity;

/**
 * Created by kouzeping on 2016/4/13.
 * email：kouzeping@shmingjiang.org.cn
 *
 * 开机自动启动，系统发开机广播比较迟
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent ootStartIntent=new Intent(context,MainMenuActivity.class);
            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(ootStartIntent);
        }
    }
}
