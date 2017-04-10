package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.LineChartTools2;
import com.github.mikephil.charting.charts.LineChart;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/14.
 */

public class TempretureActivity extends AppCompatActivity {
    @InjectView(R.id.tempreture_linechart)
    LineChart lineChart;
    String yname = "温度值";
    String ywarnning = "温度警戒值";
    String [] names = new String[]{
            "8:00","9:00","10:00","11:00","12:00","13:00","14:00"
    };
    float [] values = new float[]{
            18f,18f,19f,20f,21f,24f,25f
    };
    float[] warnning = new float[]{
            23f,23f,23f,23f,23f,23f,23f
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempreture_activity);
        ButterKnife.inject(this);
        LineChartTools2.setLineChart(lineChart,yname,ywarnning,names,values,warnning);
    }
}
