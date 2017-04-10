package com.mingjiang.android.app.bean;

import java.util.ArrayList;

/**
 * Created by kouzeping on 2016/2/3.
 * email：kouzeping@shmingjiang.org.cn
 *
 * column：x轴显示的名称
 * data：y轴显示的值
 */
public class YieldItem {
    ArrayList<String> column;
    ArrayList<Float> data;

    public ArrayList<String> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<String> column) {
        this.column = column;
    }

    public ArrayList<Float> getData() {
        return data;
    }

    public void setData(ArrayList<Float> data) {
        this.data = data;
    }
}
