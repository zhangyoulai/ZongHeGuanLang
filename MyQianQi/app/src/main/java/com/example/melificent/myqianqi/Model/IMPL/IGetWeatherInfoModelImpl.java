package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.WeatherBean.Result;
import com.example.melificent.myqianqi.Bean.WeatherBean.WeatherAllDayRealTime;
import com.example.melificent.myqianqi.Model.IGetWeatherInfoModel;
import com.example.melificent.myqianqi.NetService.WeatherApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/14.
 */

public class IGetWeatherInfoModelImpl implements IGetWeatherInfoModel {


    @Override
    public void getWeatherInfo(final AsyncCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getWeatherInfobyAreaIDURL)
                .build();
        WeatherApiService service = retrofit.create(WeatherApiService.class);

        Call<WeatherAllDayRealTime>  call = service
                .getWeatherData();
        call.enqueue(new Callback<WeatherAllDayRealTime>() {
            @Override
            public void onResponse(Response<WeatherAllDayRealTime> response, Retrofit retrofit) {
                WeatherAllDayRealTime weather = response.body();
                Log.i("body", "onResponse: "+response.body());
                Log.i("error_code", "error_code: "+weather.error_code);
                if (weather.error_code == 0){
                    Result weather_result =weather.result;
                    Log.i("GetWeatherInfoModelImpl", "onResponse: "+weather_result);
                    callBack.OnSucess(weather_result);
                }else{
                    callBack.OnError(weather.reason);
                    Log.i("fail_reason", "reason: "+weather.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("error", "onFailure: "+t.getMessage());
            }
        });
    }
}
