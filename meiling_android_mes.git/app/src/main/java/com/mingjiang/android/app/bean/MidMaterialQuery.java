package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MidMaterialQuery {

    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_NAME = "material_name";
    public static final String SEAT_ID = "seat_id";
    public static final String RESWEVOIR_AREA = "reservoir_area";
    public static final String PAGE_SIZE = "page_size";
    public static final String PAGE_NUMBER = "page_number";

    public String material_id;      //物料编码
    public String material_name;    //物料名称
    public String seat_id;          //库位编号
    public String reservoir_area;   //库区编号
    public String page_size;        //返回记录
    public String page_number;      //页码


    public Map<String,String> getQueryMap(){
        Map<String,String> map=new HashMap<>();
        map.put(MATERIAL_ID,material_id);
        map.put(MATERIAL_NAME,material_name);
        map.put(SEAT_ID,seat_id);
        map.put(RESWEVOIR_AREA,reservoir_area);
        map.put(PAGE_SIZE,page_size);
        map.put(PAGE_NUMBER,page_number);
        return map;
    }

    //将实体类数据转换为Map数据。
    public Map<String,String> setData(){

        Map<String,String> retMap = new HashMap<String,String>();

        retMap.put(MATERIAL_ID,this.material_id);
        retMap.put(MATERIAL_NAME,this.material_name);
        retMap.put(SEAT_ID,this.seat_id);
        retMap.put(RESWEVOIR_AREA,this.reservoir_area);
        if("".equals(this.page_size)){
            retMap.put(PAGE_SIZE,PAGE_SIZE);
        }else{
            retMap.put(PAGE_SIZE,this.page_size);
        }
        retMap.put(PAGE_NUMBER,this.page_number);
        return retMap;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
