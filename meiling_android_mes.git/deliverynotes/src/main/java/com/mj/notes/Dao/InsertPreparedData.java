package com.mj.notes.Dao;

import android.content.Context;

import com.mj.notes.Constant;
import com.mj.notes.bean.IceBox;

public class InsertPreparedData {
    public static void insertData(Context context){
        IceBox ib1 = new IceBox("9011655","BCD-560WUPBY", "1202537818","万鑫鑫","合肥美菱","合肥经济开发区莲花路2163号","4008-111-666");
        IceBox ib2 = new IceBox("9012267","BCD-568WPBD", "1002537829","黄和进","上海明匠智能","上海市嘉定区胜辛北路3555号","021－60675988");
        IceBox ib3 = new IceBox("9012270","BCD-570WEC", "1002547959","符星","四川长虹工程技术中心","四川省绵阳市高新区绵新东路35号","4008-111-666");
        Constant.r3CodeList.add("9011655");
        Constant.r3CodeList.add("9012267");
        Constant.r3CodeList.add("9012270");
        IbDataBaseUtil.createDataBase(context);
        IbDataBaseUtil.insert(context, ib1);
        IbDataBaseUtil.insert(context, ib2);
        IbDataBaseUtil.insert(context, ib3);
    }
}
