package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;
import com.example.melificent.myqianqi.Model.IGetTrainInfoByTrainNameModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetTrainInfoByTrainNameModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByTrainNamePresenter;
import com.example.melificent.myqianqi.View.GetTrainInfoByTrainName;

import java.util.List;

/**
 * Created by p on 2017/3/15.
 */

public class IGetTrainInfoByTrainNamePresenterImpl implements IGetTrainInfoByTrainNamePresenter {
    private GetTrainInfoByTrainName view;
    private IGetTrainInfoByTrainNameModel model;
    public IGetTrainInfoByTrainNamePresenterImpl(GetTrainInfoByTrainName view) {
        this.view = view;
        this.model = new IGetTrainInfoByTrainNameModelImpl();
    }

    @Override
    public void IGetTrainInfoByTrainNamePresenter(String name) {
        model.getTrainInfoByTrainName(name, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                TrainInfoResult results= (TrainInfoResult) object;
                view.getTrainInfoByTrainNameSuccess(results);
            }

            @Override
            public void OnError(String Msg) {
                view.getTrainInfoByTrainNameFaile(Msg);
            }
        });
    }
}
