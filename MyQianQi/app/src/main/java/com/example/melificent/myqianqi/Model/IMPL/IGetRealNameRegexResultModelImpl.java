package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.RealNameRegex.RealNameRegexBean;
import com.example.melificent.myqianqi.Model.IGetRealNameRegexResultModel;
import com.example.melificent.myqianqi.NetService.GetRealNameResultApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/16.
 */

public class IGetRealNameRegexResultModelImpl implements IGetRealNameRegexResultModel {
    @Override
    public void getRealNameRegexResult(String realname, String idcard, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.realNameRegexBaseURL)
                .build();
        GetRealNameResultApiService service = retrofit.create(GetRealNameResultApiService.class);
        Call<RealNameRegexBean> call = service.getRealNameRegexResult(realname,idcard);
        call.enqueue(new Callback<RealNameRegexBean>() {
            @Override
            public void onResponse(Response<RealNameRegexBean> response, Retrofit retrofit) {
                RealNameRegexBean realNameRegexBean = response.body();
                Log.i("realnameregexbody", "onResponse: "+realNameRegexBean);
                if (realNameRegexBean.error_code == 0 ){
                    asyncCallBack.OnSucess(realNameRegexBean.result);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("realnameregexfail", "onFailure: "+t.getMessage());
            }
        });
    }
}
