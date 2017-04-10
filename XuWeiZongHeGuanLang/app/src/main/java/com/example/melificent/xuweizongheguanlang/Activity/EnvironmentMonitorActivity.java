package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.R;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/20.
 * monitor environment,for get tempreture,humidy,water_level,oxygen,ch4,sh2,seeper_level Msg
 * seeper_level 积水深度
 */

public class EnvironmentMonitorActivity extends AppCompatActivity {
    @InjectView(R.id.tempreture_real_time_value)
    TextView tempreture_real_time_value;
    @InjectView(R.id.tempreture_warnning_Msg)
    TextView tempreture_warnning_Msg;
    @InjectView(R.id.humidy_real_time)
    TextView humidy_real_time;
    @InjectView(R.id.humidy_warnning_Msg)
    TextView humidy_warnning_Msg;
    @InjectView(R.id.water_level_real_time)
    TextView water_level_real_time;
    @InjectView(R.id.water_level_warnning_Msg)
    TextView water_level_warnning_Msg;
    @InjectView(R.id.oxygen_real_time)
    TextView oxygen_real_time;
    @InjectView(R.id.oxygen_warnning_Msg)
    TextView oxygen_warnning_Msg;
    @InjectView(R.id.ch4_real_time)
    TextView ch4_real_time;
    @InjectView(R.id.ch4_Msg)
    TextView ch4_Msg;
    @InjectView(R.id.s2h_real_time)
    TextView s2h_real_time;
    @InjectView(R.id.s2h_Msg)
    TextView s2h_Msg;
    @InjectView(R.id.seeper_real_time)
    TextView seeper_real_time;
    @InjectView(R.id.seeper_Msg)
    TextView seeper_Msg;
    String[] tempretures = new String[]{
            "18C","19C","20C","21C"
    };
    String[] tempreture_Msgs = new String[]{
            "正常","温度偏低","温度略高","温度偏高"
    };
    String[] humidies = new String[]{
            "8","9","11","12"
    };
    String[] humidy_Msgs = new String[]{
            "正常","潮湿","干燥","重度干燥"
    };
    int [] water_level = new int[]{
            1,2,3,4
    };
    String [] water_level_Msgs = new String[]{
            "正常","低于正常值","稍高","高于正常值"
    };
    int [] oxygens = new int[]{
            12,13,14,15
    };
    String[] oxygen_Msgs = new String[]{
            "正常","含量偏低","稍高","高于正常值"
    };
    double [] ch4 = new double[]{
            1.1,1.2,1.3,1.5
    };
    String [] ch4_Msgs = new String[]{
            "正常","略高","稍偏高","达到可燃值"
    };
    String[] s2h = new String[]{
            "0.4","0.6","0.7","0.8"
    };
    String [] s2h_Msgs = new String[]{
            "正常","略高","稍偏高","致毒"
    };
    int [] seeper = new int[]{
            1,2,3,4
    };
    String [] seeper_Msgs = new String[]{
            "正常","略高","接近警戒线","管廊内涝"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.environment_monitor_activity);
        ButterKnife.inject(this);
        setTableData();
    }

    private void setTableData() {
        int  i = new Random().nextInt(4);
        tempreture_real_time_value.setText(tempretures[i]);
        switch (tempretures[i]){
            case "18C":
                tempreture_warnning_Msg.setText(tempreture_Msgs[0]);
                break;
            case "19C":
                tempreture_warnning_Msg.setText(tempreture_Msgs[1]);
                break;
            case "20C":
                tempreture_warnning_Msg.setText(tempreture_Msgs[2]);
                break;
            case "21C":
                tempreture_warnning_Msg.setText(tempreture_Msgs[3]);
                break;
        }
        int  j= new Random().nextInt(4);

        humidy_real_time.setText(humidies[j]);
        switch (humidies[j]){
            case "8":
                humidy_warnning_Msg.setText(humidy_Msgs[3]);
                break;
            case "9":
                humidy_warnning_Msg.setText(humidy_Msgs[2]);
                break;
            case "11":
                humidy_warnning_Msg.setText(humidy_Msgs[1]);
                break;
            case "12":
                humidy_warnning_Msg.setText(humidy_Msgs[0]);
                break;
        }
        int  k = new Random().nextInt(4);

        water_level_real_time.setText(water_level[k]+"");
        switch (water_level[k]){
            case 1:
                water_level_warnning_Msg.setText(water_level_Msgs[0]);
                break;
            case 2:
                water_level_warnning_Msg.setText(water_level_Msgs[1]);
                break;
            case 3:
                water_level_warnning_Msg.setText(water_level_Msgs[2]);
                break;
            case 4:
                water_level_warnning_Msg.setText(water_level_Msgs[3]);
                break;
        }
        int  l = new Random().nextInt(4);

        oxygen_real_time.setText(oxygens[l]+"");
        switch (oxygens[l]){
            case 12:
                oxygen_warnning_Msg.setText(oxygen_Msgs[0]);
                break;
            case 13:
                oxygen_warnning_Msg.setText(oxygen_Msgs[1]);
                break;
            case 14:
                oxygen_warnning_Msg.setText(oxygen_Msgs[2]);
                break;
            case 15:
                oxygen_warnning_Msg.setText(oxygen_Msgs[3]);
                break;
        }
        int  m = new Random().nextInt(4);

        ch4_real_time.setText(ch4[m]+"");
        switch (ch4[m]+""){
            case "1.1":
                ch4_Msg.setText(ch4_Msgs[0]);
                break;
            case "1.2":
                ch4_Msg.setText(ch4_Msgs[1]);
                break;
            case "1.3":
                ch4_Msg.setText(ch4_Msgs[2]);
                break;
            case "1.5":
                ch4_Msg.setText(ch4_Msgs[3]);
                break;
        }
        int  n = new Random().nextInt(4);

        s2h_real_time.setText(s2h[n]);
        switch (s2h[n]){
            case "0.4":
                s2h_Msg.setText(s2h_Msgs[0]);
                break;
            case "0.6":
                s2h_Msg.setText(s2h_Msgs[1]);
                break;
            case "0.7":
                s2h_Msg.setText(s2h_Msgs[2]);
                break;
            case "0.8":
                s2h_Msg.setText(s2h_Msgs[3]);
                break;
        }
        int  o = new Random().nextInt(4);

        seeper_real_time.setText(seeper[o]+"");
        switch (seeper[o]){
            case 1:
                seeper_Msg.setText(seeper_Msgs[0]);
                break;
            case 2:
                seeper_Msg.setText(seeper_Msgs[1]);
                break;
            case 3:
                seeper_Msg.setText(seeper_Msgs[2]);
                break;
            case 4:
                seeper_Msg.setText(seeper_Msgs[3]);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
