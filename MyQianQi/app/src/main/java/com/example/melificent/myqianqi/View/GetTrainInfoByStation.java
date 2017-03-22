package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;

import java.util.List;

/**
 * Created by p on 2017/3/15.
 */

public interface GetTrainInfoByStation {
    public void getTrainInfoByStationSuccess(List<TrainInfoByStationResultList> lists);
    public void getTrainInfoByStationFail(String Msg);
}
