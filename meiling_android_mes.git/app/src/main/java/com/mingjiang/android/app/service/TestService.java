package com.mingjiang.android.app.service;

import com.mingjiang.android.app.bean.Performance;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by CaoBin on 2016/9/21.
 */
public interface TestService {

    @FormUrlEncoded
    @POST("/api/interface/public/mj_alldata/get_datas")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<Performance>> getPerfomance(@Field("value") String value);
}
