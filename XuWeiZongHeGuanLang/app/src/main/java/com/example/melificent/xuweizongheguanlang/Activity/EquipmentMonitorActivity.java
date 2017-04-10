package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.R;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/20.
 * monitor equipment
 */

public class EquipmentMonitorActivity extends AppCompatActivity {
    @InjectView(R.id.wind_equipment_status)
    TextView wind_equipment_status;
    @InjectView(R.id.wind_equipment_voltage)
    TextView wind_equipment_voltage;
    @InjectView(R.id.wind_equipment_electric_current)
    TextView wind_equipment_electric_current;
    @InjectView(R.id.water_pump_status)
    TextView water_pump_status;
    @InjectView(R.id.water_pump_voltage)
    TextView water_pump_voltage;
    @InjectView(R.id.water_pump_electric_current)
    TextView water_pump_electric_current;
    @InjectView(R.id.flight_equipment_status)
    TextView flight_equipment_status;
    @InjectView(R.id.flight_equipment_warnning_Msg)
    TextView flight_equipment_warnning_Msg;
    @InjectView(R.id.in_and_out_electric_line_status)
    TextView in_and_out_electric_line_status;
    @InjectView(R.id.in_and_out_line_error_Msg)
    TextView in_and_out_line_error_Msg;
    @InjectView(R.id.voltage_changer_status)
    TextView voltage_changer_status;
    @InjectView(R.id.voltage_high_tempreture_Msg)
    TextView voltage_high_tempreture_Msg;

    String[] status = new String[]{
            "开","关"
    };
    String[] voltages  = new String[]{
            "110V","220V","380V"
    };
    String[] electric_current = new String[]{
           "0", "0.8A","1.0A","1.2A"
    };
    String[] flight_warnning_Msgs = new String[]{
            "正常","灯泡损坏","电机损坏"
    };
    String[] in_and_out_line_warnning_Msgs = new String[]{
            "正常","短路","断路"
    };
    String[] voltage_changer = new String[]{
            "正常","损坏"
    };
    String[] high_tempreture_warnning_Msgs = new String[]{
            "正常","线圈温度过高"
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_monitor_activity);
        ButterKnife.inject(this);
        setTableData();
    }

    private void setTableData() {
        int i = new Random().nextInt(2);
        wind_equipment_status.setText(status[i]);
        wind_equipment_voltage.setText(voltages[new Random().nextInt(3)]);
        switch (status[i]){
            case "开":
                wind_equipment_electric_current.setText(electric_current[new Random().nextInt(3)+1]);
                break;
            case "关":
                wind_equipment_electric_current.setText(electric_current[0]);
                break;
        }
        int j = new Random().nextInt(2);
        water_pump_status.setText(status[j]);
        water_pump_voltage.setText(voltages[new Random().nextInt(3)]);
        switch (status[j]){
            case "开":
                water_pump_electric_current.setText(electric_current[new Random().nextInt(3)+1]);
                break;
            case "关":
                water_pump_electric_current.setText(electric_current[0]);
                break;
        }
        int k = new Random().nextInt(2);
        flight_equipment_status.setText(status[k]);
        switch (status[k]){
            case "开":
                flight_equipment_warnning_Msg.setText(flight_warnning_Msgs[0]);
                break;
            case "关":
                flight_equipment_warnning_Msg.setText(flight_warnning_Msgs[new Random().nextInt(2)+1]);
                break;
        }
        int l = new Random().nextInt(2);
        in_and_out_electric_line_status.setText(status[l]);
        switch (status[l]){
            case "开":
                in_and_out_line_error_Msg.setText(in_and_out_line_warnning_Msgs[new Random().nextInt(2)+1]);
                break;
            case "关":
                in_and_out_line_error_Msg.setText(in_and_out_line_warnning_Msgs[0]);
                break;
        }
        int m = new Random().nextInt(2);
        voltage_changer_status.setText(voltage_changer[m]);
        switch (voltage_changer[m]){
            case "正常":
                voltage_high_tempreture_Msg.setText(high_tempreture_warnning_Msgs[0]);
                break;
            case "损坏":
                voltage_high_tempreture_Msg.setText(high_tempreture_warnning_Msgs[1]);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
