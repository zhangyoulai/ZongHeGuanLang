package com.mingjiang.android.app.bean;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/24.
 * email：kouzeping@shmingjiang.org.cn
 *[{"material_code": "101", "new_charger": 1, "number": 12, "material_name": "101", "point": "101"}]
 * {
     "material_code": 物料编码,
     "new_charger": 调拨单id,
     "number": 物料数量（int）,
     "material_name": 物料名称,
     "point": 下料点ID
 },
 */
public class MarrtialItem {
    public  String material_code="";
    public  int new_charger=0;
    public  int number=0;
    public  String material_name="";
    public  String point="";

    public MarrtialItem() {
    }

    public String getMaterial_code() {
        return material_code;
    }

    public void setMaterial_code(String material_code) {
        this.material_code = material_code;
    }

    public int getNew_charger() {
        return new_charger;
    }

    public void setNew_charger(int new_charger) {
        this.new_charger = new_charger;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
