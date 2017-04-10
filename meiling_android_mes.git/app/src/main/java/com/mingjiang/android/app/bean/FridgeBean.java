package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public class FridgeBean {
    public String serial_number;  //冰箱编号
    public FridgeTraceBean trace;



    public FridgeBean() {
    }

    public FridgeBean(String serial_number, String postCode) {
        this.serial_number = serial_number;
        this.trace = new FridgeTraceBean();
        this.trace.operation = "produce";
        this.trace.post_code = postCode;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public FridgeTraceBean getTrace() {
        return trace;
    }

    public void setTrace(FridgeTraceBean trace) {
        this.trace = trace;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
