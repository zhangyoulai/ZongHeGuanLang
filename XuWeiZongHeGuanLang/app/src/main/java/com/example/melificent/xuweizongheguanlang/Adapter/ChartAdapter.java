package com.example.melificent.xuweizongheguanlang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.Bean.Msg;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by p on 2017/2/9.
 * for chart ...........
 */

public class ChartAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Msg> msgs;
    public ChartAdapter(Context mcontext,List<Msg> msgs ) {
                this.mcontext = mcontext;
        this.msgs = msgs;
    }

    @Override
    public int getCount() {
        return msgs.size();
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
         if (convertView == null){
             convertView = LayoutInflater.from(mcontext).inflate(R.layout.chart_item,null);
             holder = new ViewHolder();
             holder.left = (TextView) convertView.findViewById(R.id.left_msg);
             holder.right = (TextView) convertView.findViewById(R.id.right_msg);
             holder.left_layout = (LinearLayout) convertView.findViewById(R.id.left_layout);
             holder.right_layout = (LinearLayout) convertView.findViewById(R.id.right_layout);
             convertView.setTag(holder);
         }
        holder = (ViewHolder) convertView.getTag();
        Msg msg = msgs.get(position);
        if (msg.type== Msg.TYPE_RECEIVE){
            holder.right_layout.setVisibility(View.GONE);
            holder.left.setText(msg.content);
        }
        if(msg.type == Msg.TYPE_SEND){
            holder.left_layout.setVisibility(View.GONE);
            holder.right.setText(msg.content);
        }
        return convertView;
    }
    class ViewHolder {
        TextView left;
        TextView right;
        LinearLayout left_layout;
        LinearLayout right_layout;
    }
}
