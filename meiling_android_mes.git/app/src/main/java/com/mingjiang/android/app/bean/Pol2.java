package com.mingjiang.android.app.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangdongjia on 2016/7/15.
 */
public class Pol2 {
    //实时显示数据条数
    public static final int SIZE = 50;
    //插入位置
    public static final int INSERT_POSITION = 0;

    //当前能量
    public Float currentValue;

    //最大值
    public Float maxValue;

    //最小值
    public Float minValue;

    //最适值
    public Float bestValue;

    //能量列表
    public List<Float> valueList = new LinkedList<Float>();

    //添加值
    public void addValue(Float value){

        valueList.add(INSERT_POSITION, value);

        if(valueList.size() == SIZE){
            valueList.remove(SIZE-1);
        }

    }
}
