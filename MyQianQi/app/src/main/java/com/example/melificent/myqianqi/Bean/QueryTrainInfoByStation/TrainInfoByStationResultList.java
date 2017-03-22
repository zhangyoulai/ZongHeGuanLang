package com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p on 2017/3/15.
 */
public class TrainInfoByStationResultList implements Serializable {
    private static final long serialVersionUID = 2L;
            public String train_no;
            public String train_type;
            public String start_station;
            public String start_station_type;
            public String end_station;
            public String end_station_type;
            public String start_time;
            public String end_time;
            public String run_time;
           public List<TrainPriceList> price_list;
    
            public String run_distance;
            public String m_chaxun_url;
}
