package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByStation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class AirPlaneQueryActivity extends AppCompatActivity implements GetAirPlaneInfoByStation {
    @InjectView(R.id.btn_plane_name)
    Button btn_plane_name;
    @InjectView(R.id.btn_plane_station)
    Button btn_plane_station;
    @InjectView(R.id.plane_strat)
    EditText satrt;
    @InjectView(R.id.plane_end)
    EditText end;
    @InjectView(R.id.plane_query)
    Button plane_query;
    @InjectView(R.id.plane_nowDate)
    TextView nowDate;

    IGetAirPlaneInfoByStationPresenter presenter = new IGetAirPlaneInfoByStationPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airplanequery);
        ButterKnife.inject(this);
        setButtonListener();
        setTime();



    }

    private void setTime() {
        Date date = new Date();
        String NowDate = TimeFormatUtils.formatDate(date).substring(6,7)+"月"+TimeFormatUtils.formatDate(date).substring(8)+"日";
        nowDate.setText(NowDate);
    }

    private void setButtonListener() {
        btn_plane_name.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_plane_name.setBackgroundColor(Color.parseColor("#1eb6b7"));
            btn_plane_name.setTextColor(Color.parseColor("#1eb6b7"));
            btn_plane_station.setTextColor(Color.parseColor("#ffffff"));
            btn_plane_station.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    });
        btn_plane_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_plane_name.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_plane_name.setTextColor(Color.parseColor("#ffffff"));
                btn_plane_station.setTextColor(Color.parseColor("#1eb6b7"));
                btn_plane_station.setBackgroundColor(Color.parseColor("#1eb6b7"));
            }
        });
        plane_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.getAirPlaneInfoByStation(satrt.getText().toString().trim(),end.getText().toString().trim());
            }
        });
    }

    @Override
    public void getAirPlaneInfoByStationSuccess(List<AirPlaneInfoByStationResult> results) {
            if (results.size() !=0){
                Intent intent = new Intent(AirPlaneQueryActivity.this,QueryPlaneResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("airResult", (Serializable) results);
                intent.putExtras(b);
                startActivity(intent);
            }else {
                Toast.makeText(this, "查询出错,请核对后重试", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void getAirPlaneInfoByStationFail(String error_Msg) {
        Toast.makeText(this, "查询出错,请核对后重试", Toast.LENGTH_SHORT).show();
    }
}
