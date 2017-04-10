package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.mingjiang.android.app.R;
import com.mingjiang.android.app.ui.FoamingMachineActivity;
import com.mingjiang.android.app.ui.PerfusionMachineActivity;
import com.mingjiang.android.app.ui.WeldmentActivity;
import com.mingjiang.android.app.bean.DeviceData;

import java.util.List;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class FragmetDeviceAdapter extends BaseAdapter {
    Context mContext;
    List<DeviceData> mList;

    public FragmetDeviceAdapter(Context mContext, List<DeviceData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment1_device_item,null);
            viewHolder.textView1=(TextView)convertView.findViewById(R.id.fragment1_item_textview1);
            viewHolder.textView2=(TextView)convertView.findViewById(R.id.fragment1_item_textview2);
            viewHolder.button=(Button)convertView.findViewById(R.id.fragment1_item_buttton);
            viewHolder.colorTv = (TextView)convertView.findViewById(R.id.tv_color);//运行状态
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String color = mList.get(position).getColor();
        if (color.equals("green")){
            //运行中
            viewHolder.colorTv.setBackgroundResource(R.color.green);
        }else if (color.equals("red")){
            //暂停
            viewHolder.colorTv.setBackgroundResource(R.color.red);
        }else if (color.equals("blue")){
            //待运行
            viewHolder.colorTv.setBackgroundResource(R.color.blue);
        }
        viewHolder.textView1.setText(mList.get(position).getName());
        viewHolder.textView2.setText(mList.get(position).getCode());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据设备号判断
                String code = mList.get(position).getCode();
                Intent intent = null;
                if (code.contains("01")){
                    intent = new Intent(mContext,WeldmentActivity.class);
                    intent.putExtra("key","01");
                }else if (code.contains("02")){
                    intent = new Intent(mContext,FoamingMachineActivity.class);
                    intent.putExtra("key","02");
                }else if (code.contains("03")){
                    intent = new Intent(mContext,PerfusionMachineActivity.class);
                    intent.putExtra("key","03");
                }
                mContext.startActivity(intent);
                Toast.makeText(mContext, "跳转到详情界面", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    private class ViewHolder{
        TextView textView1,textView2,colorTv;
        Button button;
    }
}
