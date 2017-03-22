package com.example.melificent.myqianqi.Model.IMPL;

import android.util.Log;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolation;
import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolationResult;
import com.example.melificent.myqianqi.Model.IGetVehicleViolationInfoModel;
import com.example.melificent.myqianqi.NetService.VehicleViolationApiService;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by p on 2017/3/15.
 */

public class IGetVehicleViolationInfoModelImpl implements IGetVehicleViolationInfoModel {
    private static final String TAG = "GetVehicleViolationInfo";
    List<VehicleViolationResult> results = new ArrayList<>();
    @Override
    public void getVehicleViolationInfo( String frameNo,String enginNo,String carNo,final AsyncCallBack asyncCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalContants.getVehicleViolationInfoBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VehicleViolationApiService service = retrofit.create(VehicleViolationApiService.class);
        Call<VehicleViolation> call = service.getVehicleViolation(frameNo,enginNo,carNo);
        Log.i("params", "frameNo: "+frameNo+",enginNo: "+enginNo+",carNo: "+carNo);
        call.enqueue(new Callback<VehicleViolation>() {
            @Override
            public void onResponse(Response<VehicleViolation> response, Retrofit retrofit) {
                VehicleViolation vehicleViolation = response.body();
                Log.i("VehicleViolationBody", "onResponse: "+vehicleViolation);
                Log.i("VehicleVioError_code", "error_code: "+vehicleViolation.error_code);
                Log.i("VehicleViolationReason", "reason: "+vehicleViolation.reason);
                if (vehicleViolation.error_code == 0){
                    if (vehicleViolation.reason.equals("该车辆没有违章记录")){
                        GlobalContants.NoVehicleViolationBehavior = true;
                        asyncCallBack.OnSucess(vehicleViolation.reason);
                    }else {
                        asyncCallBack.OnSucess(vehicleViolation.results);
                    }
                }else {
                    Log.i(TAG, "onError: "+vehicleViolation.reason);
                    asyncCallBack.OnError(vehicleViolation.reason);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
