package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 * Created by wdongjia on 2016/9/20.
 */
public class SessionObjForPost {
    public String session_id="";
    public String login = "";
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
