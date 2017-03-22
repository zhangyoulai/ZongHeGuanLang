package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.Express.ExpressBean;
import com.example.melificent.myqianqi.Model.IGetExpressInfoModel;
import com.example.melificent.myqianqi.NetService.GetExpressInfoApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/16.
 */

public class IGetExpressInfoModelImpl implements IGetExpressInfoModel {
    @Override
    public void getExpressInfo(String com, String no, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getExpressInfoBaseURL)
                .build();
        GetExpressInfoApiService service = retrofit.create(GetExpressInfoApiService.class);
        Call<ExpressBean> call= service.getExpressInfo(com,no);
        call.enqueue(new Callback<ExpressBean>() {
            @Override
            public void onResponse(Response<ExpressBean> response, Retrofit retrofit) {
                ExpressBean expressBean = response.body();
                Log.i("expressbeanbody", "onResponse: "+expressBean);
                if (expressBean.error_code == 0 ){
                    asyncCallBack.OnSucess(expressBean.result);
                }else {
                    asyncCallBack.OnError(expressBean.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
