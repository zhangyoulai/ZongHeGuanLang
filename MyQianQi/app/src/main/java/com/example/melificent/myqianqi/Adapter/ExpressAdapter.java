package com.example.melificent.myqianqi.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.myqianqi.Bean.Express.ExpressList;
import com.example.melificent.myqianqi.R;

import java.util.List;

/**
 * Created by p on 2017/3/22.
 */

public class ExpressAdapter extends RecyclerView.Adapter<ExpressAdapter.ViewHolderExpress> {
   List<ExpressList> lists;

    public ExpressAdapter(List<ExpressList> lists) {
        this.lists = lists;
    }

    static class ViewHolderExpress extends RecyclerView.ViewHolder{
        TextView express_nowTime,express_status_description;
        ImageView icon;
        public ViewHolderExpress(View itemView) {
            super(itemView);
            express_nowTime = (TextView) itemView.findViewById(R.id.express_nowTime);
            express_status_description = (TextView) itemView.findViewById(R.id.express_status_description);
            icon = (ImageView) itemView.findViewById(R.id.express_status_icon);


        }
    }
    @Override
    public ViewHolderExpress onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.express_result_adapter,parent,false);
        ViewHolderExpress holder = new ViewHolderExpress(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderExpress holder, int position) {

        if (position == 0){
            holder.icon.setImageResource(R.drawable.dangqianzhuangtai);
            holder.express_nowTime.setTextColor(Color.parseColor("#1eb6b7"));
            holder.express_status_description.setTextColor(Color.parseColor("#1eb6b7"));
            holder.express_nowTime.setText(lists.get(0).datetime);
            holder.express_status_description.setText(lists.get(0).remark);
        }else {
            ExpressList bean = lists.get(position);
            holder.express_nowTime.setText(bean.datetime);
            holder.express_status_description.setText(bean.remark);
        }


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
