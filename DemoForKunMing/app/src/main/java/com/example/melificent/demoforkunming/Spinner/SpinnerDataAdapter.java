package com.example.melificent.demoforkunming.Spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.demoforkunming.Fragment.Info;
import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/10/28.
 */

public class SpinnerDataAdapter extends BaseAdapter {
    private Context context;
    private List<Infoentity> infos;

    public SpinnerDataAdapter(Context context, List<Infoentity> infos) {
        this.context = context;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.size();
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
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.spinner,null);
            holder.imageView  = (ImageView) convertView.findViewById(R.id.imageview);
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Infoentity info  = infos.get(position);
        holder.textView.setText(info.name);
        holder.imageView.setImageBitmap(info.bitmap);


        return convertView;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
