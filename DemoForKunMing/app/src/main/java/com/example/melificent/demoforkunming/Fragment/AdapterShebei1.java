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

public class AdapterShebei1 extends BaseAdapter {
    Context mcontext;
   List<shebeientity> shebeientities;

    public AdapterShebei1(Context mcontext, List<shebeientity> shebeientities) {
        this.mcontext = mcontext;
        this.shebeientities = shebeientities;
    }

    @Override
    public int getCount() {
        return shebeientities.size();
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
       ViewHolder holder = null;
        if (convertView==null){
            convertView= View.inflate(mcontext, R.layout.adapter_shebei,null);
            holder = new ViewHolder();
            holder.textview1 = (TextView) convertView.findViewById(R.id.textview1);
            holder.textview2 = (TextView) convertView.findViewById(R.id.textview2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
            holder.textView4 = (TextView) convertView.findViewById(R.id.textview4);
            holder.textView5= (TextView) convertView.findViewById(R.id.textview5);
            holder.textView6 = (TextView) convertView.findViewById(R.id.textview6);
            holder.textView7 = (TextView) convertView.findViewById(R.id.textview7);

            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.textview1.setText("设备型号："+shebeientities.get(position).shebeileixing);
        holder.textview2.setText("设备名称："+shebeientities.get(position).shebeimingcheng);
        holder.textView3.setText("位置："+shebeientities.get(position).weizhi);
        holder.textView4.setText("维修记录一:"+shebeientities.get(position).weixiujiluyi);
        holder.textView5.setText("维修记录二:"+shebeientities.get(position).weixiujiluer);
        holder.textView6.setText("维修记录三:"+shebeientities.get(position).weixiujilusan);
        holder.textView7.setText("使用年限："+shebeientities.get(position).shiyongnianxian);
        return convertView;
    }
    class  ViewHolder{
        TextView textview1;
        TextView textview2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;

    }
}
