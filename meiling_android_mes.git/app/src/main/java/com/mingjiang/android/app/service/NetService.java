package com.mingjiang.android.app.service;

import com.mingjiang.android.app.bean.AdjustResult;
import com.mingjiang.android.app.bean.AroundMaterialResult;
import com.mingjiang.android.app.bean.BadnessItem;
import com.mingjiang.android.app.bean.EquipmonitorMesssage;
import com.mingjiang.android.app.bean.FridgeParamsBean;
import com.mingjiang.android.app.bean.FridgeResponse;
import com.mingjiang.android.app.bean.HaveAuthority;
import com.mingjiang.android.app.bean.IndentInfo;
import com.mingjiang.android.app.bean.LineLogInfoItem;
import com.mingjiang.android.app.bean.LineLogItem;
import com.mingjiang.android.app.bean.MarrtialItem;
import com.mingjiang.android.app.bean.MaterialOneKeyAddResult;
import com.mingjiang.android.app.bean.MidMaterialResult;
import com.mingjiang.android.app.bean.Performance;
import com.mingjiang.android.app.bean.Plan;
import com.mingjiang.android.app.bean.PlanItem;
import com.mingjiang.android.app.bean.ResultData;
import com.mingjiang.android.app.bean.RetValue;
import com.mingjiang.android.app.bean.SessionObj;
import com.mingjiang.android.app.bean.SessionObjForPost;
import com.mingjiang.android.app.bean.SubmitDdata;
import com.mingjiang.android.app.bean.SubmitReturnData;
import com.mingjiang.android.app.bean.WeldmentValue;
import com.mingjiang.android.app.bean.YieldData;
import com.mingjiang.android.app.bean.YieldItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by wdongjia on 2016/9/8.
 */
public interface NetService {

    public static final String SESSIONID = "X-Openerp-Session-Id";
    public static final String CONTEXT = "context";
    public static final String CONTEXT_VALUE = "{\"verify\": 0}";

    //登录接口
    @POST("/api/userlogin/login/")
    Observable<SessionObj> login(@Query("login") String userCode, @Query(CONTEXT) String context);

    //获取出库物料信息
    @POST("/api/interface/public/material_manage.incoming_count/stock_android")
    Observable<List<MaterialOneKeyAddResult>> queryMaterial(@Query(MaterialOneKeyAddResult.OUT_CODE) String out_code);

    //提交一键入库操作
    @POST("/api/interface/public/material_manage.incoming_count/android_confirm")
    Observable<String> submitMaterial(@Query(MaterialOneKeyAddResult.CODE) String materialCode, @Query(MaterialOneKeyAddResult.NUMBER) String materialNumber);

