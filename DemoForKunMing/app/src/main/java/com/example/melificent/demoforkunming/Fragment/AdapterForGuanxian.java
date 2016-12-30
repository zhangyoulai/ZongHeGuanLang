package com.example.melificent.demoforkunming.Fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/10/31.
 */

public class AdapterForGuanxian extends BaseAdapter {
    Context mcontext;
    List<guanxianentity> guanxianentities ;

    public AdapterForGuanxian(Context mcontext, List<guanxianentity> guanxianentities) {
        this.mcontext = mcontext;
        this.guanxianentities = guanxianentities;
    }

    @Override
    public int getCount() {
        return guanxianentities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterForGuanxian.ViewHolder holder = null;
        if (convertView==null){
            convertView = View.inflate(mcontext,R.layout.adapter_guanxian,null);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1_guanxian);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2_guanxian);
            holder.tv3 = (TextView) convertView.findViewById(R.id.tv3_guanxian);
            holder.tv4 = (TextView) convertView.findViewById(R.id.tv4_guanxian);
            convertView.setTag(holder);
        }
        holder  = (ViewHolder) convertView.getTag();
        holder.tv1.setText("类别:"+guanxianentities.get(position).leibie);
        holder.tv2.setText("材质:"+guanxianentities.get(position).caizhi);
        holder.tv3.setText("管径:"+guanxianentities.get(position).guanjing);
        holder.tv4.setText("权属单位:"+guanxianentities.get(position).quanshudanwei);
        return convertView;
    }
    class  ViewHolder{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

    }
}
