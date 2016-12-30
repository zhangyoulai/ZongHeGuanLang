package com.example.melificent.demoforkunming.Fragment;

import android.graphics.Bitmap;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.example.melificent.demoforkunming.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2016/10/27.
 * 构建实体类使用于循环放置marker，可以在实体类的一开始就构建static数据
 * 实体类实现serializable
 * 通过不同的构造方法，可以简化实体类的构筑，只放置自己需要的使用参数
 */

public   class Info implements Serializable {
    public  static final long serialVersionUID = -1010711775392052966L;
    public  double latitude;
    public  double longitude;
    public  int imgId;
    public  String name;
    public  String distance;
    public  int zan;
    public BitmapDescriptor bitmapDescriptor;
    public    static List<Info> infos = new ArrayList<>();
    static
    {
       infos.add(new Info(25.192301115431, 102.957196977816,R.drawable.video,"a","1",1));
        infos.add(new Info(25.192301115431, 102.9561577816,R.drawable.video,"b","2",2));
        infos.add(new Info(25.192301115431, 102.9571987816,R.drawable.video,"c","3",3));
        infos.add(new Info(25.192301115431, 102.9541897816,R.drawable.video,"d","4",4));
        infos.add(new Info(25.192301115431, 102.9531957816,R.drawable.video,"e","5",5));

    }

    public Info(double latitude, double longitude, int imgId, String name,
                String distance, int zan)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.zan = zan;
    }

    public Info(double latitude, double longitude, BitmapDescriptor bitmapDescriptor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bitmapDescriptor = bitmapDescriptor;
    }
}
