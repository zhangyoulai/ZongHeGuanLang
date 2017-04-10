package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 *
 */
public class SessionObj {
    public String session_id="";
    public String error = "";
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
