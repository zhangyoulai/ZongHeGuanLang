package com.shmingjiang.www.mldaq.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.shmingjiang.www.mldaq.AppConfig;
import com.shmingjiang.www.mldaq.DaqApp;
import com.shmingjiang.www.mldaq.R;
import com.shmingjiang.www.mldaq.adapter.PowerValueAdapter;
import com.shmingjiang.www.mldaq.adapter.ThickValueAdapter;
import com.shmingjiang.www.mldaq.adapter.TimeValueAdapter;
import com.shmingjiang.www.mldaq.bean.WeldPower;
import com.shmingjiang.www.mldaq.bean.WeldThick;
import com.shmingjiang.www.mldaq.bean.WeldTime;
import com.shmingjiang.www.mldaq.bean.WeldmentParamsBean;
import com.shmingjiang.www.mldaq.bean.WeldmentPostBean;
import com.shmingjiang.www.mldaq.bean.WeldmentResult;
import com.shmingjiang.www.mldaq.bean.WeldmentValue;
import com.shmingjiang.www.mldaq.event.ComEvent;
import com.shmingjiang.www.mldaq.service.ComService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import app.android.mingjiang.com.mpchartapi.BrokeLineChartUtil;
import app.android.mingjiang.com.mpchartapi.PieChartUtil;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;


/**
 * 超声波焊接机监控数据界面。
 *
 */
public class WeldmentActivity extends Activity {

    protected final static String TAG = WeldmentActivity.class.getSimpleName();
    protected String code = "";
    protected String StringDEVICE_ID = "";
    StringBuffer sb = new StringBuffer();
    private volatile boolean isRun = true;  //是否结束线程
    Thread myThread;
    private TextView weldThickView = null;
    private TextView weldTimeView = null;
    private TextView weldPowerView = null;
    private TextView deviceNameView = null;
    private TextView postView = null;
    private TextView preStepView = null;
    private TextView afStepView = null;
    private TextView equipStatusView = null;

    //返回上级界面
    private ImageView mImageview;

    //检测信息
    private TextView checkPlanView;
    private TextView checkTimeView;
    private TextView checkContentView;

    //显示点检
    private TextView showCheckView;

    private LineChart thickDataGraph;
    private ListView thickDataList;
    private LineChart timeDataGraph;
    private ListView timeDataList;
    private LineChart powerDataGraph;
    private ListView powerDataList;
    private PieChart timePieChartGrapth;
    private TextClock weldTimeClock;

    private ThickValueAdapter thickValueAdapter = null;
    private TimeValueAdapter timeValueAdapter = null;
    private PowerValueAdapter powerValueAdapter = null;

    private BrokeLineChartUtil thickMJChart = null;
    private BrokeLineChartUtil timeMJChart = null;
    private BrokeLineChartUtil powerMJChart = null;

    private static final String THICK_CONTENT = "焊接厚度";
    private static final String POWER_CONTENT = "焊接能量";
    private static final String TIME_CONTENT = "焊接时间";
    private static final String THICK_DESCRIPT = "焊接厚度变化曲线";
    private static final String POWER_DESCRIPT = "焊接能量变化曲线";
    private static final String TIME_DESCRIPT = "焊接时间变化曲线";

    private static final Float THICK_HEIGH = 0.93f;
    private static final Float THICK_RIGHT = 0.80f;
    private static final Float THICK_LOW = 0.67f;

    private static final Float POWER_HEIGH = 572f;
    private static final Float POWER_RIGHT = 372f;
    private static final Float POWER_LOW = 172f;

    private static final Float TIME_HEIGH = 0.64f;
    private static final Float TIME_RIGHT = 0.34f;
    private static final Float TIME_LOW = 0.04f;

