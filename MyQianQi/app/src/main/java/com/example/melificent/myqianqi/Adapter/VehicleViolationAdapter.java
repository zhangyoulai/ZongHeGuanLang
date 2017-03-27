package com.example.melificent.myqianqi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolationResult;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/23.
 */

public class VehicleViolationAdapter extends RecyclerView.Adapter<VehicleViolationAdapter.ViewHolderVehicleViolation> {
   List<VehicleViolationResult> list;

    public VehicleViolationAdapter(List<VehicleViolationResult> list) {
        this.list = list;
    }

    static class ViewHolderVehicleViolation extends RecyclerView.ViewHolder{
        TextView behavior_item_time,behavior_item_address,behavior_item,behavior_item_docutpoint,
                behavior_item_fine,behavior_item_znj,behavior_item_fed;
        public ViewHolderVehicleViolation(View itemView) {
            super(itemView);
            behavior_item_time = (TextView) itemView.findViewById(R.id.behavior_item_time);
            behavior_item_address = (TextView) itemView.findViewById(R.id.behavior_item_address);
            behavior_item = (TextView) itemView.findViewById(R.id.behavior_item);
            behavior_item_docutpoint = (TextView) itemView.findViewById(R.id.behavior_item_docutpoint);
            behavior_item_fine = (TextView) itemView.findViewById(R.id.behavior_item_fine);
            behavior_item_znj = (TextView) itemView.findViewById(R.id.behavior_item_znj);
            behavior_item_fed = (TextView) itemView.findViewById(R.id.behavior_item_fed);

        }
    }

    @Override
    public ViewHolderVehicleViolation onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicleviolationadapter,parent,false);
        ViewHolderVehicleViolation holder= new ViewHolderVehicleViolation(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderVehicleViolation holder, int position) {
        VehicleViolationResult bean = list.get(position);
        holder.behavior_item.setText(bean.behavior);
        holder.behavior_item_address.setText(bean.address);
        holder.behavior_item_docutpoint.setText(bean.deductPoint+"");
        holder.behavior_item_fed.setText("￥"+bean.serviceFee);
        holder.behavior_item_fine.setText("￥"+bean.fine);
        holder.behavior_item_time.setText(bean.time);
        holder.behavior_item_znj.setText(bean.zhinajin+"");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
