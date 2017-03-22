package com.example.melificent.myqianqi.Model;

import com.example.melificent.myqianqi.AsyncCallBack;

import java.nio.channels.AsynchronousCloseException;

/**
 * Created by p on 2017/3/16.
 */

public interface IGetAirPlaneInfoByNameModel {
    public void getAirPlaneInfo(String name, String date, AsyncCallBack asyncCallBack);
}
