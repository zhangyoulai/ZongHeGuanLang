package com.example.melificent.myqianqi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoList;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/23.
 */

public class Plane_byName_Adapter extends RecyclerView.Adapter<Plane_byName_Adapter.ViewHolderAirplane> {
    List<AirPlaneInfoList> list ;
    String start_weather;
    String end_weather;
    String pass_weather;

    public Plane_byName_Adapter(List<AirPlaneInfoList> list, String start_weather, String end_weather,String pass_weather) {
        this.list = list;
        this.start_weather = start_weather;
        this.end_weather = end_weather;
        this.pass_weather=pass_weather;
    }

    static class ViewHolderAirplane extends RecyclerView.ViewHolder{
        TextView airplane_item_start,airplane_item_startTime,airplane_item_start_description,airplane_start_weather,airplane_end_weather,
                airplane_item_endstation,airplane_item_endTime,airplane_item_end_description,
                airplane_item_djk,airplane_item_zjgt,airplane_item_xlzk;
        public ViewHolderAirplane(View itemView) {
            super(itemView);
            airplane_item_start = (TextView) itemView.findViewById(R.id.airplane_item_start);
            airplane_item_startTime = (TextView) itemView.findViewById(R.id.airplane_item_startTime);
            airplane_item_start_description = (TextView) itemView.findViewById(R.id.airplane_item_start_description);
            airplane_start_weather = (TextView) itemView.findViewById(R.id.airplane_start_weather);
            airplane_end_weather = (TextView) itemView.findViewById(R.id.airplane_end_weather);
            airplane_item_endstation = (TextView) itemView.findViewById(R.id.airplane_item_endstation);
            airplane_item_endTime = (TextView) itemView.findViewById(R.id.airplane_item_endTime);
            airplane_item_end_description = (TextView) itemView.findViewById(R.id.airplane_item_end_description);
            airplane_item_djk = (TextView) itemView.findViewById(R.id.airplane_item_djk);
            airplane_item_zjgt = (TextView) itemView.findViewById(R.id.airplane_item_zjgt);
            airplane_item_xlzk = (TextView) itemView.findViewById(R.id.airplane_item_xlzk);

        }
    }
    @Override
    public ViewHolderAirplane onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_query_name_adapter,parent,false);
        ViewHolderAirplane holder= new ViewHolderAirplane(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderAirplane holder, int position) {
        if (list.size() ==1){
            holder.airplane_item_end_description.setText("终");
            holder.airplane_item_start_description.setText("始");
            holder.airplane_start_weather.setText(start_weather);
            holder.airplane_end_weather.setText(end_weather);
        }else{
            if (position == 0){
                holder.airplane_item_end_description.setText("经");
                holder.airplane_item_start_description.setText("始");
                holder.airplane_start_weather.setText(start_weather);
                holder.airplane_end_weather.setText(pass_weather);
            }else {
                holder.airplane_item_end_description.setText("终");
                holder.airplane_item_start_description.setText("经");
                holder.airplane_start_weather.setText(pass_weather);
                holder.airplane_end_weather.setText(end_weather);
            }
        }
        AirPlaneInfoList bean= list.get(position);
        holder.airplane_item_start.setText(bean.qf);
        holder.airplane_item_endstation.setText(bean.dd);
        if (bean.sjddtime.equals("")){
            holder.airplane_item_endTime.setText(bean.jhddtime);
        }else {
            holder.airplane_item_endTime.setText(bean.sjddtime);

        }
       if (bean.sjqftime.equals("")){
           holder.airplane_item_startTime.setText(bean.jhqftime);
       }else {
           holder.airplane_item_startTime.setText(bean.sjqftime);

       }
       holder.airplane_item_djk.setText("登机口："+bean.djk);
        holder.airplane_item_xlzk.setText("行李闸口："+bean.xlzp);
        holder.airplane_item_zjgt.setText("值机柜台："+bean.zjgt);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
