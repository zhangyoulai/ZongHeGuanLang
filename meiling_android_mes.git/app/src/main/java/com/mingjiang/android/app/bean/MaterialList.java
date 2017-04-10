package com.mingjiang.android.app.bean;

/**
 * Created by kouzeping on 2016/3/23.
 * email：kouzeping@shmingjiang.org.cn
 *
 * {
 "material_id": "12",物料编码
 "alter_number": "12"退料数量
 }
 */
public class MaterialList {
    public String material_name;
    public String material_id;
    public int alter_number;

    public MaterialList() {
    }

    public MaterialList(String material_name, String material_id, int alter_number) {
        this.material_name = material_name;
        this.material_id = material_id;
        this.alter_number = alter_number;
    }
}
