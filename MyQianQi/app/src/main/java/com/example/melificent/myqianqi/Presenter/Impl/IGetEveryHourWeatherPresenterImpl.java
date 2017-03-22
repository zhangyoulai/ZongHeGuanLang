package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.EveryHourWeather.EveryHourResult;
import com.example.melificent.myqianqi.Model.IGetEveryHourWeatherModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetEveryHourWeatherModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetEveryHourWeatherPresenter;
import com.example.melificent.myqianqi.View.GetEveryHourWeatherInfo;

/**
 * Created by p on 2017/3/21.
 */

public class IGetEveryHourWeatherPresenterImpl implements IGetEveryHourWeatherPresenter {


    private GetEveryHourWeatherInfo view;
    private IGetEveryHourWeatherModel model;
    public IGetEveryHourWeatherPresenterImpl(GetEveryHourWeatherInfo view) {
        this.view = view;
        this.model = new IGetEveryHourWeatherModelImpl();
    }
    @Override
    public void getEveryHourWeatherPresenter(String startTime, String endTime) {
       model.getWeatherbyHour(startTime, endTime, new AsyncCallBack() {
           @Override
           public void OnSucess(Object object) {
               EveryHourResult result = (EveryHourResult) object;
               view.getWeatherInfoSuccess(result);
           }

           @Override
           public void OnError(String Msg) {
                view.getWeatherInfoFail(Msg);
           }
       });
    }
}
