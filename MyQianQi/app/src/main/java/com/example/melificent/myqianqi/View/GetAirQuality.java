package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.AirQualityBean.AirQualityResult;

/**
 * Created by p on 2017/3/16.
 */

public interface GetAirQuality {
    public void getAirQualitySuccess(AirQualityResult result);
    public void getAirQualityFail(String error_Msg);
}
