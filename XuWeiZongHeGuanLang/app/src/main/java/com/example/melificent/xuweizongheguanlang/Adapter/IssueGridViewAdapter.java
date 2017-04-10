package com.example.melificent.xuweizongheguanlang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.melificent.xuweizongheguanlang.Bean.IssueGridViewBean;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/3/1.
 */

public class IssueGridViewAdapter extends BaseAdapter {
    List<IssueGridViewBean> lists = new ArrayList<>();
    Context mcontext;

    public IssueGridViewAdapter(List<IssueGridViewBean> lists, Context mcontext) {
        this.lists = lists;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public IssueGridViewBean getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IssueFedBackViewHolder holder = null;
        if (convertView == null){
            holder = new IssueFedBackViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.issue_fed_back_grideview_item,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.issue_grideview_imageview);
            convertView.setTag(holder);
        }
        holder = (IssueFedBackViewHolder) convertView.getTag();
        holder.imageView.setImageBitmap(getItem(position).bitmap);
        ModeDto modeDto = new ModeDto();
        if (position == 0){
            modeDto.setHasFirstLoad(true);
        }
        if (position == 0 && modeDto.isHasFirstLoad()){
            return convertView;
        }

        return convertView;
    }
    class  IssueFedBackViewHolder {
        ImageView imageView;
    }
    class ModeDto{
        private boolean hasFirstLoad = false;
        public boolean isHasFirstLoad(){
            return  hasFirstLoad;
        }
        public void setHasFirstLoad(Boolean hasFirstLoad){
            this.hasFirstLoad = hasFirstLoad;
        }
    }
}
