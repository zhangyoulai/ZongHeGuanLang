package app.android.mingjiang.com.qrcode.service;

import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.qrcode.bean.Plan;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by wdongjia on 2016/9/8.
 */
public interface NetService {

    //读取日计划
    @POST("/api/interface/public/plan_manage.daily_plan/process_serial_element")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<Plan>> queryDailyPlan();

    //获取二维码
    @POST("/api/interface/public/process_control.process/print_code")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> getQRCode(@QueryMap Map<String,String> params);

    //报废订单流水号
    @POST("/api/interface/public/quality_manage.scrapped_product/print_code")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<String>> getScrappedCode();

}
