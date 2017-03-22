package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoBeanByName;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;
import com.example.melificent.myqianqi.Model.IGetAirPlaneInfoByNameModel;
import com.example.melificent.myqianqi.NetService.GetAirPlaneInfoByNameApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirPlaneInfoByNameModelImpl implements IGetAirPlaneInfoByNameModel {
    @Override
    public void getAirPlaneInfo(String name, String date, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getAirPlaneInfoByNameBaseURL)
                .build();
        GetAirPlaneInfoByNameApiService service = retrofit.create(GetAirPlaneInfoByNameApiService.class);
        Call<AirPlaneInfoBeanByName> call = service.getAirPlaneInfoBeanByName(name,date);
        call.enqueue(new Callback<AirPlaneInfoBeanByName>() {
            @Override
            public void onResponse(Response<AirPlaneInfoBeanByName> response, Retrofit retrofit) {
                AirPlaneInfoBeanByName airPlaneInfoBeanByName = response.body();
                Log.i("airplaneinfobeanbyname", "onResponse: "+airPlaneInfoBeanByName);
                if (airPlaneInfoBeanByName.error_code == 0){
                    asyncCallBack.OnSucess(airPlaneInfoBeanByName.result);
                }else {
                    asyncCallBack.OnError(airPlaneInfoBeanByName.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("getairplaneinfofail", "onFailure: "+t.getMessage());
            }
        });

    }
}
