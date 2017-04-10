package com.mingjiang.android.equipmonitor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;


import com.mingjiang.android.equipmonitor.R;
import com.mingjiang.android.equipmonitor.adapter.FragmetDeviceAdapter;
import com.mingjiang.android.equipmonitor.entity.DeviceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 *
 * Updated by wangdongjia on 2016/6/21
 */
public class AllDeviceActivity extends Activity {

    protected final static String TAG = AllDeviceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_device);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }
    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    private void initView(){
        GridView mGridView= (GridView) findViewById(R.id.fragment1_gridview);
        List<DeviceData> list=new ArrayList<>();

        //超声波焊接机
        DeviceData data1=new DeviceData();
        data1.setName("设备名：超声波焊接机");
        data1.setCode("设备号：01");
        data1.setColor("green");
        list.add(data1);
        //箱体发泡机
        DeviceData data2=new DeviceData();
        data2.setName("设备名：8-箱体发泡机");
        data2.setCode("设备号：02");
        data2.setColor("green");
        list.add(data2);
        //灌注机
        DeviceData data3=new DeviceData();
        data3.setName("设备名：灌注机");
        data3.setCode("设备号：03");
        data3.setColor("green");
        list.add(data3);
        //线体
        DeviceData data4=new DeviceData();
        data4.setName("线体状态");
        data4.setCode("设备号：04");
        data4.setColor("green");
        list.add(data4);

        mGridView.setAdapter(new FragmetDeviceAdapter(this,list));
    }

    private void initData(){

    }
}
