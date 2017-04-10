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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/1/16.
 * draw one line on linechart and give the value and setting
 * caution the config setting and linechart setting
 */

public class LineChartTools {
    public static void setLineChart(LineChart chart, String yname, String[] names, float[] values){
        //初始化图标数据源
        initChartConfig(chart);
        //设置数据
        if (names.length==values.length){
            SetLineChartData(chart,yname,names,values);
        }

    }

    private static void SetLineChartData(LineChart chart, String yname, String[] names, float[] values) {
        ArrayList<Entry> yValsl = new ArrayList<Entry>();
        for (int i  =0 ;i<values.length;i++){
            yValsl.add(new Entry(values[i],i));

        }
        LineDataSet lineDataSet = new LineDataSet(yValsl,yname);
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

        LineData data = new LineData(names,lineDataSet);
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