    //中间库一键入库
    @POST("/api/interface/material_manage.incoming_count/stock_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<RetValue> addLibMaterial(@QueryMap Map<String, String> params, @Header(SESSIONID) String sessionId);

    //读取日计划
    @POST("/api/interface/public/plan_manage.daily_plan/process_serial_element")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<Plan>> queryDailyPlan();

    //获取二维码
    @POST("/api/interface/public/process_control.process/print_code")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> getQRCode(@QueryMap Map<String, String> params);

    //报废订单流水号
    @POST("/api/interface/public/quality_manage.scrapped_product/print_code")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<String>> getScrappedCode();

    //中间库物料数据查询
    @POST("/api/interface/public/material_manage.material_stock/repertory_for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<MidMaterialResult> queryMidMaterial(@QueryMap Map<String, String> options);

    //线边物料数据查询
    @POST("/api/interface/public/material_manage.line_stock/for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<AroundMaterialResult> queryAroundMaterial(@QueryMap Map<String, String> params);

    //请求停线记录
    @POST("/api/interface/public/craftwork.production.line/queryProdLineinfo")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<LineLogItem>> queryLineLog();

    //停线记录详情查询
    @POST("/api/interface/public/craftwork.production.line.log/queryProductLineLog")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<LineLogInfoItem>> queryLinLogInfo(@QueryMap Map<String, String> options);

    //请求五大不良数据集
    @POST("/g5b")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<BadnessItem>> queryBadness();

//    //请求日计划
//    @POST("/api/interface/public/plan_manage.daily_plan/monitor_progress")
//    @Headers({"Content-Type: application/x-www-form-urlencoded"})
//    Observable<List<PlanItem>> queryPlanData();

    //请求日计划
    @POST("/api/interface/public/process_control.process_trace/produce_monitor")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<PlanItem>> queryPlanData();

    //获取产量数据
    @POST("/api/interface/public/plan_manage.daily_plan/statistic_production")
    @Headers({"Content-Type:application/x-www-form-urlencoded", "Accept-Language:zh-CN"})
    Observable<YieldItem> queryYieldData(@QueryMap Map<String, String> options);

    //订单查询
    @POST("/api/interface/public/plan_manage.product_order/product_order_query")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<IndentInfo> queryIndentInfo(@QueryMap Map<String, String> options);

    // 发送接收物料请求
    @POST("/api/interface/material_manage.line_stock/receive_agv")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<ArrayList<MarrtialItem>> sendReceivenAsk(@QueryMap Map<String, String> params, @Header(SESSIONID) String sessionId);

    // 发送接收物料信息
    @POST("/api/interface/material_manage.line_stock/confirm_receivr")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendReceivenData(@QueryMap Map<String, String> params, @Header(SESSIONID) String sessionId);

    // 发送手动叫料信息
    @POST("/api/interface/material_manage.line_stock/confirm_receivr")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendRequestData(@QueryMap Map<String, String> params, @Header(SESSIONID) String sessionId);

    // 发送物料调整的数据
    @POST("/api/interface/json/material_manage.line_stock/refresh_material")
    @Headers("Content-Type: application/json")
    Observable<AdjustResult> sendFridgePostBean(@Body SubmitDdata params, @Header(SESSIONID) String sessionId);

    // 发送物料退料的数据
    @POST("/api/interface/json/material_manage.line_stock/return_material")
    @Headers("Content-Type: application/json")
    Observable<ResultData> sendReturnData(@Body SubmitReturnData params, @Header(SESSIONID) String sessionId);

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

    // 发送冰箱与岗位信息
    @POST("/api/interface/json/process_control.process/trace_process")
    @Headers({"Content-Type: application/json"})
    Observable<FridgeResponse> sendFridgePostBean(@Body FridgeParamsBean params, @Header(SESSIONID) String sessionId);

    //获取产量数据
    @POST("/api/interface/public/process_control.count_over_post/count_over_post")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<YieldData>> getYieldData(@QueryMap Map<String, String> options, @Header(SESSIONID) String sessionId);

    //校验用户是否为质检站长
    @GET("/check_inpspect_post/{code}")
    Observable<HaveAuthority> isInspectionMaster(@Path("code") String code, @Header(SESSIONID) String sessionId);

    //校验用户
    @FormUrlEncoded
    @POST("/api/userlogin/login")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<SessionObjForPost> isUser(@Field("login") String userCode, @Field("context") String context);

    //校验用户是否为本岗位用户
    @FormUrlEncoded
    @POST("/api/userlogin/workstation")
    Observable<SessionObj> isRightUser(@Field("login") String userCode,@Field("workstation") String postCode);

    @POST("/api/interface/quality_manage.up_down_record/onoff")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> submitOnOffLine(@QueryMap Map<String,String> params,@Header(SESSIONID) String sessionId);

    @POST("/api/interface/public/operation.instruction/get_info_type")
    @Headers({"Content-Type: form-data"})
    Observable<FridgeResponse> getPDFInfo(@QueryMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/api/interface/public/mj_alldata/get_datas")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<Performance>> getPerfomance(@Field("value") String value);
}
