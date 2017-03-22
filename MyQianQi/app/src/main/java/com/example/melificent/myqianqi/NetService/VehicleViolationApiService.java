package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolation;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/15.
 */

public interface VehicleViolationApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=af425987a5fc04e12f1229c20e190e18&carType=02")//v.juhe.cn/wzdj/querywz.php?carNo=%E5%AE%81AFN013&frameNo=050378&enginNo=4616&carType=02&key=567b61eaedb42395bc8880f79a0af63b
    Call<VehicleViolation> getVehicleViolation(
            @Query("frameNo") String frameNo,
            @Query("enginNo") String enginNo,
            @Query("carNo") String carNo
    );


}
