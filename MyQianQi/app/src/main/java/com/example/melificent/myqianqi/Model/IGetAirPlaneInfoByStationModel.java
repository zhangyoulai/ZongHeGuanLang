package com.example.melificent.myqianqi.Model;

import com.example.melificent.myqianqi.AsyncCallBack;

/**
 * Created by p on 2017/3/16.
 */

public interface IGetAirPlaneInfoByStationModel {
    public void getAirPlaneInfoByStation(String start, String end,String date, AsyncCallBack asyncCallBack);
}
