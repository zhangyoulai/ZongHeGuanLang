package com.example.melificent.myqianqi.Model;

import com.example.melificent.myqianqi.AsyncCallBack;

/**
 * Created by p on 2017/3/16.
 */

public interface IGetRealNameRegexResultModel {
    public void getRealNameRegexResult(String realname, String idcard, AsyncCallBack asyncCallBack);
}
