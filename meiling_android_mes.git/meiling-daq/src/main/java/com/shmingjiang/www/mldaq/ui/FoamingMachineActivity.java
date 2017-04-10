package com.shmingjiang.www.mldaq.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import com.shmingjiang.www.mldaq.AppConfig;
import com.shmingjiang.www.mldaq.R;
import com.shmingjiang.www.mldaq.adapter.Pol1ValueAdapter;
import com.shmingjiang.www.mldaq.bean.Pol1;
import com.shmingjiang.www.mldaq.bean.Pol2;
import com.shmingjiang.www.mldaq.bean.Pol3;
import com.shmingjiang.www.mldaq.bean.Pol4;


import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.transport.security.SecurityMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import app.android.mingjiang.com.mpchartapi.BrokeLineChartUtil;

/**
 * 箱体发泡机数据采集
 * Created by wangdongjia on 2016/7/11.
 */
public class FoamingMachineActivity extends Activity {

    protected final static String TAG = FoamingMachineActivity.class.getSimpleName();

    protected TextView box1Tv, box3Tv, box5Tv, box7Tv, box9Tv, box10Tv,
            box11Tv, box12Tv, box13Tv, box15Tv, box16Tv, box17Tv;

    UaClient client;

    private LineChart pol1Lc, pol2Lc, pol3Lc, pol4Lc;
    private ListView pol1Lv, pol2Lv, pol3Lv, pol4Lv;

    private Pol1ValueAdapter pol1ValueAdapter = null;
    private Pol1ValueAdapter pol2ValueAdapter = null;
    private Pol1ValueAdapter pol3ValueAdapter = null;
    private Pol1ValueAdapter pol4ValueAdapter = null;

