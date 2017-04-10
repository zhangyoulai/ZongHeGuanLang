package com.example.hasee.compare.dao;

import android.content.Context;

import com.example.hasee.compare.bean.CodeAndSpec;

public class InsertPreparedData {
    public static void insertData(Context context){
        CodeAndSpec ib1 = new CodeAndSpec("9011655","BCD-560WUPBY");
        CodeAndSpec ib2 = new CodeAndSpec("9013427","BCD-568WPBD");
        CodeAndSpec ib3 = new CodeAndSpec("9010710","BCD-570WEC");
        IbDataBaseUtil.createDataBase(context);
        IbDataBaseUtil.insert(context, ib1);
        IbDataBaseUtil.insert(context, ib2);
        IbDataBaseUtil.insert(context, ib3);
    }
}
