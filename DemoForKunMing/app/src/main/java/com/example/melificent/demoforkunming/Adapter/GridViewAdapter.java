package com.example.melificent.demoforkunming.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.melificent.demoforkunming.R;

import java.util.List;

/**
 * Created by p on 2016/11/16.
 */

public class GridViewAdapter extends BaseAdapter {
    List<Bitmap> bitmaps;
    Context mcontext;
    public GridViewAdapter(Context context,List<Bitmap> b) {
        this.mcontext = context;
        this.bitmaps = b;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
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
        GridViewViewHolder holder = null;
        if (convertView==null){
            convertView = View.inflate(mcontext, R.layout.gridview,null);
            holder = new GridViewViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.gridview_img);
            convertView.setTag(holder);
        }
        holder = (GridViewViewHolder) convertView.getTag();
        holder.imageView.setImageBitmap(bitmaps.get(position));
        return convertView;
    }
    class  GridViewViewHolder{
        ImageView imageView;
    }
}
