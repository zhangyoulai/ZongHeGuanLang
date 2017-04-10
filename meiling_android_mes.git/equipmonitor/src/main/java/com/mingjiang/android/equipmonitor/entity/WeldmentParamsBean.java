package com.mingjiang.android.equipmonitor.entity;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */

public class WeldmentParamsBean {
    public WeldmentPostBean params;

    public WeldmentPostBean getParams() {
        return params;
    }

    public  void setParams(WeldmentPostBean params) {
        this.params = params;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
