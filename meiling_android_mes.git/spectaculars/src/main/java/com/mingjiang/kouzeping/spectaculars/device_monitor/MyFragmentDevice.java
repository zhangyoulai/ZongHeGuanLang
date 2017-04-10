package com.mingjiang.kouzeping.spectaculars.device_monitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.mingjiang.kouzeping.spectaculars.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 *
 * Updated by wangdongjia on 2016/6/21
 */
public class MyFragmentDevice extends Fragment {
    public MyFragmentDevice() {
        // TODO Auto-generated constructor stub
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_device, null);
        GridView mGridView= (GridView) view.findViewById(R.id.fragment1_gridview);
        List<DeviceData> list=new ArrayList<>();

        //超声波焊接机
        DeviceData data1=new DeviceData();
        data1.setName("超声波焊接机");
        data1.setCode("01");
        data1.setColor("green");
        list.add(data1);
        //发泡机
        DeviceData data2=new DeviceData();
        data2.setName("箱体发泡机");
        data2.setCode("02");
        data2.setColor("green");
        list.add(data2);
        //灌注机
        DeviceData data3=new DeviceData();
        data3.setName("灌注机");
        data3.setCode("03");
        data3.setColor("green");
        list.add(data3);

        mGridView.setAdapter(new FragmetDeviceAdapter(getActivity(),list));
        return view;
    }
}