    private ArrayList<String> xList = new ArrayList<String>(Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));

    //动态更新绘图数据。
    private static final String KEY = "KEY";
    private static final String POWER = "power";
    private static final String TIME = "time";
    private static final String THICK = "thick";
    private static final String VALUE = "value";

    private List<String> timeScaleNameList = new ArrayList<>();
    private List<Float> timeScaleValueList = new ArrayList<>();

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle buldler = msg.getData();
            float value = buldler.getFloat(VALUE);
            String key =  buldler.getString(KEY);
            if(key.equals(POWER)){
                int intValue = (int)value;
                Float floatValue = (float)intValue;
                powerMJChart.addEntry("1", floatValue);
                weldPowerView.setText(intValue+"");
            }else if(key.equals(TIME)){
                float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                timeMJChart.addEntry("1", fValue);
                weldTimeView.setText(fValue + "");
            }else if(key.equals(THICK)){
               float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                thickMJChart.addEntry("1", fValue);
                weldThickView.setText(fValue+"");
            }
        }
    };

    public void initData() {
        startService(new Intent(this, ComService.class));//开启串口服务
        myThread = new MyThread();
        myThread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weldment);
        EventBus.getDefault().register(this);
        //获取设备唯一标示
//        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        StringDEVICE_ID = tm.getDeviceId();
//        Log.i(TAG,"StringDEVICE_ID:"+StringDEVICE_ID);
//        if (StringDEVICE_ID.contains("352005048247251")){
//            //利用线程获取数据
//            Log.i(TAG,"tmd");
//            myThread = new MyThread1();
//            myThread.start();
//        }
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        dataToGraph();
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_SCHUNK) {
//            String code1 = event.getMessage();
            String code1 = "|25.05.2016|16:58:45|   1866|1.00|0.20| 4.5 | 22|1.48|| 0.84 | 0.36 |  375 |";
            Log.i(TAG,"code1:"+code1);
            sb.append(String.valueOf(code1));
            Log.i(TAG, "等待处理的："+String.valueOf(sb));
            if(sb.length() >= 76) {
                Log.i(TAG, "开始处理时："+String.valueOf(sb));
                //判断格式是否正确
                handleCode();
                //处理完之后删除code的值
                sb.setLength(0);
                Log.i(TAG,"处理完成时："+String.valueOf(sb)+"end");
            }
        }
    }

    private void sendFridgePostBean(final String date, final String time, final String number,
                                    final String height,final String time1,final String energy) {

        Map<String,String> map=new HashMap<>();
        map.put("date",date);
        map.put("time",time);
        map.put("number",number);
        map.put("height",height);
        map.put("time1",time1);
        map.put("energy",energy);
        DaqApp.getApp().getNetService(this).sendWelementData(map).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                //加载数据成功
                Log.i(TAG,"hello");

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG,"nimabi");
            }
        });
    }

    /**
     * 处理串口接收到的数据
     */
    protected void handleCode(){
        String string = "|25.05.2016|16:58:45|   1866|1.00|0.20| 4.5 | 22|1.48|| 0.84 | 0.36 |  375 |";
        //日期
        String date = string.substring(1,11).trim();
        //当前时间
        String time = string.substring(12,20).trim();
        //计数器
        String number = string.substring(21,28).trim();//必须去空格
        //高度
        String height = string.substring(55,60).trim();
        //焊接持续时间
        String time1 = string.substring(62,68).trim();
        //能量
        String energy = string.substring(69,75).trim();
//        sendFridgePostBean(date,time,number,height,time1,energy);
        try {
//            POWER
            Float powerValue = new Float(sb.toString().substring(69,75).trim());
            Log.i(TAG,sb.toString().substring(69,75).trim());
            Float floatValue = (float)powerValue;
            powerMJChart.addEntry("1", floatValue);
            weldPowerView.setText(powerValue+"");
//       TIME
            Float timeValue = new Float(sb.toString().substring(62,68).trim());
            Log.i(TAG,sb.toString().substring(62,68).trim());
            float fValue = new BigDecimal(timeValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            timeMJChart.addEntry("1", fValue);
            weldTimeView.setText(fValue + "");
//       THICK
            Float thickValue = new Float(sb.toString().substring(55,60).trim());
            Log.i(TAG,sb.toString().substring(55,60).trim());//0.84
            float fValue1 = new BigDecimal(thickValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            thickMJChart.addEntry("1", fValue1);
            weldThickView.setText(fValue1+"");
        }catch (Exception e){
            //异常
            Log.i(TAG,"NumberFormatException");
            sb.setLength(0);
        }
    }

    public void initView(){

        weldThickView = (TextView)findViewById(R.id.weld_thick);
        weldTimeView = (TextView)findViewById(R.id.weld_time);
        weldPowerView = (TextView)findViewById(R.id.weld_power);
        //显示名字信息
        deviceNameView = (TextView)findViewById(R.id.device_name);
        postView = (TextView)findViewById(R.id.post);
        preStepView = (TextView)findViewById(R.id.pre_step);
        afStepView = (TextView)findViewById(R.id.af_step);
        equipStatusView = (TextView)findViewById(R.id.equip_status);
        //显示检测信息
        checkPlanView = (TextView)findViewById(R.id.check_plan);
        checkTimeView = (TextView)findViewById(R.id.check_time);
        checkContentView = (TextView)findViewById(R.id.check_content);
        //显示点检
        showCheckView = (TextView)findViewById(R.id.show_check);
        showCheckView.setOnClickListener(new ShowCheckListener());
        thickDataGraph = (LineChart)findViewById(R.id.thickDataGraph);
        thickDataList = (ListView)findViewById(R.id.thickDataList);

        timeDataGraph = (LineChart)findViewById(R.id.timeDataGraph);
        timeDataList = (ListView)findViewById(R.id.timeDataList);

        powerDataGraph = (LineChart)findViewById(R.id.powerDataGraph);
        powerDataList = (ListView)findViewById(R.id.powerDataList);

        timePieChartGrapth = (PieChart)findViewById(R.id.timeScaleGraph);

        weldTimeClock = (TextClock)findViewById(R.id.weldClock);

        mImageview=(ImageView)findViewById(R.id.weld_imageveiw);
//        mImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        weldTimeClock.setFormat24Hour("yyyy-MM-dd hh:mm:ss, EEEE");

        List<Float> thickList = new ArrayList<Float>();
        thickList.add(THICK_RIGHT);
        List<Float> timeList = new ArrayList<Float>();
        timeList.add(TIME_RIGHT);
        List<Float> powerList = new ArrayList<Float>();
        powerList.add(POWER_RIGHT);

        thickMJChart = new BrokeLineChartUtil(thickDataGraph,THICK_DESCRIPT,THICK_CONTENT,xList,thickList);
        timeMJChart = new BrokeLineChartUtil(timeDataGraph,TIME_DESCRIPT,TIME_CONTENT,xList,timeList);
        powerMJChart = new BrokeLineChartUtil(powerDataGraph,POWER_DESCRIPT,POWER_CONTENT,xList,powerList);
        testTimeScalePie();
    }

    //显示点检信息
    class ShowCheckListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            PointCheckFragment pointCheckFragment = new PointCheckFragment();
            pointCheckFragment.setCancelable(true);
            pointCheckFragment.show(getFragmentManager(), "超声波焊接机点检信息");
        }
    }



    /**
     * 根据数据绘制二维图。
     */
    private void dataToGraph(){
        initThickView();
        initTimeView();
        intPowerView();
    }

    //初始化壁厚界面。
    private void initThickView(){

        //展现listview数据
        WeldThick weldThick = AppConfig.weldThick;
        thickValueAdapter = new ThickValueAdapter(weldThick.valueList,this);
        thickDataList.setAdapter(thickValueAdapter);
        thickValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataThick();
        //LineChartUtil.getInstance().initMChart(thickDataGraph,weldThick.minValue,weldThick.maxValue,weldThick.bestValue, weldThick.valueList);
    }

    //初始化焊接时间界面。
    private void initTimeView(){

        //展现listview数据
        WeldTime weldTime = AppConfig.weldTime;
        timeValueAdapter = new TimeValueAdapter(weldTime.valueList,this);
        timeDataList.setAdapter(timeValueAdapter);
        timeValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataTime();
        //LineChartUtil.getInstance().addDataSet(timeDataGraph,weldTime.valueList);
    }

    //初始化能量界面。
    private void intPowerView(){

        //展现listview数据
        WeldPower weldPower = AppConfig.weldPower;
        powerValueAdapter = new PowerValueAdapter(weldPower.valueList,this);
        powerDataList.setAdapter(powerValueAdapter);
        powerValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataPower();
        //LineChartUtil.getInstance().addDataSet(powerDataGraph, weldPower.valueList);
    }

    private void testDataPower(){
        powerMJChart.addXLimitLine(POWER_HEIGH,"焊接能量警戒值",Color.RED);
        powerMJChart.addXLimitLine(POWER_RIGHT, "焊接能量最优值", Color.BLACK);
        powerMJChart.addXLimitLine(POWER_LOW, "焊接能量警戒值", Color.RED);
        final Random radmom = new Random();
//        new Thread()
//        {
//            @Override
//            public void run() {
//
//                for(int i=0;;i++) {
//                    try {
//                        this.sleep(20000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString(KEY, POWER);
//                    Float value = POWER_RIGHT;
//                    if(radmom.nextFloat() > 0.5f){
//                        value = POWER_RIGHT + 200*radmom.nextFloat();
//                    } else {
//                        value = POWER_RIGHT - 200*radmom.nextFloat();
//                    }
//                    bundle.putFloat(VALUE,value);
//                    Message message = new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                }
//            }
//        }.start();
    }

    private void testDataTime(){
        timeMJChart.addXLimitLine(TIME_HEIGH,"焊接时间警戒值",Color.RED);
        timeMJChart.addXLimitLine(TIME_RIGHT,"焊接时间最优值", Color.BLACK);
        timeMJChart.addXLimitLine(TIME_LOW,"焊接时间警戒值",Color.RED);
        final Random radmom = new Random();

//        new Thread()
//        {
//            @Override
//            public void run() {
//
//                for(int i=0;;i++) {
//                    try {
//                        this.sleep(20000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString(KEY, TIME);
//                    Float value = TIME_RIGHT;
//                    if(radmom.nextFloat() > 0.5f){
//                        value = TIME_RIGHT + 0.3f * radmom.nextFloat();
//                    } else {
//                        value = TIME_RIGHT - 0.3f * radmom.nextFloat();
//                    }
//
//                    bundle.putFloat(VALUE,value);
//                    Message message = new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                }
//            }
//        }.start();
    }

    private void testDataThick(){
        thickMJChart.addXLimitLine(THICK_HEIGH,"焊接壁厚警戒值",Color.RED);
        thickMJChart.addXLimitLine(THICK_RIGHT,"焊接壁厚最优值", Color.BLACK);
        thickMJChart.addXLimitLine(THICK_LOW,"焊接壁厚警戒值",Color.RED);
//        final Random radmom = new Random();
//        new Thread()
//        {
//            @Override
//            public void run() {
//
//                for(int i=0;;i++) {
//                    try {
//                        this.sleep(20000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString(KEY, THICK);
//                    Float value = THICK_RIGHT;
//                    if(radmom.nextFloat() > 0.5f){
//                        value = THICK_RIGHT + 0.13f * radmom.nextFloat();
//                    } else {
//                        value = THICK_RIGHT - 0.13f * radmom.nextFloat();
//                    }
//
//                    bundle.putFloat(VALUE,value);
//                    Message message = new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                }
//            }
//        }.start();
    }

    private void testTimeScalePie()
    {
        timeScaleNameList.add("点检");
        timeScaleNameList.add("检修");
        timeScaleNameList.add("停机");
        timeScaleNameList.add("正常使用");

        timeScaleValueList.add(0.3f);
        timeScaleValueList.add(0.5f);
        timeScaleValueList.add(0f);
        timeScaleValueList.add(99.2f);
        PieChartUtil.createPieChart(timePieChartGrapth, "设备使用率", timeScaleValueList, timeScaleNameList);
    }

    @Override
    public void onStop() {
        Log.i(TAG,"onStop");
        super.onStop();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, ComService.class));//开启串口服务
        isRun = false;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            while (isRun) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sb.setLength(0);
            }
        }
    }

    private void getWeldmentData(){
        DaqApp.getApp().getNetService(this).queryWeldmentData().subscribe(new Action1<WeldmentValue>() {
            @Override
            public void call(WeldmentValue weldmentResult) {
                Log.i(TAG,weldmentResult.height);
                Log.i(TAG,weldmentResult.time1);
                Log.i(TAG,weldmentResult.energy);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG,"nidaye");
            }
        });
    }


    public class MyThread1 extends Thread {
        @Override
        public void run() {
            while (isRun) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getWeldmentData();
            }
        }
    }
}
