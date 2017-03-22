package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.ShanghaiAndShenzhenStrock.HuShenStrockBean;
import com.example.melificent.myqianqi.Model.IGetHuShenStrockModel;
import com.example.melificent.myqianqi.NetService.HuShenStrockApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/17.
 */

public class IGetHuShenStrockModelImpl implements IGetHuShenStrockModel {
    @Override
    public void getHuShenStrock(String pid, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalContants.getStrockIndexBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HuShenStrockApiService service = retrofit.create(HuShenStrockApiService.class);
        Call<HuShenStrockBean> call= service.getHuShenStrockBean(pid);
        call.enqueue(new Callback<HuShenStrockBean>() {
            @Override
            public void onResponse(Response<HuShenStrockBean> response, Retrofit retrofit) {
                HuShenStrockBean huShenStrockBean = response.body();
                Log.i("hushenstrockbeanbody", "onResponse: "+huShenStrockBean);
                if (huShenStrockBean.error_code == 0){
                    asyncCallBack.OnSucess(huShenStrockBean.result);
                }else {
                    asyncCallBack.OnError(huShenStrockBean.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("hushenstrockfail", "onFailure: "+t.getMessage());
            }
        });
    }
}
