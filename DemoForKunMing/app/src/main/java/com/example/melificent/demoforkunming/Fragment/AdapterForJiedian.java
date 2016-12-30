package com.example.melificent.demoforkunming.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/10/31.
 */

public class AdapterForJiedian extends BaseAdapter  {
    List<jiedianentity> jiedianentities;
    Context mcontext;




    public AdapterForJiedian(List<jiedianentity> jiedianentities, Context mcontext) {
        this.jiedianentities = jiedianentities;
        this.mcontext = mcontext;

    }

    @Override
    public int getCount() {
        return jiedianentities.size();
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
        ViewHolder holder= null;
        if (convertView==null){
            convertView = View.inflate(mcontext,R.layout.adapter_jiedian,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1_jiedian);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2_jiedian);
            holder.tv3 = (TextView) convertView.findViewById(R.id.tv3_jiedian);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv1.setText("节点名称:"+jiedianentities.get(position).jiedianmingcheng);
        holder.tv2.setText("节点设施:"+jiedianentities.get(position).jiediansheshi);
        holder.tv3.setText("节点位置:"+jiedianentities.get(position).jiedianweizhi);
        holder.imageView.setImageBitmap(jiedianentities.get(position).bitmap);

        return convertView;
    }


    class ViewHolder{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView imageView;

    }
}
