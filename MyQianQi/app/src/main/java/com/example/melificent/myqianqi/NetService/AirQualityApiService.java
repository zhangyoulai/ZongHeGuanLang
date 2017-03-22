package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.AirQualityBean.AirQualityBean;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by p on 2017/3/16.
 */

public interface AirQualityApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=482670f5b8546fb893c1a6f157b184fb")
    Call<AirQualityBean> getAllAirQuality();
}
