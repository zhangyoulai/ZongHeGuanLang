package com.example.melificent.xuweizongheguanlang.Utils;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by p on 2017/1/16.
 * can draw three line on the same linechart,only need to transmit three values and the do three settings
 *
 */

public class LineChartTools3 {
    public static void setLineChart(LineChart chart, String yname,String ywarnning,String yname3, String[] names, float[] values,float[] warnning,float[] values3){
        //初始化图标数据源
        initChartConfig(chart);
        //设置数据
        if (names.length==values.length){
            SetLineChartData(chart,yname,ywarnning,yname3,names,values,warnning,values3);
        }

    }

    private static void SetLineChartData(LineChart chart, String yname,String ywarnning,String ynames3, String[] names, float[] values,float[] warnning,float[] values3) {
        ArrayList<Entry> yValsl = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<>();
        ArrayList<Entry> yValsl3 = new ArrayList<>();
        for (int i  =0 ;i<values.length;i++){
            yValsl.add(new Entry(values[i],i));

        }
        for (int i = 0 ;i<values.length;i++){
            yVals2.add(new Entry(warnning[i],i));
        }
        for (int i=0;i<values.length;i++){
            yValsl3.add(new Entry(values3[i],i));
        }
        LineDataSet lineDataSet = new LineDataSet(yValsl,yname);


        LineDataSet lineDataSetWarnning = new LineDataSet(yVals2,ywarnning);
        lineDataSetWarnning.setDrawCircleHole(true);
        lineDataSetWarnning.setValueTextSize(20f);
        lineDataSetWarnning.setValueTextColor(Color.WHITE);
        lineDataSetWarnning.setColor(Color.parseColor("#f26077"));

        LineDataSet lineDataSetValues3 = new LineDataSet(yValsl3,ynames3);
        lineDataSetValues3.setDrawCircleHole(true);
        lineDataSetValues3.setValueTextSize(20f);
        lineDataSetValues3.setValueTextColor(Color.WHITE);
        lineDataSetValues3.setColor(Color.parseColor("#00C0BF"));

        //数据点颜色
        lineDataSet.setCircleColor(Color.BLUE);
        //数据点颜色
        lineDataSet.setCircleSize(4f);
        lineDataSet.setValueTextSize(20f);
        //实心圆
        lineDataSet.setDrawCircleHole(true);
        //数据文字大小
        lineDataSet.setValueTextSize(20f);
        //设置数据字体颜色
        lineDataSet.setValueTextColor(Color.WHITE);

        lineDataSet.setColor(Color.rgb(255,208,0));
//        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineDataSet.setValueTextSize(Color.rgb(255,208,0));


//        data.setValueTextSize(10f);
//        chart.setData(data);
        ArrayList<LineDataSet> mLineDataSet = new ArrayList<>();
        mLineDataSet.add(lineDataSet);
        mLineDataSet.add(lineDataSetWarnning);
        mLineDataSet.add(lineDataSetValues3);
        LineData data = new LineData(names,mLineDataSet);
        data.setValueTextSize(10f);
        chart.setData(data);
        chart.invalidate();


    }

    private static void initChartConfig(LineChart chart) {
        chart.setDrawGridBackground(false);
        chart.setDescription("");
        chart.setNoDataTextDescription("没有数据源");
        //图标的背景颜色设置
        chart.setBackgroundColor(Color.rgb(31,39,65));
        //y轴设置
        YAxis leftAxis = chart.getAxisLeft();
        //y轴颜色设置
        leftAxis.setTextColor(Color.BLUE);
        //从0开始
        leftAxis.setStartAtZero(true);
        //y轴字体大小
        leftAxis.setTextSize(16f);

        chart.getAxisRight().setEnabled(true);
        chart.getAxisRight().setTextColor(Color.alpha(100));
        chart.getAxisRight().setAxisLineWidth(0);
        chart.getAxisRight().setAxisLineColor(Color.alpha(100));

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GREEN);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);

//        chart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        chart.animateXY(3000,3000, Easing.EasingOption.EaseInOutQuart, Easing.EasingOption.EaseInOutQuart);
        Legend legend = chart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setFormSize(9f);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(20f);
        legend.setXEntrySpace(4f);
        chart.invalidate();
    }
}
