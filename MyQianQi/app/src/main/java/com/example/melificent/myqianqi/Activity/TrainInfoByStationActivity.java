package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetTrainInfoByStation;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/15.
 */

public class TrainInfoByStationActivity extends AppCompatActivity implements GetTrainInfoByStation {
   @InjectView(R.id.train_station_start)
    EditText start;
    @InjectView(R.id.train_station_end)
    EditText end;
    @InjectView(R.id.query_by_station)
    Button query;
    IGetTrainInfoByStationPresenter presenter = new IGetTrainInfoByStationPresenterImpl(this);
    List<TrainInfoByStationResultList> lists;
    String error_Msg ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_info_by_station);
        ButterKnife.inject(this);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getTrainInfoByStation(start.getText().toString().trim(),end.getText().toString().trim(),"");
            }
        });
    }

    @Override
    public void getTrainInfoByStationSuccess(List<TrainInfoByStationResultList> lists) {
        this.lists = lists;
        Log.i("endStation", "endStation: "+lists.get(0).end_station);
    }

    @Override
    public void getTrainInfoByStationFail(String Msg) {
        Log.i("trainInfoByStationFail", "getTrainInfoByStationFail: "+Msg);
    }
}
