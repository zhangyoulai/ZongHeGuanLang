package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexResult;
import com.example.melificent.myqianqi.Model.IGetStrockIndexModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetStrockIndexModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetStrockInfoPresenter;
import com.example.melificent.myqianqi.View.GetStrockIndex;

/**
 * Created by p on 2017/3/17.
 */

public class IGetStrockIndexResultPresenterImpl implements IGetStrockInfoPresenter {
    private GetStrockIndex view;
    private IGetStrockIndexModel model;

    public IGetStrockIndexResultPresenterImpl(GetStrockIndex view) {
        this.view = view;
        this.model = new IGetStrockIndexModelImpl();
    }

    @Override
    public void getStrockInfo(int type) {
        model.getStrockIndex(type, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                StrockIndexResult result= (StrockIndexResult) object;
                view.getStrockIndexSuccess(result);
            }

            @Override
            public void OnError(String Msg) {
                view.getStrockIndexFial(Msg);
            }
        });
    }
}
