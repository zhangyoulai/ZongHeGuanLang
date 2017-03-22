package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolationResult;
import com.example.melificent.myqianqi.Model.IGetVehicleViolationInfoModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetVehicleViolationInfoModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetVehicleViolationInfoPresenter;
import com.example.melificent.myqianqi.Utils.GlobalContants;
import com.example.melificent.myqianqi.View.GetVehicleViolationInfo;

import java.util.List;

/**
 * Created by p on 2017/3/15.
 */

public class IGetVehicleViolationInfoPresenterImpl implements IGetVehicleViolationInfoPresenter {
    private GetVehicleViolationInfo view;
    private IGetVehicleViolationInfoModel model;

    public IGetVehicleViolationInfoPresenterImpl(GetVehicleViolationInfo view) {
        this.view = view;
        this.model = new IGetVehicleViolationInfoModelImpl();
    }

    @Override
    public void IGetVehicleViolationInfoPresenter(String frameNo, String engineNo, String carNo) {
        model.getVehicleViolationInfo( frameNo,engineNo,carNo,new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                    if (GlobalContants.NoVehicleViolationBehavior){
                        String reason = (String) object;
                        view.NoVehicleViolationBehavior(reason);
                    }else {
                        List<VehicleViolationResult> results = (List<VehicleViolationResult>) object;
                        view.getVehicleViolationInfoSucess(results);
                    }
            }

            @Override
            public void OnError(String Msg) {
                        view.getVehicleViolationInfoError(Msg);
            }
        });
    }


}
