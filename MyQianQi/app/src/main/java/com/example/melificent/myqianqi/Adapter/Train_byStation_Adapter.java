package com.example.melificent.myqianqi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/22.
 */

public class Train_byStation_Adapter extends RecyclerView.Adapter<Train_byStation_Adapter.ViewHolderTrain> {
    List<TrainInfoByStationResultList> lists;

    public Train_byStation_Adapter(List<TrainInfoByStationResultList> lists) {
        this.lists = lists;
    }



    static  class  ViewHolderTrain extends  RecyclerView.ViewHolder{
        TextView train_item_start,train_item_end,train_item_startTime,train_item_endTime,
                train_item_start_description,train_item_end_description,train_item_name,train_item_totalTime,
                train_item_price1,train_item_price2,train_item_price3;
        public ViewHolderTrain(View itemView) {
            super(itemView);
            train_item_end = (TextView) itemView.findViewById(R.id.train_item_endstation);
            train_item_start = (TextView) itemView.findViewById(R.id.train_item_start);
            train_item_startTime = (TextView) itemView.findViewById(R.id.train_item_startTime);
            train_item_endTime = (TextView) itemView.findViewById(R.id.train_item_endTime);
            train_item_start_description = (TextView) itemView.findViewById(R.id.train_item_start_description);
            train_item_end_description = (TextView) itemView.findViewById(R.id.train_item_end_description);
            train_item_name = (TextView) itemView.findViewById(R.id.train_item_name);
            train_item_totalTime = (TextView) itemView.findViewById(R.id.train_item_totalTime);
            train_item_price1 = (TextView) itemView.findViewById(R.id.train_item_price1);
            train_item_price2 = (TextView) itemView.findViewById(R.id.train_item_price2);
            train_item_price3 = (TextView) itemView.findViewById(R.id.train_item_price3);
        }
    }
    @Override
    public ViewHolderTrain onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_query_station_adapter,parent,false);
        ViewHolderTrain holder = new ViewHolderTrain(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTrain holder, int position) {
        TrainInfoByStationResultList bean = lists.get(position);
        holder.train_item_end.setText(bean.end_station);
        holder.train_item_start.setText(bean.start_station);
        holder.train_item_end_description.setText(bean.end_station_type);
        holder.train_item_start_description.setText(bean.start_station_type);
        holder.train_item_endTime.setText(bean.end_time);
        holder.train_item_startTime.setText(bean.start_time);
        holder.train_item_totalTime.setText(bean.run_time);
        holder.train_item_name.setText(bean.train_no);
        holder.train_item_price1.setText(bean.price_list.get(0).price_type+":"+bean.price_list.get(0).price);
        holder.train_item_price2.setText(bean.price_list.get(1).price_type+":"+bean.price_list.get(1).price);
        if (bean.price_list.size() == 3){
            holder.train_item_price3.setText(bean.price_list.get(2).price_type+":"+bean.price_list.get(2).price);
        }else {
            holder.train_item_price3.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
