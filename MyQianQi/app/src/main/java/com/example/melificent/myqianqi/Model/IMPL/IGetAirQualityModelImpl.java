package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirQualityBean.AirQualityBean;
import com.example.melificent.myqianqi.Model.IGetAirQualityModel;
import com.example.melificent.myqianqi.NetService.AirQualityApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirQualityModelImpl implements IGetAirQualityModel {
    @Override
    public void getAirQuality(final AsyncCallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getAirQualityBaseURL)
                .build();
        AirQualityApiService service = retrofit.create(AirQualityApiService.class);
        Call<AirQualityBean> call = service.getAllAirQuality();
        call.enqueue(new Callback<AirQualityBean>() {
            @Override
            public void onResponse(Response<AirQualityBean> response, Retrofit retrofit) {
                AirQualityBean airQualityBean = response.body();
                Log.i("airQualityBean", "onResponse: "+airQualityBean);
                if (airQualityBean.error_code == 0){
                    callback.OnSucess(airQualityBean.result);
                }else {
                    callback.OnError(airQualityBean.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("getairqualityfail", "onFailure: "+t.getMessage());
            }
        });
    }


}
