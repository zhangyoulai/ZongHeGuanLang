package com.example.melificent.xuweizongheguanlang.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.map.BitmapDescriptor;

import java.io.Serializable;

/**
 * Created by p on 2017/2/15.
 * this is a entity class about marker,implements Serializable and give the serialVersionUID for serialize and unserialize.
 * give the different construction for this entity
 */

public class MarkBean implements Serializable{
    public  static final long serialVersionUID = -1010711775392052966L;
    public double latitude;
    public double longitude;
    public String identification;
    public BitmapDescriptor bitmapDescriptor;

    public MarkBean(double latitude, double longitude, String identification, BitmapDescriptor bitmapDescriptor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.identification = identification;
        this.bitmapDescriptor = bitmapDescriptor;
    }

    public MarkBean(double latitude, double longitude, BitmapDescriptor bitmapDescriptor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bitmapDescriptor = bitmapDescriptor;
    }

    public MarkBean(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
