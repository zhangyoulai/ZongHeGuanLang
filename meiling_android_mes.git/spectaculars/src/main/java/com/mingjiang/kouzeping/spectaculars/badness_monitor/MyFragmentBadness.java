package com.mingjiang.kouzeping.spectaculars.badness_monitor;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentBadness extends Fragment {
    protected final static String TAG = MyFragmentBadness.class.getSimpleName();
    GridView mGridview;
    Context mContext;
    List<BadnessItem> mBadnessItems;
    BadnessAdapter mBadnessAdapter;
    private volatile boolean isRun = true;  //是否结束线程
    volatile int mRefreshtTime = 10000;       //刷新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getBadnessInfo();
//            getTestData();
        }
    };

    public MyFragmentBadness() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment5_badness, null);
        iniViewForGridview(view);
        getBadnessInfo();
//        getTestData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启刷新线程
        initViewForAuto();
    }

    private void iniViewForGridview(View view) {
        mGridview = (GridView) view.findViewById(R.id.fragment5_badness_gridview);
        mBadnessItems = new ArrayList<>();
        mBadnessAdapter = new BadnessAdapter(mBadnessItems, mContext);
        mGridview.setAdapter(mBadnessAdapter);
    }

    private void getBadnessInfo() {
        SpectacularsApplication.getNetService(mContext).queryBadness().subscribe(new Action1<List<BadnessItem>>() {
            @Override
            public void call(List<BadnessItem> badnessItems) {
                Log.e("cb--->",new Gson().toJson(badnessItems));
                EventBus.getDefault().post(new Event(badnessItems, Event.BADNESS_INFO));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, "获取产品不良信息失败");
                EventBus.getDefault().post(new Event("获取产品不良信息失败", Event.BADNESS_INFO_MISS));
            }
        });
    }

    public void getTestData() {
        List<BadnessItem> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            BadnessItem data = new BadnessItem();
            data.spec = ("产品类型" + i);
            data.name1 = ("第一类不良");
            data.value1 = (i + 5);
            data.name2 = ("第二类不良");
            data.value2 = (i + 4);
            data.name3 = ("第三类不良");
            data.value3 = (i + 3);
            data.name4 = ("第四类不良");
            data.value4 = (i + 2);
            data.name5 = ("第五类不良");
            data.value5 = (i + 1);
            data.badtotal = ((i + 5) * 5);
            data.badcount = ((i + 4) * 5);
            data.count = ((i + 5) * 8);
            list.add(data);
        }
        mGridview.setAdapter(new BadnessAdapter(list, getActivity()));
    }

    public void onEventMainThread(Event event) {
        if (Event.BADNESS_INFO == event.getActionType()) {
            List<BadnessItem> badnessItems = (List<BadnessItem>) event.getObject();
            if (badnessItems != null) {
                mBadnessItems = badnessItems;
                mBadnessAdapter.setmList(mBadnessItems);
                mBadnessAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(mContext, "没有停线记录", Toast.LENGTH_SHORT).show();
            }
        } else if (Event.BADNESS_INFO_MISS == event.getActionType()) {
            Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRun = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRun = false;
        EventBus.getDefault().unregister(this);
    }

    private void initViewForAuto() {
        // 自动更新数据
        isRun = true;
        MyThread myThread = new MyThread();
        myThread.start();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isRun) {
                mRefreshtTime = RefreshTimeUtils.getRefreshTime(mContext);
                SystemClock.sleep(mRefreshtTime);
                handler.sendEmptyMessage(0);
            }
        }
    }
}
