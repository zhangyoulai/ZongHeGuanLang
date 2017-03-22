package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByNamePresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneByNamePresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByName;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/16.
 */

public class AirPlaneInfoByName extends AppCompatActivity implements GetAirPlaneInfoByName {
    @InjectView(R.id.airplane_byname_query)
    Button query;
    @InjectView(R.id.airplane_name)
    EditText name;
    @InjectView(R.id.airplanedate)
    EditText date;
    IGetAirPlaneInfoByNamePresenter presenter = new IGetAirPlaneByNamePresenterImpl(this);
    AirPlaneInfoResult result ;
    String error_Msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airplane_info_byname);
        ButterKnife.inject(this);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getAirPlaneInfoByNamePresenter(name.getText().toString().trim(),date.getText().toString().trim());
            }
        });
    }

    @Override
    public void getAirPlaneInfoByNameSuccess(AirPlaneInfoResult result) {
        this.result = result;
        Log.i("to_simple", "getAirPlaneInfoByNameTo_Simple: "+result.info.to_simple);
        Log.i("dd_simple", "getAirPlaneInfoByNameDd_Simple: "+result.list.get(0).dd_simple);
    }

    @Override
    public void getAirPlaneInfoByNameFail(String error_Msg) {
        this.error_Msg = error_Msg;
        Log.i("error_Msg", "getAirPlaneInfoByNameFail: "+error_Msg);
    }
}
