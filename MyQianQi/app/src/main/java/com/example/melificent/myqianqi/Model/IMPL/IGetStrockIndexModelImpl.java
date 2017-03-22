package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexBean;
import com.example.melificent.myqianqi.Model.IGetStrockIndexModel;
import com.example.melificent.myqianqi.NetService.StrockIndexApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/17.
 */

public class IGetStrockIndexModelImpl implements IGetStrockIndexModel {
    @Override
    public void getStrockIndex(int type, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(GlobalContants.getStrockIndexBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StrockIndexApiService service = retrofit.create(StrockIndexApiService.class);
        Call<StrockIndexBean> call= service.getStrockIndex(type);
        call.enqueue(new Callback<StrockIndexBean>() {
            @Override
            public void onResponse(Response<StrockIndexBean> response, Retrofit retrofit) {
                StrockIndexBean strockIndexBean = response.body();
                Log.i("strockindexbeanbody", "onResponse: "+strockIndexBean);
                if (strockIndexBean.error_code == 0){
                    asyncCallBack.OnSucess(strockIndexBean.result);
                }else {
                    asyncCallBack.OnError(strockIndexBean.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("strockindexfail", "onFailure: "+t.getMessage());
            }
        });
    }
}
