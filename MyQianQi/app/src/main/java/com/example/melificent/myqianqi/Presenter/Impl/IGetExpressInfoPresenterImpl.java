package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.Express.ExpressBean;
import com.example.melificent.myqianqi.Bean.Express.ExpressResult;
import com.example.melificent.myqianqi.Model.IGetExpressInfoModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetExpressInfoModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetExpresssInfoPresenter;
import com.example.melificent.myqianqi.View.GetExpressInfo;

/**
 * Created by p on 2017/3/16.
 */

public class IGetExpressInfoPresenterImpl implements IGetExpresssInfoPresenter {
    private GetExpressInfo view;
    private IGetExpressInfoModel model;

    public IGetExpressInfoPresenterImpl(GetExpressInfo view) {
        this.view = view;
        this.model = new IGetExpressInfoModelImpl();
    }

    @Override
    public void getExpressInfoPresenter(String com, String no) {
        model.getExpressInfo(com, no, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                ExpressResult result = (ExpressResult) object;
                view.getExpressInfoSuccess(result);
            }

            @Override
            public void OnError(String Msg) {
                view.getExpressInfoFail(Msg);
            }
        });
    }
}
