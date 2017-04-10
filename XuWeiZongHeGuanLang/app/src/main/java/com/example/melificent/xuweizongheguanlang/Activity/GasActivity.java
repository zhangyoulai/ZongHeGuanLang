package com.example.melificent.xuweizongheguanlang.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.LineChartTools1;
import com.example.melificent.xuweizongheguanlang.Utils.PieChartTools;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.melificent.xuweizongheguanlang.Utils.LineChartTools.setLineChart;

/**
 * Created by p on 2017/1/16.
 * Use PieChart LineChart to show the Gas Infomation ,also contains search (have search history and
 * associate image search )
 */

public class GasActivity extends AppCompatActivity {
    @InjectView(R.id.gas_piechart)
    PieChart pieChart;
    @InjectView(R.id.gas_linechart)
    LineChart lineChart;
    @InjectView(R.id.gas_title)
    TextView title;
    @InjectView(R.id.gas_total)
    TextView total;
    @InjectView(R.id.gas_ch4)
    LinearLayout ch4;
    @InjectView(R.id.gas_s2h)
    LinearLayout s2h;
    @InjectView(R.id.gas_o2)
    LinearLayout o2;
    @InjectView(R.id.gas_back)
    Button back;
    private String centerDescription = "";
    private String yname = "甲烷气体值";
    private String yname1 = "硫化氢气体值";
    private String yname2 = "氧气气体值";


    private String legendDescription = "各部分气体值";
    private String[]names = {
            "8:00","9:00","10:00","11:00","12:00","13:00","14:00"
    };
    private float[] values = {
            0.8f,0.9f,1.0f,1.2f,1.3f,1.2f,1.1f
    };
    private int count  =4;
    private String[] pieChartDescription = {
            "甲烷","硫化氢","氧气","其它"
    };
    private Float[] fValues ={
            18f,18f,32f,40f
    };
    private Integer[] colors = {
            Color.rgb(205,205,205),Color.rgb(114,188,223),Color.rgb(255,123,124),Color.rgb(57,135,200)
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        ButterKnife.inject(this);
        setPieChart();
        setButtonListener();
    }

    private void setButtonListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPieChart();
                title.setText("总概览");
                lineChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
            }
        });
      ch4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             setLineChart(lineChart,yname,names,values);
              lineChart.setVisibility(View.VISIBLE);
              pieChart.setVisibility(View.GONE);
              title.setText("甲烷气体详情");
          }
      });
        s2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineChartTools1.setLineChart(lineChart,yname1,names,values);
                lineChart.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                title.setText("硫化氢气体详情");
            }
        });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLineChart(lineChart,yname2,names,values);
                lineChart.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                title.setText("氧气气体详情");
            }
        });
    }

    private void setPieChart() {
        PieChartTools.creatPieChart(pieChart,centerDescription,legendDescription,count,pieChartDescription,fValues,colors);
    }

}
