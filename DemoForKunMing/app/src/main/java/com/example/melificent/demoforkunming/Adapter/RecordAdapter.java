package com.example.melificent.demoforkunming.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/12/20.
 */

public class RecordAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Record>  records ;

    public RecordAdapter(Context mcontext, List<Record> records) {
        this.mcontext = mcontext;
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size()==0 ? 0:records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.size()==0 ? null:records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = View.inflate(mcontext, R.layout.record_item,null);
            holder = new ViewHolder();
            holder.tv_Record = (TextView) convertView.findViewById(R.id.tv_record_item);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_Record.setText(records.get(position).record);
        return convertView;
    }
    class  ViewHolder{
        TextView tv_Record;
    }
}
