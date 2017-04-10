package com.example.melificent.xuweizongheguanlang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.Bean.Task;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/3/1.
 */

public class TaskAdapter extends BaseAdapter {
    List<Task> tasks = new ArrayList<>();
    Context mcontext;

    public TaskAdapter(List<Task> tasks, Context mcontext) {
        this.tasks = tasks;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.inspection_task_item,null);
            holder.taskbody = (TextView) convertView.findViewById(R.id.inspection_task_Msg);
            holder.tasktime = (TextView) convertView.findViewById(R.id.inspection_task_time);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tasktime.setText(getItem(position).taskTime);
        holder.taskbody.setText(getItem(position).taskBody);

        return convertView;
    }
    class ViewHolder{
        TextView tasktime;
        TextView taskbody;
    }
}
