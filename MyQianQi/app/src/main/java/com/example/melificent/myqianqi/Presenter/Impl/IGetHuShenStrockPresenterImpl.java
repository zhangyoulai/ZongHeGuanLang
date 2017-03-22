package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.ShanghaiAndShenzhenStrock.HuShenStrockResult;
import com.example.melificent.myqianqi.Model.IGetHuShenStrockModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetHuShenStrockModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetHuShenStrockPresenter;
import com.example.melificent.myqianqi.View.GetHuShenStrockResult;

import java.util.List;

/**
 * Created by p on 2017/3/17.
 */

public class IGetHuShenStrockPresenterImpl implements IGetHuShenStrockPresenter {
    private GetHuShenStrockResult view;
    private IGetHuShenStrockModel model;

    public IGetHuShenStrockPresenterImpl(GetHuShenStrockResult view) {
        this.view = view;
        this.model = new IGetHuShenStrockModelImpl();
    }

    @Override
    public void getHuShenStrockPresenter(String gid) {
        model.getHuShenStrock(gid, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
               List<HuShenStrockResult> results = (List<HuShenStrockResult>) object;
                view.getHuShenStrockResultSuccess(results);
            }

            @Override
            public void OnError(String Msg) {
                view.getHuShenStrockResultFail(Msg);
            }
        });
    }
}
