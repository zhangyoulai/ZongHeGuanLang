package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;

import java.util.List;

/**
 * Created by p on 2017/3/15.
 */

public interface GetTrainInfoByTrainName {
   public void getTrainInfoByTrainNameSuccess(TrainInfoResult results);
   public void getTrainInfoByTrainNameFaile(String error_Msg);
}
