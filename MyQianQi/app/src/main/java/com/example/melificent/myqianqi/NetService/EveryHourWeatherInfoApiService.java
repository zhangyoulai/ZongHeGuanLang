package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.EveryHourWeather.EveryHourWeatherBean;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/21.
 */

public interface EveryHourWeatherInfoApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=482670f5b8546fb893c1a6f157b184fb&areaid=101080705")
    Call<EveryHourWeatherBean> getEveryHourWeather(
            @Query("startTime") String startTime,
            @Query("endTime") String endTime
    );
}
