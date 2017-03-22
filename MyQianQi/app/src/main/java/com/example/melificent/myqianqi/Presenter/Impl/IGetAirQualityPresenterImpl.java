package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirQualityBean.AirQualityResult;
import com.example.melificent.myqianqi.Model.IGetAirQualityModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetAirQualityModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetAirQualityPresenter;
import com.example.melificent.myqianqi.View.GetAirQuality;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirQualityPresenterImpl implements IGetAirQualityPresenter {
    private  GetAirQuality view;
    private IGetAirQualityModel  model;

    public IGetAirQualityPresenterImpl(GetAirQuality view) {
        this.view = view;
        this.model = new IGetAirQualityModelImpl();
    }

    @Override
    public void getAirQualityPresenter() {
        model.getAirQuality(new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                AirQualityResult result  = (AirQualityResult) object;
                view.getAirQualitySuccess(result);
            }

            @Override
            public void OnError(String Msg) {
                view.getAirQualityFail(Msg);
            }
        });
    }
}
