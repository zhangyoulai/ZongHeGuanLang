package com.mingjiang.android.app;

import android.app.Activity;
import android.os.Bundle;

import com.mingjiang.android.app.ui.MainMenuActivity;

/**
 * 应用启动界面
 */
public class AppStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainMenuActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }

//        setContentView(R.layout.app_start);
//        findViewById(R.id.app_start_view).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                redirectTo();
//            }
//        }, 800);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
