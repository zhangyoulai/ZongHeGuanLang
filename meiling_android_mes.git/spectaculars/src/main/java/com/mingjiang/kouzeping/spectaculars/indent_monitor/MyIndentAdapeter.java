package com.mingjiang.kouzeping.spectaculars.indent_monitor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.kouzeping.spectaculars.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyIndentAdapeter extends BaseAdapter {
    List<IndentItem> mList;
    Context mContext;

    public MyIndentAdapeter(List<IndentItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setmList(List<IndentItem> mList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment2_indent_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.fragment2_item_textview1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.fragment2_item_textview2);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.fragment2_item_textview3);
            viewHolder.textView4 = (TextView) convertView.findViewById(R.id.fragment2_item_textview4);
            viewHolder.textView5 = (TextView) convertView.findViewById(R.id.fragment2_item_textview5);
            viewHolder.textView6 = (TextView) convertView.findViewById(R.id.fragment2_item_textview6);
            viewHolder.textView7 = (TextView) convertView.findViewById(R.id.fragment2_item_textview7);
            viewHolder.textView8 = (TextView) convertView.findViewById(R.id.fragment2_item_textview8);
            viewHolder.textView9 = (TextView) convertView.findViewById(R.id.fragment2_item_textview9);
            viewHolder.textView10 = (TextView) convertView.findViewById(R.id.fragment2_item_textview10);
            viewHolder.textView11 = (TextView) convertView.findViewById(R.id.fragment2_item_textview11);
            viewHolder.textView12 = (TextView) convertView.findViewById(R.id.fragment2_item_textview12);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        order_id:生产订单号
//        batch_id：生产批次号-->
//                product_line:生产线
//                export_id:出口评审号
//                export_country:出口国家
//                product_id:成品代码
//                order_quantity:订单数量
//                expire_date:完成日期
//                is_customize：定制化订单
//                notes:备注
//                daily_plan:生产日计划
//

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.gray);
        } else {
            convertView.setBackgroundResource(R.color.list_line2_bg);
        }
        IndentItem deviceDetail = mList.get(position);
        viewHolder.textView1.setText(deviceDetail.getOrder_id());
        if (deviceDetail.getBatch_id().contains("false")){
            viewHolder.textView2.setText("/");
        }else {
            viewHolder.textView2.setText(deviceDetail.getBatch_id());
        }
        viewHolder.textView3.setText(deviceDetail.getProduct_line());
        if (deviceDetail.getExport_id().contains("false")){
            viewHolder.textView4.setText("/");//出口号
        }else{
            viewHolder.textView4.setText(deviceDetail.getExport_id());//出口号
        }
        if (deviceDetail.getExport_country().contains("false")){
            viewHolder.textView5.setText("/");
        }else{
            Log.e("cb---->",deviceDetail.getExport_country());
            viewHolder.textView5.setText(deviceDetail.getExport_country());//出口国家
        }
        viewHolder.textView6.setText(deviceDetail.getProduct_id());
        if (deviceDetail.getIs_customize().contains("false")){
            viewHolder.textView7.setText("/");
        }else{
            viewHolder.textView7.setText(deviceDetail.getIs_customize());//定制化
        }
        viewHolder.textView8.setText(deviceDetail.getOrder_quantity());
        if (deviceDetail.getFinish_number().contains("false")){
            viewHolder.textView9.setText("/");
        }else{
            viewHolder.textView9.setText(deviceDetail.getFinish_number());//完成数量
        }
        int finish = 0;
        int order_1uantity = -1;
        if (deviceDetail.getFinish_number() != null && !"false".equals(deviceDetail.getFinish_number())) {
            finish = Integer.parseInt(deviceDetail.getFinish_number());
        }
        if (deviceDetail.getOrder_quantity() != null && !"false".equals(deviceDetail.getOrder_quantity())) {
            order_1uantity = Integer.parseInt(deviceDetail.getOrder_quantity());
        }
        double percentage = finish * 100 / (double) order_1uantity;
        DecimalFormat df = new DecimalFormat("######0.00");
        viewHolder.textView10.setText("" + df.format(percentage) + "%");
        if (deviceDetail.getExpire_date().contains("false")){
            viewHolder.textView11.setText("/");
        }else{
            viewHolder.textView11.setText(deviceDetail.getExpire_date());//完成日期
        }
        if (deviceDetail.getNotes().contains("false")){
            viewHolder.textView12.setText("/");
        }else {
            viewHolder.textView12.setText(deviceDetail.getNotes());
        }
        viewHolder.textView5.setSelected(true);
        viewHolder.textView12.setSelected(true);
        return convertView;
    }

    private class ViewHolder {
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12;
    }
}
