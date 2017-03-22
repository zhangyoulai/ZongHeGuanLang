package com.example.melificent.myqianqi.Model;

import com.example.melificent.myqianqi.AsyncCallBack;

/**
 * Created by p on 2017/3/15.
 */

public interface IGetTrainInfoByStationModel {
    public  void getTrainInfo(String start, String end, AsyncCallBack callBack);
}
