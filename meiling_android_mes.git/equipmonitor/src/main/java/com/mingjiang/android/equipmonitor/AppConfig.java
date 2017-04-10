package com.mingjiang.android.equipmonitor;


import com.mingjiang.android.equipmonitor.entity.Pol1;
import com.mingjiang.android.equipmonitor.entity.Pol2;
import com.mingjiang.android.equipmonitor.entity.Pol3;
import com.mingjiang.android.equipmonitor.entity.Pol4;
import com.mingjiang.android.equipmonitor.entity.WeldPower;
import com.mingjiang.android.equipmonitor.entity.WeldThick;
import com.mingjiang.android.equipmonitor.entity.WeldTime;

/**
 * Created by wangdongjia on 2016/7/13.
 */
public class AppConfig {

    public static WeldPower weldPower = new WeldPower();//焊接能量

    public static WeldThick weldThick = new WeldThick();//焊接壁厚

    public static WeldTime weldTime = new WeldTime();//焊接时间

    public static Pol1 pol1 = new Pol1();
    public static Pol2 pol2 = new Pol2();
    public static Pol3 pol3 = new Pol3();
    public static Pol4 pol4 = new Pol4();
}
