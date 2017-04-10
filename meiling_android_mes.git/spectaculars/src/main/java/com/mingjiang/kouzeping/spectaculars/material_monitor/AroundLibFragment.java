package com.mingjiang.kouzeping.spectaculars.material_monitor;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mingjiang.android.base.api.bootstrap.BootstrapButtonApi;
import com.mingjiang.android.base.api.bootstrap.BootstrapEditTextApi;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.adapter.AroundLibAdapter;
import com.mingjiang.kouzeping.spectaculars.bean.AroundMaterialQuery;
import com.mingjiang.kouzeping.spectaculars.bean.AroundMaterialResult;
import com.mingjiang.kouzeping.spectaculars.bean.AroundMaterialValue;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：线边库信息。
 * 作者：wangzs on 2016/2/19 17:33
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AroundLibFragment extends Fragment {

    protected final static String TAG = AroundLibFragment.class.getSimpleName();

    private Context context = null;
//    private View view = null;
    private AroundLibAdapter adapter = null;

    TextView mTextview1;
    LinearLayout mLinearLayout;

    private ListView materialList = null;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText areaIdEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn = null;

    private volatile boolean isRun = true;  //是否结束线程
    volatile int mRefreshtTime = 10000;       //刷新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealQueryData();
        }
    };

    public AroundLibFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4_around, null);
        context = getActivity();
        EventBus.getDefault().register(this);
        initView(view);
        //开始时默认获取一次数据
        dealQueryData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启刷新线程
        initViewForAuto();
    }

    private void initView(View view) {
        mLinearLayout = (LinearLayout)view.findViewById(R.id.fragment4_linearlayout);
        mTextview1 = (TextView)view.findViewById(R.id.fragment4_textveiw1);
        mTextview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        materialList = (ListView) view.findViewById(R.id.around_material_list);
        materialIdEdit = (BootstrapEditText) view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText) view.findViewById(R.id.material_name);
        areaIdEdit = (BootstrapEditText) view.findViewById(R.id.area_id);
        pageSizeSpinner = (Spinner) view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText) view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton) view.findViewById(R.id.material_query);
        BootstrapButtonApi.initConfig(queryBtn, 2);
        BootstrapEditTextApi.initConfig(materialIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit, 1.5f);
        BootstrapEditTextApi.initConfig(areaIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(pageNumberEdit, 1.5f);
        pageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));    //设置颜色
                tv.setTextSize(getResources().getDimension(R.dimen.item_text));    //设置大小
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        queryBtn.setOnClickListener(new QueryClick());
    }

    //执行物料数据查询操作。
    class QueryClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dealQueryData();
        }
    }

    //组织查询数据。
    private void dealQueryData() {
        AroundMaterialQuery aroundMaterialQuery = new AroundMaterialQuery();
        aroundMaterialQuery.material_id = materialIdEdit.getText().toString();
        aroundMaterialQuery.material_name = materialNameEdit.getText().toString();
        aroundMaterialQuery.area_id = areaIdEdit.getText().toString();
        aroundMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        aroundMaterialQuery.page_number = pageNumberEdit.getText().toString();
        dealQueary(aroundMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(AroundMaterialQuery aroundMaterialQuery) {
        Map<String, String> retMap = aroundMaterialQuery.setData();
        SpectacularsApplication.getNetService(context).queryAroundMaterial(retMap).subscribe(new Action1<AroundMaterialResult>() {
            @Override
            public void call(AroundMaterialResult aroundMaterialResult) {
                EventBus.getDefault().post(new Event(aroundMaterialResult, Event.ACTION_AROUND_LIBARY_QUERY));
                Log.i(TAG,aroundMaterialResult.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(AroundMaterialResult aroundMaterialResult) {
        List<AroundMaterialValue> list = aroundMaterialResult.result;
        adapter = new AroundLibAdapter(context, list);
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(Event event) {
        if (Event.ACTION_AROUND_LIBARY_QUERY == event.getActionType()) {
            AroundMaterialResult aroundMaterialResult = (AroundMaterialResult) event.getObjectMsg();
            dealQueryDataShow(aroundMaterialResult);
//            int number = Integer.valueOf(pageNumberEdit.getText().toString());
//            pageNumberEdit.setText(number + 1);
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
                mRefreshtTime = RefreshTimeUtils.getRefreshTime(context);
                SystemClock.sleep(mRefreshtTime);
                handler.sendEmptyMessage(0);
            }
        }
    }

}
