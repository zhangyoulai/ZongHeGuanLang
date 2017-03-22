package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByTrainNamePresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByTrainNamePresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetTrainInfoByStation;
import com.example.melificent.myqianqi.View.GetTrainInfoByTrainName;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class TrainQueryActivity extends AppCompatActivity implements GetTrainInfoByStation,GetTrainInfoByTrainName {
    @InjectView(R.id.btn_train_bystation)
    Button btn_train_bystation;
    @InjectView(R.id.btn_train_byname)
    Button btn_train_byname;
    @InjectView(R.id.train_strat)
    EditText start;
    @InjectView(R.id.train_end)
    EditText end;
    @InjectView(R.id.train_nextDate)
    TextView nextDate;
    @InjectView(R.id.train_query)
    Button query;
    @InjectView(R.id.train_name_field)
    LinearLayout name_field;
    @InjectView(R.id.train_station_field)
    LinearLayout station_field;
    @InjectView(R.id.train_name)
    EditText train_name;
    IGetTrainInfoByStationPresenter presenter = new IGetTrainInfoByStationPresenterImpl(this);
    IGetTrainInfoByTrainNamePresenter presenter_byname = new IGetTrainInfoByTrainNamePresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainquerybystationactivity);
        ButterKnife.inject(this);
        setButtonListener();
        setTime();
    }

    private void setTime() {
    }

    private void setButtonListener() {
        btn_train_byname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_train_byname.setBackgroundColor(Color.parseColor("#1eb6b7"));
                btn_train_byname.setTextColor(Color.parseColor("#1eb6b7"));
                btn_train_byname.setText("按名称");
                btn_train_bystation.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_train_bystation.setTextColor(Color.parseColor("#ffffff"));
                btn_train_bystation.setText("按发到站");
                name_field.setVisibility(View.VISIBLE);
                station_field.setVisibility(View.GONE);
                query.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (train_name.getText().toString().trim() != null){
                            presenter_byname.IGetTrainInfoByTrainNamePresenter(train_name.getText().toString().trim());
                        }else{
                            Toast.makeText(TrainQueryActivity.this, "车次不能为空!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_train_bystation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_train_byname.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_train_byname.setTextColor(Color.parseColor("#ffffff"));
                btn_train_byname.setText("按名称");
                btn_train_bystation.setBackgroundColor(Color.parseColor("#1eb6b7"));
                btn_train_bystation.setTextColor(Color.parseColor("#1eb6b7"));
                btn_train_bystation.setText("按发到站");
                query.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(start.getText().toString().trim() != null && end.getText().toString().trim() != null){
                            presenter.getTrainInfoByStation(start.getText().toString().trim(),end.getText().toString().trim());
                        }else {
                            Toast.makeText(TrainQueryActivity.this, "始发地或目的地不能为空!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void getTrainInfoByStationSuccess(List<TrainInfoByStationResultList> lists) {
            if (lists.size() != 0){
                Intent intent = new Intent(TrainQueryActivity.this,TrainQueryResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("trainResult", (Serializable) lists);
                b.putString("start",start.getText().toString().trim());
                b.putString("end",end.getText().toString().trim());
                intent.putExtras(b);
                startActivity(intent);
            }else {
                Toast.makeText(this, "查询失败，请确认后重试!", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void getTrainInfoByStationFail(String Msg) {
        Toast.makeText(this, "查询失败，请确认后重试!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTrainInfoByTrainNameSuccess(TrainInfoResult results) {
        Intent intent = new Intent(TrainQueryActivity.this,TrainQueryResultByNameActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("TrainInfoResult",  results);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void getTrainInfoByTrainNameFaile(String error_Msg) {
        Toast.makeText(this, "查询列车信息失败！", Toast.LENGTH_SHORT).show();
    }
}
