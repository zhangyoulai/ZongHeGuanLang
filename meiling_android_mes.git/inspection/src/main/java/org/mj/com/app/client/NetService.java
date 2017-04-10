package org.mj.com.app.client;

import org.mj.com.app.bean.HaveAuthority;
import org.mj.com.app.bean.SessionObj;

import java.util.List;
import java.util.Map;

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
 * 备注：上线/下线网络通信。
 * 作者：wangzs on 2016/2/19 14:03
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface NetService {
    String SESSIONID = "X-Openerp-Session-Id";
    //提交检测结果
    @POST("/api/interface/quality_manage.up_down_record/onoff")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> submitOnOffLine(@QueryMap Map<String, String> params, @Header(SESSIONID) String sessionId);

    //校验用户是否为本岗位用户
    @FormUrlEncoded
    @POST("/api/userlogin/workstation")
    Observable<SessionObj> isRightUser(@Field("login") String userCode,@Field("workstation") String postCode);

    //校验用户是否为质检站长
    @GET("/check_inpspect_post/{code}")
    Observable<HaveAuthority> isInspectionMaster(@Path("code") String code, @Header(SESSIONID) String sessionId);

    //校验用户
    @FormUrlEncoded
    @POST("/api/userlogin/login")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<SessionObj> isUser(@Field("login") String userCode,@Field("context") String context);
}
