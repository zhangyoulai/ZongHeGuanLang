package com.example.melificent.myqianqi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.Train.Station_List;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/22.
 */

public class Train_byName_Adapter extends RecyclerView.Adapter<Train_byName_Adapter.ViewHolderTrainName> {
   List<Station_List> lists;

    public Train_byName_Adapter(List<Station_List> lists) {
        this.lists = lists;
    }

    static class ViewHolderTrainName extends RecyclerView.ViewHolder{
            TextView train_item_No,train_item_No_stationName,train_item_No_stationArriTime,
                    train_item_No_stationGoneTime,train_item_No_stationStayTime;
        public ViewHolderTrainName(View itemView) {
            super(itemView);
            train_item_No = (TextView) itemView.findViewById(R.id.train_item_No);
            train_item_No_stationName = (TextView) itemView.findViewById(R.id.train_item_No_stationName);
            train_item_No_stationArriTime = (TextView) itemView.findViewById(R.id.train_item_No_stationArriTime);
            train_item_No_stationGoneTime = (TextView) itemView.findViewById(R.id.train_item_No_stationGoneTime);
            train_item_No_stationStayTime = (TextView) itemView.findViewById(R.id.train_item_No_stationStayTime);

        }

    }
    @Override
    public ViewHolderTrainName onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_query_name_adapter,parent,false);
        ViewHolderTrainName holder = new ViewHolderTrainName(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTrainName holder, int position) {
        Station_List bean = lists.get(position);
        holder.train_item_No.setText(bean.train_id);
        holder.train_item_No_stationArriTime.setText(bean.arrived_time);
        holder.train_item_No_stationGoneTime.setText(bean.leave_time);
        holder.train_item_No_stationName.setText(bean.station_name);
        holder.train_item_No_stationStayTime.setText(bean.stay);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
