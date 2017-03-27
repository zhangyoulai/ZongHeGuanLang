package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.Model.IGetTrainInfoByStationModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetTrainInfoByStationModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByStationPresenter;
import com.example.melificent.myqianqi.View.GetTrainInfoByStation;

import java.util.List;

/**
 * Created by p on 2017/3/15.
 */

public class IGetTrainInfoByStationPresenterImpl implements IGetTrainInfoByStationPresenter {
    private GetTrainInfoByStation view;
    private IGetTrainInfoByStationModel model;

    public IGetTrainInfoByStationPresenterImpl(GetTrainInfoByStation view) {
        this.view = view;
        this.model = new IGetTrainInfoByStationModelImpl();
    }

    @Override
    public void getTrainInfoByStation(String start, String end,String date) {
            model.getTrainInfo(start, end, date,new AsyncCallBack() {
                @Override
                public void OnSucess(Object object) {
                    List<TrainInfoByStationResultList> lists = (List<TrainInfoByStationResultList>) object;
                    view.getTrainInfoByStationSuccess(lists);
                }

                @Override
                public void OnError(String Msg) {
                    view.getTrainInfoByStationFail(Msg);
                }
            });
    }
}
