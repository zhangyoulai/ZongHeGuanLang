package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoBeanByStation;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/15.
 */

public interface GetTrainInfoByStationApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=c1050b969b5fb25a392c15f661edaa57")
    Call<TrainInfoBeanByStation> getTrainInfoBeanByStation(
            @Query("start") String start,
            @Query("end") String end
    );
}
