package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexResult;
import com.example.melificent.myqianqi.Presenter.IGetStrockInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetStrockIndexResultPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetStrockIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/3/17.
 */

public class StrockIndexActivity extends AppCompatActivity implements GetStrockIndex {
    IGetStrockInfoPresenter presenter =new IGetStrockIndexResultPresenterImpl(this);
    int typeSHANGHAI = 0;
    int typeSHENZHEN =1;
    List<StrockIndexResult> results = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strockindex_activity);

        presenter.getStrockInfo(typeSHENZHEN);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.currentThread().sleep(1000);
                    presenter.getStrockInfo(typeSHANGHAI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void getStrockIndexSuccess(StrockIndexResult result) {
            StrockIndexResult result1 = result;
        results.add(result1);
        Log.i("strockindexsize", "getStrockIndexlistsize: "+results.size());
        Log.i("nowPrice", "getStrockIndexNowPrice: "+result1.nowpri);
    }

    @Override
    public void getStrockIndexFial(String Msg) {
        Log.i("getstrockindexfail", "getStrockIndexFial: "+Msg);
    }
}
