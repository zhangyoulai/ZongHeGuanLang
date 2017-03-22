package com.example.melificent.myqianqi.Bean.Train;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p on 2017/3/15.
 */
public class TrainInfoResult implements Serializable {
    private static final long serialVersionUID = 4L;
    public Train_info train_info;
    public List<Station_List> station_list;
}
