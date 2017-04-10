package com.mingjiang.kouzeping.spectaculars.badness_monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.mingjiang.kouzeping.spectaculars.R;
import java.util.ArrayList;
import java.util.List;
import app.android.mingjiang.com.mpchartapi.PieChartUtil;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class BadnessAdapter extends BaseAdapter {
    Context mContext;
    List<BadnessItem> mList;

    public BadnessAdapter( List<BadnessItem> mList,Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setmList(List<BadnessItem> mList) {
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment5_badness_item, null);
            viewHolder.textViewType = (TextView) convertView.findViewById(R.id.fragment5_badness_producttype);
            viewHolder.textViewTotal = (TextView) convertView.findViewById(R.id.fragment5_badness_alltotal);
            viewHolder.textViewFpy = (TextView) convertView.findViewById(R.id.fragment5_badness_fpy);
            viewHolder.pieChart = (PieChart) convertView.findViewById(R.id.fragment5_badness_chart);

            viewHolder.textViewBadName1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname1);
            viewHolder.textViewBadValve1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue1);
            viewHolder.textViewTotalScale1 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale1);
            viewHolder.textViewBadScale1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale1);

            viewHolder.textViewBadName2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname2);
            viewHolder.textViewBadValve2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue2);
            viewHolder.textViewTotalScale2 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale2);
            viewHolder.textViewBadScale2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale2);

            viewHolder.textViewBadName3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname3);
            viewHolder.textViewBadValve3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue3);
            viewHolder.textViewTotalScale3 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale3);
            viewHolder.textViewBadScale3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale3);

            viewHolder.textViewBadName4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname4);
            viewHolder.textViewBadValve4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue4);
            viewHolder.textViewTotalScale4 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale4);
            viewHolder.textViewBadScale4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale4);

            viewHolder.textViewBadName5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname5);
            viewHolder.textViewBadValve5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue5);
            viewHolder.textViewTotalScale5 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale5);
            viewHolder.textViewBadScale5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale5);

            viewHolder.img1= (ImageView) convertView.findViewById(R.id.img_1);
            viewHolder.img2= (ImageView) convertView.findViewById(R.id.img_2);
            viewHolder.img3= (ImageView) convertView.findViewById(R.id.img_3);
            viewHolder.img4= (ImageView) convertView.findViewById(R.id.img_4);
            viewHolder.img5= (ImageView) convertView.findViewById(R.id.img_5);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BadnessItem item = mList.get(position);
        if (item.badtotal==0){
            item.badtotal=1000000000;
        }
        if (item.count==0){
            item.count=1;
        }
        List<Float> pieYValue = new ArrayList<>();
        List<String> pieXValue = new ArrayList<>();
        viewHolder.textViewType.setText(item.spec);
        viewHolder.textViewTotal.setText(item.count+ "");
        viewHolder.textViewFpy.setText((100-item.badcount*100/item.count)+"%");

        if (item.value1>0){
            viewHolder.textViewBadName1.setText(item.name1);
            viewHolder.textViewBadValve1.setText(item.value1 + "件");
            viewHolder.textViewTotalScale1.setText((item.value1* 100 / item.count) + "%");
            viewHolder.textViewBadScale1.setText((item.value1* 100 / item.badtotal) + "%");
            viewHolder.img1.setVisibility(View.VISIBLE);
            pieYValue.add(0f + item.value1);
            pieXValue.add(item.name1.length() > 6 ? item.name1.substring(0, 6) : item.name1);
        }else {
            viewHolder.textViewBadName1.setText("");
            viewHolder.textViewBadValve1.setText("");
            viewHolder.textViewTotalScale1.setText("");
            viewHolder.textViewBadScale1.setText("");
            viewHolder.img1.setVisibility(View.INVISIBLE);
        }

        if (item.value2>0) {
            viewHolder.textViewBadName2.setText(item.name2);
            viewHolder.textViewBadValve2.setText(item.value2 + "件");
            viewHolder.textViewTotalScale2.setText((item.value2 * 100 / item.count) + "%");
            viewHolder.textViewBadScale2.setText((item.value2 * 100 / item.badtotal) + "%");
            viewHolder.img2.setVisibility(View.VISIBLE);
            pieYValue.add(0f + item.value2);
            pieXValue.add(item.name2.length() > 6 ? item.name2.substring(0, 6) : item.name2);
        }else {
            viewHolder.textViewBadName2.setText("");
            viewHolder.textViewBadValve2.setText("");
            viewHolder.textViewTotalScale2.setText("");
            viewHolder.textViewBadScale2.setText("");
            viewHolder.img2.setVisibility(View.INVISIBLE);
        }
        if (item.value3>0) {
            viewHolder.textViewBadName3.setText(item.name3);
            viewHolder.textViewBadValve3.setText(item.value3 + "件");
            viewHolder.textViewTotalScale3.setText((item.value3 * 100 / item.count) + "%");
            viewHolder.textViewBadScale3.setText((item.value3 * 100 / item.badtotal) + "%");
            viewHolder.img3.setVisibility(View.VISIBLE);
            pieYValue.add(0f + item.value3);
            pieXValue.add(item.name3.length() > 6 ? item.name3.substring(0, 6) : item.name3);
        }else {
            viewHolder.textViewBadName3.setText("");
            viewHolder.textViewBadValve3.setText("");
            viewHolder.textViewTotalScale3.setText("");
            viewHolder.textViewBadScale3.setText("");
            viewHolder.img3.setVisibility(View.INVISIBLE);
        }

        if (item.value4>0) {
            viewHolder.textViewBadName4.setText(item.name4);
            viewHolder.textViewBadValve4.setText(item.value4 + "件");
            viewHolder.textViewTotalScale4.setText((item.value4 * 100 / item.count) + "%");
            viewHolder.textViewBadScale4.setText((item.value4 * 100 / item.badtotal) + "%");
            viewHolder.img4.setVisibility(View.VISIBLE);
            pieYValue.add(0f + item.value4);
            pieXValue.add(item.name4.length() > 6 ? item.name4.substring(0, 6) : item.name4);
        } else {
            viewHolder.textViewBadName4.setText("");
            viewHolder.textViewBadValve4.setText("");
            viewHolder.textViewTotalScale4.setText("");
            viewHolder.textViewBadScale4.setText("");
            viewHolder.img4.setVisibility(View.INVISIBLE);
        }
        if (item.value5>0) {
            viewHolder.textViewBadName5.setText(item.name5);
            viewHolder.textViewBadValve5.setText(item.value5 + "件");
            viewHolder.textViewTotalScale5.setText((item.value5 * 100 / item.count) + "%");
            viewHolder.textViewBadScale5.setText((item.value5 * 100 / item.badtotal) + "%");
            viewHolder.img5.setVisibility(View.VISIBLE);
            pieYValue.add(0f + item.value5);
            pieXValue.add(item.name5.length() > 6 ? item.name5.substring(0, 6) : item.name5);
        }else {
            viewHolder.textViewBadName5.setText("");
            viewHolder.textViewBadValve5.setText("");
            viewHolder.textViewTotalScale5.setText("");
            viewHolder.textViewBadScale5.setText("");
            viewHolder.img5.setVisibility(View.INVISIBLE);
        }
        //计算其他不良的占比。
        float other = 0f + item.badtotal - item.value1 - item.value2- item.value3 - item.value4 - item.value5;
        if (other < 0) {
            other = 0f;
        }
        pieYValue.add(other);

        if (item.badtotal==1000000000){
            pieXValue.add("全部合格");
            PieChartUtil.createPieChart(viewHolder.pieChart, "合格比例", pieYValue, pieXValue);
        }else {
            pieXValue.add("其他不良");
            PieChartUtil.createPieChart(viewHolder.pieChart, "不良比例", pieYValue, pieXValue);
        }
        return convertView;
    }
    private class ViewHolder {
        TextView textViewType, textViewTotal, textViewFpy,
                textViewBadName1, textViewBadValve1, textViewTotalScale1, textViewBadScale1,
                textViewBadName2, textViewBadValve2, textViewTotalScale2, textViewBadScale2,
                textViewBadName3, textViewBadValve3, textViewTotalScale3, textViewBadScale3,
                textViewBadName4, textViewBadValve4, textViewTotalScale4, textViewBadScale4,
                textViewBadName5, textViewBadValve5, textViewTotalScale5, textViewBadScale5;
        ImageView img1,img2,img3,img4,img5;
        private PieChart pieChart;
    }
}
