package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.myqianqi.Adapter.VehicleViolationAdapter;
import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolationResult;
import com.example.melificent.myqianqi.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/23.
 */

public class VehicleViolationResultActivity extends AppCompatActivity {
@InjectView(R.id.vehicleviolationcount)
    TextView count;
    @InjectView(R.id.vehicleviolationfine)
    TextView fine;
    @InjectView(R.id.vehicleviolationdocutpoint)
    TextView point;
    @InjectView(R.id.vehicleviolation_recycleview)
    RecyclerView recycleView;
    @InjectView(R.id.carNo)
    TextView carNo;
    @InjectView(R.id.vehicleviolationresultgoback)
    ImageView back;

    VehicleViolationAdapter adapter;
    List<VehicleViolationResult> results;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicleviolationqueryresult);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        getData();
        setTextForTextview();
        setAdapterForRecycleView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAdapterForRecycleView() {
        adapter = new VehicleViolationAdapter(results);
        recycleView.setAdapter(adapter);
    }

    private void setTextForTextview() {
        carNo.setText(results.get(0).carNo);
        count.setText(results.size()+"");
        int fineCount  = 0;
        for (VehicleViolationResult result:results
             ) {
            fineCount += result.fine;
        }
        fine.setText(fineCount+"");

        int pointCount = 0;
        for (VehicleViolationResult result:results
             ) {
            pointCount += result.deductPoint;
        }
        point.setText(pointCount+"");
    }

    private void getData() {
        Intent intent = getIntent();
        results = (List<VehicleViolationResult>) intent.getSerializableExtra("vehicleviolation");
    }


}
