package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.melificent.myqianqi.Adapter.Plane_byStation_Adapter;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class QueryPlaneResultActivity extends AppCompatActivity {
    @InjectView(R.id.plane_end_station)
    TextView end;
    @InjectView(R.id.plane_start_station)
    TextView start;
    @InjectView(R.id.plane_result_bystation)
    RecyclerView recyclerView;
    @InjectView(R.id.plane_bystation_date)
    TextView date;
    List<AirPlaneInfoByStationResult> results;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_plane_result);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        results = (List<AirPlaneInfoByStationResult>) intent.getSerializableExtra("airResult");
        date.setText(results.get(0).FlightDate.substring(5,7)+"月"+results.get(0).FlightDate.substring(8)+"日");
        end.setText(results.get(0).ArrCity);
        start.setText(results.get(0).DepCity);
        Plane_byStation_Adapter adapter = new Plane_byStation_Adapter(results);
        recyclerView.setAdapter(adapter);


    }
}
