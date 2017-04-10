package com.shmingjiang.www.mldaq.service;

import com.shmingjiang.www.mldaq.bean.WeldmentPostBean;
import com.shmingjiang.www.mldaq.bean.WeldmentResult;
import com.shmingjiang.www.mldaq.bean.WeldmentValue;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by wdongjia on 2016/9/2.
 */
public interface NetService {

    //上传超声波焊接机工艺数据
    @POST("/api/interface/public/device_weldments/post_data")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendWelementData(@QueryMap Map<String, String> options);

    //查询超声波焊接机工艺数据
    @POST("/api/interface/public/device_weldments/load_data")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<WeldmentValue> queryWeldmentData();
}
