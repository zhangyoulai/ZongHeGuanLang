package com.mingjiang.android.equipmonitor.entity;

import com.google.gson.Gson;

/**
 * 向服务器发送冰箱岗位信息后，返回的
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public class WeldmentResponse {
    public String jsonrpc;
    public int id;
    public WeldmentResponseResult result;

    public WeldmentResponse() {
    }


    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeldmentResponseResult getResult() {
        return result;
    }

    public void setResult(WeldmentResponseResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
