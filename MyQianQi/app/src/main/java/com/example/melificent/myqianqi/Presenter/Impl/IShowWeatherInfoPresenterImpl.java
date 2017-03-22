package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.WeatherBean.Result;
import com.example.melificent.myqianqi.Model.IGetWeatherInfoModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetWeatherInfoModelImpl;
import com.example.melificent.myqianqi.Presenter.IShowWeatherInfoPresenter;
import com.example.melificent.myqianqi.View.ShowWeatherInfo;

/**
 * Created by p on 2017/3/14.
 */

public class IShowWeatherInfoPresenterImpl implements IShowWeatherInfoPresenter {
     private ShowWeatherInfo view;
     private IGetWeatherInfoModel model;

    public IShowWeatherInfoPresenterImpl(ShowWeatherInfo view) {
        this.view = view;
        this.model = new IGetWeatherInfoModelImpl();
    }

    @Override
    public void showWeatherInfoPresenter() {
        model.getWeatherInfo( new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                Result result = (Result) object;
                view.showWeatherInfo(result);
            }

            @Override
            public void OnError(String Msg) {
                view.showErrorInfo(Msg);
            }
        });
    }
}
