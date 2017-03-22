package com.example.melificent.myqianqi.Model;

import com.example.melificent.myqianqi.AsyncCallBack;

/**
 * Created by p on 2017/3/15.
 */

public interface IGetVehicleViolationInfoModel {
    public void getVehicleViolationInfo( String frameNo,String engineNo,String carNo,AsyncCallBack asyncCallBack);
}
