package com.example.melificent.myqianqi.Model.IMPL;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.EveryHourWeather.EveryHourWeatherBean;
import com.example.melificent.myqianqi.Model.IGetEveryHourWeatherModel;
import com.example.melificent.myqianqi.NetService.EveryHourWeatherInfoApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/21.
 */

public class IGetEveryHourWeatherModelImpl implements IGetEveryHourWeatherModel {
    @Override
    public void getWeatherbyHour(String startTime, String endTime, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getEveryHourWeatherBaseURL)
                .build();
        EveryHourWeatherInfoApiService service = retrofit.create(EveryHourWeatherInfoApiService.class);
        Call<EveryHourWeatherBean> call = service.getEveryHourWeather(startTime,endTime);
        call.enqueue(new Callback<EveryHourWeatherBean>() {
            @Override
            public void onResponse(Response<EveryHourWeatherBean> response, Retrofit retrofit) {
                EveryHourWeatherBean everyHourWeatherBean = response.body();
                if (everyHourWeatherBean.error_code == 0){
                    asyncCallBack.OnSucess(everyHourWeatherBean.result);
                }else {
                    asyncCallBack.OnError(everyHourWeatherBean.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
