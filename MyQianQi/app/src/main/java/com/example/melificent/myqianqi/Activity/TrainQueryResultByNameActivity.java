package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.melificent.myqianqi.Adapter.Train_byName_Adapter;
import com.example.melificent.myqianqi.Bean.Train.Station_List;
import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;
import com.example.melificent.myqianqi.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class TrainQueryResultByNameActivity extends AppCompatActivity {
    @InjectView(R.id.plane_start_station_byname)
    TextView start_name;
    @InjectView(R.id.plane_end_station_byname)
    TextView end_name;
    @InjectView(R.id.train_item_start_byname)
    TextView start_station;
    @InjectView(R.id.train_item_startTime_byname)
    TextView start_time;
    @InjectView(R.id.train_item_endstation_byname)
    TextView end_station;
    @InjectView(R.id.train_item_endTime_byname)
    TextView end_time;
    @InjectView(R.id.train_item_name)
    TextView name;
    @InjectView(R.id.plane_result_byname)
    RecyclerView recyclerView;
    TrainInfoResult result;
    Train_byName_Adapter adapter;
    List<Station_List> lists;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_query_byname_result);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        setTextForTextView();
        setRecycleViewAdapter();
    }

    private void setRecycleViewAdapter() {
        adapter = new Train_byName_Adapter(lists);
        recyclerView.setAdapter(adapter);
    }

    private void setTextForTextView() {
        start_name.setText(result.train_info.start);
        end_name.setText(result.train_info.end);
        start_station.setText(result.train_info.start);
        end_station.setText(result.train_info.end);
        start_time.setText(result.train_info.starttime);
        end_time.setText(result.train_info.endtime);
        name.setText(result.train_info.name);
    }

    private void getData() {
        Intent intent = getIntent();
        result = (TrainInfoResult) intent.getSerializableExtra("TrainInfoResult");
        lists = result.station_list;
    }
}
