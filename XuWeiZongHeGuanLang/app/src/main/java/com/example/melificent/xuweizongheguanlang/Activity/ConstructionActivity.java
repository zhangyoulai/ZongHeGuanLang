package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.LineChartTools3;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/17.
 * for monitor construction,show the real time value and add value ,and get the warnning Msg
 */

public class ConstructionActivity extends AppCompatActivity {
    @InjectView(R.id.construction_real_time_value_down)
    TextView real_time_down;
    @InjectView(R.id.construction_add_down)
    TextView add_down;
    @InjectView(R.id.construction_warnning_Msg_down)
    TextView warnning_Msg_down;
    @InjectView(R.id.construction_trun_round_real_time)
    TextView trun_real_time;
    @InjectView(R.id.construction_trun_round_add)
    TextView trun_round_add;
    @InjectView(R.id.construction_trun_round_warnning_Msg)
    TextView trun_round_warnning_Msg;
    @InjectView(R.id.construction_open_real_time)
    TextView open_real_time;
    @InjectView(R.id.construction_open_add)
    TextView open_add;
    @InjectView(R.id.construction_open_warnning_Msg)
    TextView open_warnning_Msg;
    @InjectView(R.id.construction_oneday)
    CheckBox oneday;
    @InjectView(R.id.construction_oneweek)
    CheckBox oneweek;
    @InjectView(R.id.construction_linechart)
    LineChart lineChart;

    float[] real_times_down = new float[]{
        0.1f,1.15f,0.16f,0.18f
    };
    float[] real_times_trun_round = new float[]{
        0.01f,0.015f,0.016f,0.017f
    };
    float[] real_times_open = new float[]{
        0.4f,0.41f,0.43f,0.45f
    };
    float[] adds_down = new float[]{
            0.1f,0.12f,0.13f,0.15f
    };
    float[] adds_trun_round = new float[]{
            0.2f,0.18f,0.16f,0.21f
    };
    float[] adds_open = new float[]{
            0.6f,0.72f,0.75f,0.82f
    };
    String[] warnning_Msgs_down = new String[]{
            "范围正常","沉降超标","接近警戒值","远低于警戒"
    };
    String[] warnning_Msgs_trun_round = new String[]{
            "无扭转","轻微扭转","中度扭转","严重偏离"
    };
    String[] warnning_Msgs_open = new String[]{
            "开口度正常","轻微张开","中都打开","严重张开"
    };
    String yname_down = "管廊沉降";
    String yname_trun_round = "管廊扭转";
    String yname_open = "管廊开口";
    String [] name1 = new String[]{
            "8:00","9:00","10:00","11:00","12:00","13:00","14:00"
    };
    String [] name2 = new String[]{
            "周一","周二","周三","周四","周五","周六","周日"
    };
    float [] values1 = new float[]{
            0.09f,0.1f,0.12f,0.14f,0.16f,0.18f,0.15f
    };
    float []values2 = new float[]{
            0.009f,0.01f,0.011f,0.013f,0.015f,0.016f,0.015f
    };
    float [] values3 = new float[]{
            0.4f,0.41f,0.42f,0.43f,0.46f,0.48f,0.50f
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.construction_monitor_activity);
        ButterKnife.inject(this);

        //for table set data
        setValueForConstructionMonitorTable();
        //set checkbox change listener
        setCheckBoxListener();

        //show chartline for three datas {down,trun round,open} on the same linechart
        showLineChart();
    }

    private void showLineChart() {
        oneday.setChecked(true);
    }


    private void setCheckBoxListener() {
        oneday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //select oneday data to show on
                    LineChartTools3.setLineChart(lineChart,yname_down,yname_trun_round,yname_open,name1,values1,values2,values3);
                }
            }
        });
        oneweek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //select oneweek data to show on
                    LineChartTools3.setLineChart(lineChart,yname_down,yname_trun_round,yname_open,name2,values1,values2,values3);

                }
            }
        });
    }

    private void setValueForConstructionMonitorTable() {
        int i = new Random().nextInt(4);
        real_time_down.setText(real_times_down[i]+"");
        add_down.setText(adds_down[i]+"");
        warnning_Msg_down.setText(warnning_Msgs_down[i]);

        trun_real_time.setText(real_times_trun_round[i]+"");
        trun_round_add.setText(adds_trun_round[i]+"");
        trun_round_warnning_Msg.setText(warnning_Msgs_trun_round[i]);

        open_real_time.setText(real_times_open[i]+"");
        open_add.setText(adds_open[i]+"");
        open_warnning_Msg.setText(warnning_Msgs_open[i]);
    }

}
