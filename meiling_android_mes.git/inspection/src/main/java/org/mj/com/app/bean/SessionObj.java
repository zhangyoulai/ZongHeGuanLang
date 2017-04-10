package org.mj.com.app.bean;

import com.google.gson.Gson;

/**
 * Created by wangzs on 2015/12/17.
 */
public class SessionObj {
    public String session_id="";
    public String login = "";
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
