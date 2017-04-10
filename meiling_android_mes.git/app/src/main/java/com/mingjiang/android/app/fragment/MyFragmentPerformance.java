package com.mingjiang.android.app.fragment;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mingjiang.android.app.AppContext;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.adapter.PerformanceAdapter;
import com.mingjiang.android.app.bean.Performance;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by CaoBin on 2016/9/21.
 */
public class MyFragmentPerformance extends Fragment {
    protected final static String TAG = MyFragmentPerformance.class.getSimpleName();
    @InjectView(R.id.fragment_performance_gridview)
    GridView fragmentPerformanceGridview;
    private View view;

    private PerformanceAdapter adapter;
    private List<Performance> performances;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.fragment_performance,container,false);
        }
        ButterKnife.inject(this, view);
        initData();
        new Thread(new PerformanceThread()).start();
        return view;
    }

    public void initData() {
        performances=new ArrayList<>();
        adapter=new PerformanceAdapter(getActivity(),performances);
        fragmentPerformanceGridview.setAdapter(adapter);
    }


    public void getData(){
        AppContext.getTestService(getActivity()).getPerfomance("")
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(
                new Action1<List<Performance>>() {
                    @Override
                    public void call(List<Performance> performances) {
                        adapter.setPerformances(performances);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("111111111111111","kkkk");
                        Log.e("cb-->",throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class PerformanceThread implements Runnable{

        @Override
        public void run() {
            while (true){
                getData();
                SystemClock.sleep(10000);
            }
        }
    }
}
