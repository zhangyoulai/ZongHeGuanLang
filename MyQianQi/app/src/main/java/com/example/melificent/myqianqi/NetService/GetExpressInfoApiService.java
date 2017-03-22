package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.Express.ExpressBean;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/16.
 */

public interface GetExpressInfoApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=264faad0144bc26cd6b87e537a1b6769")
    Call<ExpressBean> getExpressInfo(
            @Query("com")String com,
            @Query("no") String no
    );
}
