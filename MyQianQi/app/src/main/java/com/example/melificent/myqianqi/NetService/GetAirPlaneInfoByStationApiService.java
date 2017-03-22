package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoBeanByStation;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/16.
 */

public interface GetAirPlaneInfoByStationApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=fd16273c518b8d319db290fdcc6017f7")
    Call<AirPlaneInfoBeanByStation> getAirPlaneInfoBeanByStation(
            @Query("start") String start,
            @Query("end") String end
    );
}
