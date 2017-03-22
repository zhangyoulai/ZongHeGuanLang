package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.melificent.myqianqi.Bean.AirQualityBean.AirQualityResult;
import com.example.melificent.myqianqi.Bean.AirQualityBean.Kdatalistey;
import com.example.melificent.myqianqi.Presenter.IGetAirQualityPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirQualityPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetAirQuality;

import java.util.List;

/**
 * Created by p on 2017/3/16.
 */

public class AirQualityActivity extends AppCompatActivity implements GetAirQuality {
    IGetAirQualityPresenter presenter = new IGetAirQualityPresenterImpl(this);
    AirQualityResult result ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_home_fragment);
        presenter.getAirQualityPresenter();
    }

    @Override
    public void getAirQualitySuccess(AirQualityResult result) {
        this.result = result;
        Log.i("beginTime", "beginTime: "+System.currentTimeMillis());
        List<Kdatalistey> kdatalisteys = result.kdatalistey;
        for (Kdatalistey kdatalistey: kdatalisteys
             ) {
            if (kdatalistey.areaid.equals("101080705")){
                Log.i("AirQualityaqi", "getAirQualityApi: "+kdatalistey.aqi);
                Log.i("AirQualityCO", "getAirQualityCO: "+kdatalistey.co);
            };
        }
    }

    @Override
    public void getAirQualityFail(String error_Msg) {
        Log.i("getAirQualityFail", "getAirQualityFail: "+error_Msg);
    }
}
