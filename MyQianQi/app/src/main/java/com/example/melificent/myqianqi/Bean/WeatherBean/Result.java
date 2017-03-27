package com.example.melificent.myqianqi.Bean.WeatherBean;

import com.example.melificent.myqianqi.Bean.WeatherBean.Data;

import java.io.Serializable;

/**
 * Created by p on 2017/3/14.
 */
public class Result implements Serializable {
    private static final long serialVersionUID =31L;

    public String pubTime;
    public String reqTime;
    public Data data;
}
