package com.example.melificent.xuweizongheguanlang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.Bean.GrideBean;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by p on 2017/2/13.
 * for load View For BusinessActivity .....
 */

public class GrideAdapter extends BaseAdapter {
    private List<GrideBean> beans;
    private Context mContext;


    public GrideAdapter(List<GrideBean> beans, Context mContext) {
        this.beans = beans;
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return beans.size();
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
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_imageview);
            holder.textView = (TextView) convertView.findViewById(R.id.grid_image_describle);
            convertView.setTag(holder);

        }
        holder = (ViewHolder) convertView.getTag();
        GrideBean bean = beans.get(position);
        holder.textView.setText(bean.describle);
        holder.imageView.setImageBitmap(bean.bitmap);
        return convertView;
    }
    private  class  ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
