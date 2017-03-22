package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;
import com.example.melificent.myqianqi.Model.IGetAirPlaneInfoByNameModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetAirPlaneInfoByNameModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByNamePresenter;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByName;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirPlaneByNamePresenterImpl implements IGetAirPlaneInfoByNamePresenter {
    private GetAirPlaneInfoByName view;
    private IGetAirPlaneInfoByNameModel model;

    public IGetAirPlaneByNamePresenterImpl(GetAirPlaneInfoByName view) {
        this.view = view;
        this.model = new IGetAirPlaneInfoByNameModelImpl();
    }

    @Override
    public void getAirPlaneInfoByNamePresenter(String name, String date) {
        model.getAirPlaneInfo(name, date, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                AirPlaneInfoResult result = (AirPlaneInfoResult) object;
                view.getAirPlaneInfoByNameSuccess(result);
            }

            @Override
            public void OnError(String Msg) {
                view.getAirPlaneInfoByNameFail(Msg);
            }
        });
    }
}
