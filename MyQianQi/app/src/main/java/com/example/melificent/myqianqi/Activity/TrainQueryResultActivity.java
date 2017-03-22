package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.melificent.myqianqi.Adapter.Train_byStation_Adapter;
import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class TrainQueryResultActivity extends AppCompatActivity {
    @InjectView(R.id.train_start_station)
    TextView start_station;
    @InjectView(R.id.train_end_station)
    TextView end_station;
    @InjectView(R.id.train_total_size)
    TextView size;
    @InjectView(R.id.train_bystation_date)
    TextView date_bystation;
    @InjectView(R.id.train_result_bystation)
    RecyclerView recyclerView;
    List<TrainInfoByStationResultList> lists;
    String satrt;
    String end;
    Train_byStation_Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_query_result);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        setData();
        adapter = new Train_byStation_Adapter(lists);
        recyclerView.setAdapter(adapter);
        
    }

    private void setData() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);
        date= calendar.getTime();
        String finalTime = TimeFormatUtils.formatDate(date).substring(5,7)+"月"+TimeFormatUtils.formatDate(date).substring(8)+"日";
        date_bystation.setText(finalTime);

        size.setText("(共"+lists.size()+"趟列车)");
        start_station.setText(satrt);
        end_station.setText(end);
    }

    private void getData() {
        Intent intent = getIntent();
        lists = (List<TrainInfoByStationResultList>) intent.getSerializableExtra("trainResult");
        satrt = intent.getStringExtra("start");
        end = intent.getStringExtra("end");


    }


}
