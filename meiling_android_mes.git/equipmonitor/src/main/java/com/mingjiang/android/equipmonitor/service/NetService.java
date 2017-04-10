package com.mingjiang.android.equipmonitor.service;



import com.mingjiang.android.equipmonitor.entity.EquipmonitorMesssage;
import com.mingjiang.android.equipmonitor.entity.WeldmentValue;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by wdongjia on 2016/9/2.
 */
public interface NetService {

    //获取设备监控管理信息
    @GET("/api/operation.instruction/get_info")
    Observable<EquipmonitorMesssage> getEquipmonitorMessageByCode();

    //上传超声波焊接机工艺数据
    @POST("/api/interface/public/device_weldments/post_data")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendWelementData(@QueryMap Map<String, String> options);

    //查询超声波焊接机工艺数据
    @POST("/api/interface/public/device_weldments/load_data")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<WeldmentValue> queryWeldmentData();
}
