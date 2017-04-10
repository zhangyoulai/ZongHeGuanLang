package meiling.mingjiang.line_storage.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meiling.mingjiang.line_storage.adjustment_fragment.AdjustResult;
import meiling.mingjiang.line_storage.adjustment_fragment.SubmitDdata;
import meiling.mingjiang.line_storage.bean.AroundMaterialResult;
import meiling.mingjiang.line_storage.marrtial_fragment.MarrtialItem;
import meiling.mingjiang.line_storage.return_fragment.ResultData;
import meiling.mingjiang.line_storage.return_fragment.SubmitReturnData;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * 备注：
 * 作者：wangzs on 2016/2/20 10:40
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface NetService {

    public static final String SESSIONID = "X-Openerp-Session-Id";
    public static final String CONTEXT  = "context";
    public static final String CONTEXT_VALUE = "{\"verify\": 0}";

    //线边物料数据查询
    @POST("/api/interface/public/material_manage.line_stock/for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<AroundMaterialResult> queryAroundMaterial(@QueryMap Map<String, String> params);

    // 发送接收物料请求
    @POST("/api/interface/material_manage.line_stock/receive_agv")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<ArrayList<MarrtialItem>> sendReceivenAsk(@QueryMap Map<String,String> params,@Header(SESSIONID) String sessionId);

    // 发送接收物料信息
    @POST("/api/interface/material_manage.line_stock/confirm_receivr")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendReceivenData(@QueryMap Map<String,String> params,@Header(SESSIONID) String sessionId);

    // 发送手动叫料信息
    @POST("/api/interface/material_manage.line_stock/confirm_receivr")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> sendRequestData(@QueryMap Map<String,String> params,@Header(SESSIONID) String sessionId);

    // 发送物料调整的数据
    @POST("/api/interface/json/material_manage.line_stock/refresh_material")
    @Headers("Content-Type: application/json")
    Observable<AdjustResult> sendFridgePostBean(@Body SubmitDdata params,@Header(SESSIONID) String sessionId);

    // 发送物料退料的数据
    @POST("/api/interface/json/material_manage.line_stock/return_material")
    @Headers("Content-Type: application/json")
    Observable<ResultData> sendReturnData(@Body SubmitReturnData params,@Header(SESSIONID) String sessionId);

    //登录接口
    @POST("/api/userlogin/login/")
    Observable<SessionObj> login(@Query("login") String userCode, @Query(CONTEXT) String context);
}
