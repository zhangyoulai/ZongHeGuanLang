package com.shmingjiang.www.mldaq;

import com.shmingjiang.www.mldaq.bean.Pol1;
import com.shmingjiang.www.mldaq.bean.Pol2;
import com.shmingjiang.www.mldaq.bean.Pol3;
import com.shmingjiang.www.mldaq.bean.Pol4;
import com.shmingjiang.www.mldaq.bean.WeldPower;
import com.shmingjiang.www.mldaq.bean.WeldThick;
import com.shmingjiang.www.mldaq.bean.WeldTime;

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
