package com.example.melificent.myqianqi.Presenter.Impl;

import com.example.melificent.myqianqi.AsyncCallBack;
import com.example.melificent.myqianqi.Bean.RealNameRegex.RealNameRegexResult;
import com.example.melificent.myqianqi.Model.IGetRealNameRegexResultModel;
import com.example.melificent.myqianqi.Model.IMPL.IGetRealNameRegexResultModelImpl;
import com.example.melificent.myqianqi.Presenter.IGetRealNameRegexResultPresenter;
import com.example.melificent.myqianqi.View.GetRealNameRegexResult;

/**
 * Created by p on 2017/3/16.
 */

public class IGetRealNameRegexResultPresenterImpl implements IGetRealNameRegexResultPresenter {
    private GetRealNameRegexResult view;
    private IGetRealNameRegexResultModel model;

    public IGetRealNameRegexResultPresenterImpl(GetRealNameRegexResult view) {
        this.view = view;
        this.model = new IGetRealNameRegexResultModelImpl();
    }

    @Override
    public void getRealNameRegexResultPresenter(String realname, String idcard) {
        model.getRealNameRegexResult(realname, idcard, new AsyncCallBack() {
            @Override
            public void OnSucess(Object object) {
                RealNameRegexResult result = (RealNameRegexResult) object;
                view.getRealNameRegexSuccess(result);

            }

            @Override
            public void OnError(String Msg) {

            }
        });
    }
}
