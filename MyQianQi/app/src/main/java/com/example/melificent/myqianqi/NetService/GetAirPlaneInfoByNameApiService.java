package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoBeanByName;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/16.
 */

public interface GetAirPlaneInfoByNameApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=fd16273c518b8d319db290fdcc6017f7")
    Call<AirPlaneInfoBeanByName> getAirPlaneInfoBeanByName(
            @Query("name") String name,
            @Query("date") String date
    );
}
