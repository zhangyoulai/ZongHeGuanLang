package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoBeanByStation;
import com.example.melificent.myqianqi.Model.IGetAirPlaneInfoByNameModel;
import com.example.melificent.myqianqi.Model.IGetAirPlaneInfoByStationModel;
import com.example.melificent.myqianqi.NetService.GetAirPlaneInfoByStationApiService;
import com.example.melificent.myqianqi.NetService.GetTrainInfoByStationApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirPlaneInfoByStationModelImpl implements IGetAirPlaneInfoByStationModel {

    @Override
    public void getAirPlaneInfoByStation(String start, String end, final AsyncCallBack asyncCallBack) {
        Retrofit retrofit =new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GlobalContants.getAirPlaneInfoByStationBaseURL)
                .build();
        GetAirPlaneInfoByStationApiService service=retrofit.create(GetAirPlaneInfoByStationApiService.class);
        Call<AirPlaneInfoBeanByStation> call = service.getAirPlaneInfoBeanByStation(start,end);
        call.enqueue(new Callback<AirPlaneInfoBeanByStation>() {
            @Override
            public void onResponse(Response<AirPlaneInfoBeanByStation> response, Retrofit retrofit) {
                AirPlaneInfoBeanByStation airPlaneInfoBeanByStation = response.body();
                Log.i("airplaneinfobystation", "onResponse: "+airPlaneInfoBeanByStation);
                if (airPlaneInfoBeanByStation.error_code == 0){
                    asyncCallBack.OnSucess(airPlaneInfoBeanByStation.result);
                }else {
                    asyncCallBack.OnError(airPlaneInfoBeanByStation.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("airinfobystationfail", "onFailure: "+t.getMessage());
            }
        });
    }
}
