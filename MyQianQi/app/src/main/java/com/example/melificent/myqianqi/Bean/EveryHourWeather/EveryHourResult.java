package com.example.melificent.myqianqi.Bean.EveryHourWeather;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p on 2017/3/21.
 */
public class EveryHourResult implements Serializable {
    private static final long serialVersionUID =33L;
    public List<Series> series;
    public String pubtime;
    public String startTime;
    public String endTime;
    public int count;

}
