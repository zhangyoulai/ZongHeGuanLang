package com.example.melificent.xuweizongheguanlang.Utils;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

/**
 * Created by p on 2017/1/16.
 * this utils can show piechart on your screen by method "createPieChart",this method need some params,
 * piechart,
 * centerDescription use to description this piechart in the center,
 * legendDescription ues to description every part of piechart,
 * count for description the part of piechart, caution this is type "int"
 * piechart part description use to description every part on the piechart,this is type"String[]"
 * fvalues every part value,this is type "float[]"
 * colors use to add color on the piechart.this is type "Integer[]"
 * ok that's all.
 */

public class PieChartTools {
    public static  void  creatPieChart(PieChart pieChart, String centerdescription, String legendDescription, int count, String[] piechartpartdescription, Float[] fvalues, Integer[] colors){
        PieData pieData = getPieData(count,piechartpartdescription,fvalues,legendDescription,colors);
        showChart(pieChart,centerdescription,pieData);

    }

    private static PieData getPieData(int count, String[] piechartpartdescription, Float[] fvalues, String legendDescription, Integer[] colors) {
        ArrayList<String> xValues = new ArrayList<>();//各部分的图例展示
        for (int i = 0 ; i<count;i++){
            xValues.add(piechartpartdescription[i]+fvalues[i]+"%");
        }

        ArrayList<Entry> yValues = new ArrayList<>();//用于封装各部分实际值
        for (int i=0;i<count;i++){
            yValues.add(new Entry(fvalues[i],i));
        }

        //y轴集合
        PieDataSet pieDataSet = new PieDataSet(yValues,legendDescription);
        pieDataSet.setSliceSpace(0f);//各部分的间隙

        ArrayList<Integer> Colors = new ArrayList<>();//用于封装各个部分的颜色值
        for (int i=0;i<count;i++){
            Colors.add(colors[i]);
        }
        pieDataSet.setColors(Colors);
        PieData peidata = new PieData(xValues,pieDataSet);
        return  peidata;

    }



    /**
     * 初始化pieChart的设置
     * @param pieChart 饼状图
     * @param centerdescription 中间空心圆的文字描述
     * @param pieData
     */
    private static void showChart(PieChart pieChart,String centerdescription, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);//设置半径
        pieChart.setTransparentCircleRadius(64f);//半透明圆圈

        pieChart.setDescription("气体详情");//描述信息

        pieChart.setDrawCenterText(true);//饼状图中间可以添加描述信息
        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90);//设置初始的旋转角度

        pieChart.setRotationEnabled(true);//可以手动旋转
        pieChart.setUsePercentValues(true);//以百分比显示

        pieChart.setCenterText(centerdescription);//中间区域显示的文字
        pieChart.setData(pieData);//饼状图设置数据

        //设置图例
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);//靠右边显示
        legend.setForm(Legend.LegendForm.SQUARE);//设置图例的显示样式，默认是方形
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(5f);
        pieChart.animateXY(3000,3000);

    }

}
