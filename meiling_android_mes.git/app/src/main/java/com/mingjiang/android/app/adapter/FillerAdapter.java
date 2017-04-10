package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.PerfusionMachine;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by CaoBin on 2016/9/22.
 */
public class FillerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PerfusionMachine> perfusionMachines;

    public FillerAdapter(Context context,List<PerfusionMachine> perfusionMachines) {
        inflater=LayoutInflater.from(context);
        this.perfusionMachines=perfusionMachines;
    }

    public List<PerfusionMachine> getPerfusionMachines() {
        return perfusionMachines;
    }

    public void setPerfusionMachines(List<PerfusionMachine> perfusionMachines) {
        this.perfusionMachines = perfusionMachines;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return perfusionMachines.size();
    }

    @Override
    public Object getItem(int position) {
        return perfusionMachines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.fillter_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.list_line1_bg);
        } else {
            convertView.setBackgroundResource(R.color.list_line2_bg);
        }
        holder.tvBarCode.setText(perfusionMachines.get(position).getBarCode());
        holder.tvReportTime.setText(perfusionMachines.get(position).getReportDateTime());
        if (perfusionMachines.get(position).getE_CARICA().equals("合格")){
            holder.tvReportResult.setText("合格");
            holder.tvReportResult.setBackgroundResource(R.color.green);
        }else {
            holder.tvReportResult.setText("不合格");
            holder.tvReportResult.setBackgroundResource(R.color.red);
        }
        return convertView;
    }

    static

    class ViewHolder {
        @InjectView(R.id.tv_bar_code)
        TextView tvBarCode;
        @InjectView(R.id.tv_report_time)
        TextView tvReportTime;
        @InjectView(R.id.tv_gun_number)
        TextView tvGunNumber;
        @InjectView(R.id.tv_program_number)
        TextView tvProgramNumber;
        @InjectView(R.id.tv_vacuum_degree)
        TextView tvVacuumDegree;
        @InjectView(R.id.tv_actual_vacuum_degree)
        TextView tvActualVacuumDegree;
        @InjectView(R.id.tv_set_grams)
        TextView tvSetGrams;
        @InjectView(R.id.tv_actual_grams)
        TextView tvActualGrams;
        @InjectView(R.id.tv_report_result)
        TextView tvReportResult;
        @InjectView(R.id.tv_refrigerant_code)
        TextView tvRefrigerantCode;
        @InjectView(R.id.tv_refrigerant_type)
        TextView tvRefrigerantType;
        @InjectView(R.id.tv_refrigerant_temperature)
        TextView tvRefrigerantTemperature;
        @InjectView(R.id.tv_refrigerant_pressure)
        TextView tvRefrigerantPressure;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
