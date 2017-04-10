package com.example.melificent.xuweizongheguanlang.Bean;

import com.baidu.mapapi.map.BitmapDescriptor;

import java.io.Serializable;

/**
 * Created by p on 2017/2/21.
 */

public class Info implements Serializable {
    public static final long serialVersionUID = -758459502806858414L;
    public double latitude;
    public double longtitude;
    public BitmapDescriptor descriptor;

    public Info(double latitude, double longtitude, BitmapDescriptor descriptor) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.descriptor = descriptor;
    }
}
