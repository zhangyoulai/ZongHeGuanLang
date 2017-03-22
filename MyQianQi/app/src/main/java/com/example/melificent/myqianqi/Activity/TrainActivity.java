package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByTrainNamePresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByTrainNamePresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetTrainInfoByTrainName;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/15.
 */

public class TrainActivity extends AppCompatActivity implements GetTrainInfoByTrainName {
    @InjectView(R.id.train_input)
    EditText train_input;
    @InjectView(R.id.train_search)
    Button search;
    IGetTrainInfoByTrainNamePresenter presenter = new IGetTrainInfoByTrainNamePresenterImpl(this);
    TrainInfoResult result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);
        ButterKnife.inject(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.IGetTrainInfoByTrainNamePresenter(train_input.getText().toString().trim());
            }
        });
    }

    @Override
    public void getTrainInfoByTrainNameSuccess(TrainInfoResult result) {
        this.result = result;
        Log.i("name", "getTrainInfoByTrainName: "+result.station_list.get(0).station_name);
    }

    @Override
    public void getTrainInfoByTrainNameFaile(String error_Msg) {
        Log.i("TrainError_Msg", "getTrainInfoByTrainNameFail: "+error_Msg);

    }
}
