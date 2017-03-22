package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;

/**
 * Created by p on 2017/3/16.
 */

public interface GetAirPlaneInfoByName {
    public void getAirPlaneInfoByNameSuccess(AirPlaneInfoResult result);
    public void getAirPlaneInfoByNameFail(String error_Msg);
}
