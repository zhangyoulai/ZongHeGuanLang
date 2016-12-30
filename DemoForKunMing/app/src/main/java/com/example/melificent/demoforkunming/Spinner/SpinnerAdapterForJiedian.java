package com.example.melificent.demoforkunming.Spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/10/30.
 */

public class SpinnerAdapterForJiedian extends BaseAdapter {
    List<JiedianEntity> entities ;
    Context context;

    public SpinnerAdapterForJiedian(List<JiedianEntity> entities, Context context) {
        this.entities = entities;
        this.context = context;
    }

    @Override
    public int getCount() {
        return entities.size();
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
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context,R.layout.spinner_jiedian,null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_spinner_jiedian);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(entities.get(position).jiedian);
        return convertView;
    }
    class  ViewHolder{
        TextView textView;
    }
}
