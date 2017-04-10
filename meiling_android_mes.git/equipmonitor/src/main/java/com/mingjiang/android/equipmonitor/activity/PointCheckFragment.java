package com.mingjiang.android.equipmonitor.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingjiang.android.equipmonitor.R;


/**
 * 点检信息
 * Created by wangdongjia on 2016-7-14 09:01:51
 */
public class PointCheckFragment extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_check, container);
        getDialog().setTitle("超声波焊接机点检");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //设置自定义的title  layout
        //getDialog().getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

    }
}
