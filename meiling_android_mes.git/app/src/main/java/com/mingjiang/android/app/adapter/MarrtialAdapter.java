package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.AroundMaterialValue;

import java.util.ArrayList;
import java.util.List;


/**
 * 备注：线边库数据显示。
 * 作者：wangzs on 2016/2/19 17:32
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MarrtialAdapter extends BaseAdapter {

    private Context context = null;
    private List<AroundMaterialValue> aroundMaterialValueArrayList = new ArrayList<AroundMaterialValue>();

    public MarrtialAdapter(Context context, List<AroundMaterialValue> aroundMaterialValueArrayList) {
        this.context = context;
        this.aroundMaterialValueArrayList = aroundMaterialValueArrayList;
    }

    @Override
    public int getCount() {
        return aroundMaterialValueArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return aroundMaterialValueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AroundMaterialValue aroundMaterialValue = aroundMaterialValueArrayList.get(position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.marrtial_fragment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.material_id = (TextView) convertView.findViewById(R.id.material_id);
            viewHolder.material_name = (TextView) convertView.findViewById(R.id.material_name);
            viewHolder.safety = (TextView) convertView.findViewById(R.id.safety);
            viewHolder.safety_stock = (TextView) convertView.findViewById(R.id.safety_stock);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.shortage = (TextView) convertView.findViewById(R.id.shortage);
            viewHolder.expends = (TextView) convertView.findViewById(R.id.expends);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.gray);
        } else {
            convertView.setBackgroundResource(R.color.white);
        }
        if (aroundMaterialValue.safety) {
            viewHolder.safety.setText("是");
        } else {
            viewHolder.safety.setText("否");
            convertView.setBackgroundResource(R.color.red);
        }
        viewHolder.material_id.setText(aroundMaterialValue.material_id);
        viewHolder.material_name.setText(aroundMaterialValue.material_name);
        viewHolder.safety_stock.setText(aroundMaterialValue.safety_stock);
        viewHolder.number.setText(aroundMaterialValue.number);
        viewHolder.shortage.setText(aroundMaterialValue.shortage);
        viewHolder.expends.setText(aroundMaterialValue.expends);
        return convertView;
    }

    class ViewHolder {
        TextView material_id, material_name, safety, safety_stock, number, shortage, expends;
    }

}
