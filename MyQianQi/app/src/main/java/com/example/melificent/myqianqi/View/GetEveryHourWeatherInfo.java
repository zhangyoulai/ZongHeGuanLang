package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.EveryHourWeather.EveryHourResult;

/**
 * Created by p on 2017/3/21.
 */

public interface GetEveryHourWeatherInfo {
    public void getWeatherInfoSuccess(EveryHourResult result);
    public void getWeatherInfoFail(String Msg);
}
