package meiling.mingjiang.instructionpdf.client;
import java.util.List;
import java.util.Map;

import meiling.mingjiang.instructionpdf.entity.FridgeParamsBean;
import meiling.mingjiang.instructionpdf.entity.FridgeResponse;
import meiling.mingjiang.instructionpdf.entity.YieldData;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
public interface NetService {

   String SESSIONID = "X-Openerp-Session-Id";
    // 发送冰箱与岗位信息
    @POST("/api/interface/json/process_control.process/trace_process")
    @Headers({"Content-Type: application/json"})
    Observable<FridgeResponse> sendFridgePostBean(@Body FridgeParamsBean params, @Header(SESSIONID) String sessionId);
    //获取产量数据
    @POST("/api/interface/public/process_control.count_over_post/count_over_post")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<YieldData>> getYieldData(@QueryMap Map<String, String> options, @Header(SESSIONID) String sessionId);
}