package com.example.melificent.myqianqi.NetService;

import com.example.melificent.myqianqi.Bean.RealNameRegex.RealNameRegexBean;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by p on 2017/3/16.
 */

public interface GetRealNameResultApiService {
    @Headers(GlobalContants.HeadsSet)
    @GET("?key=c8818fdc9f95dd5282bcb1b42717b8c8")
    Call<RealNameRegexBean> getRealNameRegexResult(
            @Query("realname") String realname,
            @Query("idcard") String idcard
    );
}