    private BrokeLineChartUtil pol1Chart = null;
    private BrokeLineChartUtil pol2Chart = null;
    private BrokeLineChartUtil pol3Chart = null;
    private BrokeLineChartUtil pol4Chart = null;
    private ArrayList<String> xList = new ArrayList<String>
            (Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foaming_machine);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        dataToGraph();
    }

    public void initView() {
        pol1Lc = (LineChart) findViewById(R.id.lc_pol1);
        pol2Lc = (LineChart) findViewById(R.id.lc_pol2);
        pol3Lc = (LineChart) findViewById(R.id.lc_pol3);
        pol4Lc = (LineChart) findViewById(R.id.lc_pol4);
        pol1Lv = (ListView) findViewById(R.id.pol1DataList);
        pol2Lv = (ListView) findViewById(R.id.pol2DataList);
        pol3Lv = (ListView) findViewById(R.id.pol3DataList);
        pol4Lv = (ListView) findViewById(R.id.pol4DataList);
        List<Float> pol1List = new ArrayList<Float>();
        pol1List.add(7000f);
        List<Float> pol2List = new ArrayList<Float>();
        pol2List.add(200f);
        List<Float> pol3List = new ArrayList<Float>();
        pol3List.add(1200f);
        List<Float> pol4List = new ArrayList<Float>();
        pol4List.add(100f);
        pol1Chart = new BrokeLineChartUtil(pol1Lc, "POL流量变化曲线", "POL流量", xList, pol1List);
        pol2Chart = new BrokeLineChartUtil(pol2Lc, "POL实际料温变化曲线", "POL实际料温", xList, pol2List);
        pol3Chart = new BrokeLineChartUtil(pol3Lc, "POL比重/密度变化曲线", "POL比重/密度", xList, pol3List);
        pol4Chart = new BrokeLineChartUtil(pol4Lc, "POL效率实际值变化曲线", "POL效率实际值", xList, pol4List);
        box1Tv = (TextView) findViewById(R.id.tv_box1);
        box3Tv = (TextView) findViewById(R.id.tv_box3);
        box5Tv = (TextView) findViewById(R.id.tv_box5);
        box7Tv = (TextView) findViewById(R.id.tv_box7);
        box9Tv = (TextView) findViewById(R.id.tv_box9);
        box10Tv = (TextView) findViewById(R.id.tv_box10);
        box11Tv = (TextView) findViewById(R.id.tv_box11);
        box12Tv = (TextView) findViewById(R.id.tv_box12);
        box13Tv = (TextView) findViewById(R.id.tv_box13);
        box15Tv = (TextView) findViewById(R.id.tv_box15);
        box16Tv = (TextView) findViewById(R.id.tv_box16);
        box17Tv = (TextView) findViewById(R.id.tv_box17);
    }

    private void initData() {//17
        subsriptionNode("Box.Box01.zero_zero", 0);//POL流量(g/s）
        subsriptionNode("Box.Box01.zero_one", 1);//ISO流量(g/s）
        subsriptionNode("Box.Box01.zero_two", 2);//POL实际料温（℃）
        subsriptionNode("Box.Box01.zero_three", 3);//ISO实际料温（℃）
        subsriptionNode("Box.Box01.zero_four", 4);//POL比重/密度（g/cc）
        subsriptionNode("Box.Box01.zero_five", 5);//ISO比重/密度（g/cc）
        subsriptionNode("Box.Box01.zero_six", 6);//POL效率实际值（%）
        subsriptionNode("Box.Box01.zero_seven", 7);//ISO效率实际值（%）
        subsriptionNode("Box.Box01.zero_eight", 8);//料比
        subsriptionNode("Box.Box01.zero_nine", 9);//料重（g）
        subsriptionNode("Box.Box01.zero_ten", 100);//操作模式
        subsriptionNode("Box.Box01.one_zero", 10);//液压阀
        subsriptionNode("Box.Box01.one_one", 11);//POL搅拌泵
        subsriptionNode("Box.Box01.one_two", 12);//ISO搅拌泵
        subsriptionNode("Box.Box01.one_three", 13);//报警
        subsriptionNode("Box.Box01.one_four", 14);//防火阀
        subsriptionNode("Box.Box01.one_five", 15);//高压循环最长时间

    }

    private void subsriptionNode(final String nodeId, final int offset) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        client = new UaClient("opc.tcp://10.18.113.200:52520/OPCUA/MeiLingOPCUAServer");
                        client.setSecurityMode(SecurityMode.NONE);
                        client.setTimeout(1000);
                        client.connect();
                        NodeId id = new NodeId(2, nodeId);
                        // 读变量值
                        DataValue value = client.readValue(id);
                        System.out.println(value.getValue());
                        Bundle bundle = new Bundle();
                        bundle.putString(KEY, String.valueOf(value.getValue()));
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = offset;
                        handler.sendMessage(message);
                        // 订阅变量变化
                        Subscription subscription = new Subscription();
                        MonitoredDataItem item = new MonitoredDataItem(id, Attributes.Value,
                                MonitoringMode.Reporting, subscription.getPublishingInterval());
                        item.setDataChangeListener(new MonitoredDataItemListener() {
                            /**
                             * @param monitoredDataItem
                             * monitoredDataItem存储着节点的相关信息
                             * @param dataValue
                             * dataValue 上一次的value
                             * @param dataValue1
                             * 当前的value
                             */
                            @Override
                            public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1) {
                                Log.i("--old--DataValue----", dataValue.getValue() + "");
                                Log.i("--new--DataValue1----", dataValue1.getValue() + "");
                                Bundle bundle = new Bundle();
                                bundle.putString(KEY, String.valueOf(dataValue1.getValue()));
                                Message message = new Message();
                                message.setData(bundle);
                                message.what = offset;
                                handler.sendMessage(message);
                            }
                        });
                        subscription.addItem(item);
                        client.addSubscription(subscription);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //数据处理、UI更新
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box1Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol1Chart.addEntry("1", floatValue);
                    break;
                }
                case 1: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box1Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol1Chart.addEntry("1", floatValue);
                    break;
                }
                case 2: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box3Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol2Chart.addEntry("1", floatValue);
                    break;
                }
                case 3: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box3Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol2Chart.addEntry("1", floatValue);
                    break;
                }
                case 4: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box5Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol3Chart.addEntry("1", floatValue);
                    break;
                }
                case 5: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box5Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol3Chart.addEntry("1", floatValue);
                    break;
                }
                case 6: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box7Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol4Chart.addEntry("1", floatValue);
                    break;
                }
                case 7: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box7Tv.setText(value);
                    int intValue = Integer.parseInt(value);
                    Float floatValue = (float) intValue;
                    pol4Chart.addEntry("1", floatValue);
                    break;
                }
                case 8: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box9Tv.setText(value);
                    break;
                }
                case 9: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box10Tv.setText(value);
                    break;
                }
                case 100: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box11Tv.setText(value);
                    break;
                }
                case 10: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box12Tv.setText(value);
                    break;
                }
                case 11: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box13Tv.setText(value);
                    break;
                }
                case 12: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box13Tv.setText(value);
                    break;
                }
                case 13: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box15Tv.setText(value);
                    break;
                }
                case 14: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box16Tv.setText(value);
                    break;
                }
                case 15: {
                    Bundle buldler = msg.getData();
                    String value = buldler.getString(KEY);
                    Log.i(TAG, value);
                    box17Tv.setText(value);
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 根据数据绘制二维图。
     */
    private void dataToGraph() {
        initPol1View();
        initPol2View();
        initPol3View();
        initPol4View();
    }

    //初始化POL流量(g/s）
    private void initPol1View() {
        //展现listview数据
        Pol1 pol1 = AppConfig.pol1;
        pol1ValueAdapter = new Pol1ValueAdapter(pol1.valueList, this);
        pol1Lv.setAdapter(pol1ValueAdapter);
        pol1ValueAdapter.notifyDataSetChanged();
        //绘制二维图
        testDataPol1();
    }

    //初始化POL实际料温（℃）
    private void initPol2View() {
        //展现listview数据
        Pol2 pol2 = AppConfig.pol2;
        pol2ValueAdapter = new Pol1ValueAdapter(pol2.valueList, this);
        pol2Lv.setAdapter(pol2ValueAdapter);
        pol2ValueAdapter.notifyDataSetChanged();
        //绘制二维图
        testDataPol2();
        //LineChartUtil.getInstance().addDataSet(timeDataGraph,weldTime.valueList);
    }

    //初始化POL比重/密度（g/cc）
    private void initPol3View() {
        //展现listview数据
        Pol3 pol3 = AppConfig.pol3;
        pol3ValueAdapter = new Pol1ValueAdapter(pol3.valueList, this);
        pol3Lv.setAdapter(pol3ValueAdapter);
        pol3ValueAdapter.notifyDataSetChanged();
        //绘制二维图
        testDataPol3();
        //LineChartUtil.getInstance().addDataSet(powerDataGraph, weldPower.valueList);
    }

    //初始化POL效率实际值（%）
    private void initPol4View() {
        //展现listview数据
        Pol4 pol4 = AppConfig.pol4;
        pol4ValueAdapter = new Pol1ValueAdapter(pol4.valueList, this);
        pol4Lv.setAdapter(pol4ValueAdapter);
        pol4ValueAdapter.notifyDataSetChanged();
        //绘制二维图
        testDataPol4();
        //LineChartUtil.getInstance().addDataSet(powerDataGraph, weldPower.valueList);
    }

    private void testDataPol4() {
        pol4Chart.addXLimitLine(0.93f, "效率警戒值", Color.RED);
        pol4Chart.addXLimitLine(0.93f, "效率最优值", Color.BLACK);
        pol4Chart.addXLimitLine(0.93f, "效率警戒值", Color.RED);
    }

    private void testDataPol3() {
        pol3Chart.addXLimitLine(0.93f, "比重/密度警戒值", Color.RED);
        pol3Chart.addXLimitLine(0.93f, "比重/密度最优值", Color.BLACK);
        pol3Chart.addXLimitLine(0.93f, "比重/密度警戒值", Color.RED);
    }

    private void testDataPol2() {
        pol2Chart.addXLimitLine(0.93f, "料温警戒值", Color.RED);
        pol2Chart.addXLimitLine(0.93f, "料温最优值", Color.BLACK);
        pol2Chart.addXLimitLine(0.93f, "料温警戒值", Color.RED);
    }

    //动态更新绘图数据。
    private static final String KEY = "KEY";
    private static final String POWER = "power";
    private static final String TIME = "time";
    private static final String THICK = "thick";
    private static final String VALUE = "value";

    private void testDataPol1() {
        pol1Chart.addXLimitLine(0.93f, "流量警戒值", Color.RED);
        pol1Chart.addXLimitLine(0.93f, "流量最优值", Color.BLACK);
        pol1Chart.addXLimitLine(0.93f, "流量警戒值", Color.RED);
    }

//    private Handler handler1 = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Bundle buldler = msg.getData();
//            float value = buldler.getFloat(VALUE);
//            String key = buldler.getString(KEY);
//            int intValue = (int) value;
//            Float floatValue = (float) intValue;
//            pol1Chart.addEntry("1", floatValue);
//            pol2Chart.addEntry("1", floatValue);
//            pol3Chart.addEntry("1", floatValue);
//            pol4Chart.addEntry("1", floatValue);
////            if(key.equals(POWER)){
////                int intValue = (int)value;
////                Float floatValue = (float)intValue;
////                powerMJChart.addEntry("1", floatValue);
////                weldPowerView.setText(intValue+"");
////            }else if(key.equals(TIME)){
////                float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
////                timeMJChart.addEntry("1", fValue);
////                weldTimeView.setText(fValue + "");
////            }else if(key.equals(THICK)){
////                float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
////                thickMJChart.addEntry("1", fValue);
////                weldThickView.setText(fValue+"");
////            }
//        }
//    };


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
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

