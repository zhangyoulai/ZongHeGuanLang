package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.Model.IGetAirPlaneInfoByStationModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetAirPlaneInfoByStationModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByStationPresenter;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByStation;

import java.util.List;

/**
 * Created by p on 2017/3/16.
 */

public class IGetAirPlaneInfoByStationPresenterImpl implements IGetAirPlaneInfoByStationPresenter {
    private GetAirPlaneInfoByStation view;
    private IGetAirPlaneInfoByStationModel model;

    public IGetAirPlaneInfoByStationPresenterImpl(GetAirPlaneInfoByStation view) {
        this.view = view;
        this.model = new IGetAirPlaneInfoByStationModelImpl();
    }

    @Override
    public void getAirPlaneInfoByStation(String start, String end) {
        model.getAirPlaneInfoByStation(start, end, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                List<AirPlaneInfoByStationResult> results = (List<AirPlaneInfoByStationResult>) object;
                view.getAirPlaneInfoByStationSuccess(results);
            }

            @Override
            public void OnError(String Msg) {
                view.getAirPlaneInfoByStationFail(Msg);
            }
        });
    }
}
