package com.example.melificent.myqianqi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/21.
 */

public class Plane_byStation_Adapter extends RecyclerView.Adapter<Plane_byStation_Adapter.ViewHoler> {
    private List<AirPlaneInfoByStationResult> results;



    static  class  ViewHoler extends RecyclerView.ViewHolder{
        TextView startTime;
        TextView endTime;
        TextView startStation;
        TextView endStation;
        TextView AirName;
        TextView AirCompany;
        public ViewHoler(View itemView) {
            super(itemView);
            startStation = (TextView) itemView.findViewById(R.id.plane_item_station_startsation);
            endStation = (TextView) itemView.findViewById(R.id.plane_item_station_endstation);
            startTime = (TextView) itemView.findViewById(R.id.plane_item_station_starttime);
            endTime = (TextView) itemView.findViewById(R.id.plane_item_station_endtime);
            AirName = (TextView) itemView.findViewById(R.id.plane_item_station_Airname);
            AirCompany = (TextView) itemView.findViewById(R.id.plane_item_station_AirCompany);

        }
    }

    public Plane_byStation_Adapter(List<AirPlaneInfoByStationResult> results) {
        this.results = results;
    }
    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_query_station_adapter,parent,false);
        ViewHoler holder = new ViewHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        AirPlaneInfoByStationResult bean = results.get(position);
        holder.AirCompany .setText(bean.Airline);
        holder.AirName.setText(bean.FlightNum);
        if (!bean.Aexpected.equals("")){
            holder.endTime.setText(bean.Aexpected.substring(11));
        }else {
            holder.endTime.setText(bean.ArrTime);
        }
        if (!bean.Dexpected.equals("")){
            holder.startTime.setText(bean.Dexpected.substring(11));
        }else {
            holder.startTime.setText(bean.DepTime);
        }
        if (!bean.ArrTerminal.equals("")){
            holder.endStation.setText(bean.ArrTerminal+"航站楼");
        }else {
            holder.endStation.setText("暂无");
        }
        if (!bean.DepTerminal.equals("")){
            holder.startStation.setText(bean.DepTerminal+"航站楼");
        }else {
            holder.startStation.setText("暂无");
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
