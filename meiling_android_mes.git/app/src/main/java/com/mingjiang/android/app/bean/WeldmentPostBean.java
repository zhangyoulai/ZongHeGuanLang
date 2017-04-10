package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 * 冰箱与工位信息上传到服务器的bean
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public class WeldmentPostBean {
    public String[] args;
    public WeldmentBean kwargs;

    public WeldmentPostBean(String date, String time,String number,String height,String time1,String energy) {

        this.args = new String[0];
        this.kwargs = new WeldmentBean(date,time,number,height,time1,energy);
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public WeldmentBean getKwargs() {
        return kwargs;
    }

    public void setKwargs(WeldmentBean kwargs) {
        this.kwargs = kwargs;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
