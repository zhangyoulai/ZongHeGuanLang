package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.MidMaterialValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MiddleLibAdapter extends BaseAdapter {

    private Context context = null;
    private List<MidMaterialValue> midMaterialValueList = new ArrayList<MidMaterialValue>();

    public MiddleLibAdapter(Context context, List<MidMaterialValue> midMaterialValueList) {
        this.context = context;
        this.midMaterialValueList = midMaterialValueList;
    }

    public void setMidMaterialValueList(List<MidMaterialValue> midMaterialValueList) {
        this.midMaterialValueList = midMaterialValueList;
    }

    @Override
    public int getCount() {
        return midMaterialValueList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.midMaterialValueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MidMaterialValue midMaterialValue = midMaterialValueList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment4_material_item, null);
            viewHolder = new ViewHolder();
            viewHolder.material_id = (TextView) convertView.findViewById(R.id.material_id);
            viewHolder.material_name = (TextView) convertView.findViewById(R.id.material_name);
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.seat_id = (TextView) convertView.findViewById(R.id.seat_id);
            viewHolder.safety = (TextView) convertView.findViewById(R.id.safety);
            viewHolder.safety_stock = (TextView) convertView.findViewById(R.id.safety_stock);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.shortage = (TextView) convertView.findViewById(R.id.shortage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.material_id.setText(midMaterialValue.material_id);
        viewHolder.material_name.setText(midMaterialValue.material_name);
        viewHolder.id.setText(midMaterialValue.id);
        viewHolder.seat_id.setText(midMaterialValue.seat_id);
        viewHolder.safety.setText(midMaterialValue.safety ? "是" : "否");
        viewHolder.safety_stock.setText(midMaterialValue.safety_stock);
        viewHolder.number.setText(midMaterialValue.number);
        viewHolder.shortage.setText(midMaterialValue.shortage);

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.gray);
        } else {
            convertView.setBackgroundResource(R.color.white);
        }
        if (!midMaterialValue.safety) {
            convertView.setBackgroundResource(R.color.red);
        }
        return convertView;
    }

    class ViewHolder {
        TextView material_id, material_name, seat_id, safety, safety_stock, number, shortage, id;
    }
}
