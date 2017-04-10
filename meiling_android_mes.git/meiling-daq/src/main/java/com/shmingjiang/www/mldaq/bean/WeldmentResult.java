package com.shmingjiang.www.mldaq.bean;

import com.google.gson.Gson;


/**
 * Created by wdongjia on 2016/9/2.
 */
public class WeldmentResult {

    public String result_size = ""; //返回记录数

    public WeldmentValue result = new WeldmentValue();//返回记录

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
