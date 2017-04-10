package com.mingjiang.android.app.bean;

import java.io.Serializable;

/**
 * Created by wangdongjia on 2016/1/9.
 */
public class Plan implements Serializable {
    private String export_id;
    private String export;

    public String getExport_id() {
        return export_id;
    }

    public void setExport_id(String export_id) {
        this.export_id = export_id;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    //日计划日期
    private String date;
    //产品型号
    private String spec;
    //产品编码
    private String r3code;
    //订单号
    private String order;
    //日订单数量
    private int number;
    //已打印数量
    private int completed;

    private boolean flag = false;

    public Plan(String export_id, String export, String date, String spec, String r3code, String order,
                int number, int completed) {
        this.export_id = export_id;
        this.export = export;
        this.date = date;
        this.spec = spec;
        this.r3code = r3code;
        this.order = order;
        this.number = number;
        this.completed = completed;

    }

    public Plan() {
        this.flag = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getR3code() {
        return r3code;
    }

    public void setR3code(String r3code) {
        this.r3code = r3code;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
