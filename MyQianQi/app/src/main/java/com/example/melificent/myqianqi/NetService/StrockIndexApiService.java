package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexBean;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/17.
 */

public interface StrockIndexApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=a23cd4983655b336fbd1f440acfb1152")
    Call<StrockIndexBean> getStrockIndex(
            @Query("type") int type
    );
}
