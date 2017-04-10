package com.shmingjiang.www.mldaq.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import com.shmingjiang.www.mldaq.R;
import com.shmingjiang.www.mldaq.event.ComEvent;
import com.shmingjiang.www.mldaq.service.ComService;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.transport.security.SecurityMode;


import de.greenrobot.event.EventBus;


/**
 * 线体总体状态
 */
public class LineOldActivity extends Activity {
    protected static final String TAG = LineOldActivity.class.getSimpleName();
    private long exitTime = 0;
    UaClient client;
    protected Button Vaccum0Btn,Vaccum1Btn,Vaccum2Btn;
    protected Button Vaccum10Btn,Vaccum11Btn,Vaccum12Btn;
    protected Button Test0Btn,Test1Btn,Test2Btn,Test3Btn,Test4Btn,Test5Btn,Test6Btn,Test7Btn;
    protected Button Assembly0Btn;
    protected Button Robot10Btn,Robot11Btn,Robot20Btn,Robot21Btn,Robot30Btn,Robot31Btn;
    protected Button Wash0Btn,Wash1Btn,Wash2Btn,Wash3Btn,Wash4Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_old);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    public void initView() {
//        Vaccum0Btn = (Button)findViewById(R.id.btn_vaccum0);
//        Vaccum1Btn = (Button)findViewById(R.id.btn_vaccum1);
//        Vaccum2Btn = (Button)findViewById(R.id.btn_vaccum2);
//
//        //自动翻转机
//        Vaccum10Btn = (Button)findViewById(R.id.btn_vaccum10);
//        Vaccum11Btn = (Button)findViewById(R.id.btn_vaccum11);
//        Vaccum12Btn = (Button)findViewById(R.id.btn_vaccum12);
//
//        Test0Btn = (Button)findViewById(R.id.btn_test0);
//        Test1Btn = (Button)findViewById(R.id.btn_test1);
//        Test2Btn = (Button)findViewById(R.id.btn_test2);
//        Test3Btn = (Button)view.findViewById(R.id.btn_test3);
//        Test4Btn = (Button)view.findViewById(R.id.btn_test4);
//        Test5Btn = (Button)view.findViewById(R.id.btn_test5);
//        Test6Btn = (Button)view.findViewById(R.id.btn_test6);
//        Test7Btn = (Button)view.findViewById(R.id.btn_test7);
//
//        Assembly0Btn = (Button)view.findViewById(R.id.btn_assembly0);
//
//        Robot10Btn = (Button)view.findViewById(R.id.btn_robot10);
//        Robot11Btn = (Button)view.findViewById(R.id.btn_robot11);
//        Robot20Btn = (Button)view.findViewById(R.id.btn_robot20);
//        Robot21Btn = (Button)view.findViewById(R.id.btn_robot21);
//        Robot30Btn = (Button)view.findViewById(R.id.btn_robot30);
//        Robot31Btn = (Button)view.findViewById(R.id.btn_robot31);
//
//        Wash0Btn = (Button)view.findViewById(R.id.btn_wash0);
//        Wash1Btn = (Button)view.findViewById(R.id.btn_wash1);
//        Wash2Btn = (Button)view.findViewById(R.id.btn_wash2);
//        Wash3Btn = (Button)view.findViewById(R.id.btn_wash3);
//        Wash4Btn = (Button)view.findViewById(R.id.btn_wash4);
    }

    public void initData() {
        //订阅所有节点
        subsriptionNode("Assembly.Assembly01.zero_zero",10);

        subsriptionNode("Robot.Robot01.zero_zero",210);
        subsriptionNode("Robot.Robot01.zero_one",211);
        subsriptionNode("Robot.Robot02.zero_zero",220);
        subsriptionNode("Robot.Robot02.zero_one",221);
        subsriptionNode("Robot.Robot03.zero_zero",230);
        subsriptionNode("Robot.Robot03.zero_one",231);

        subsriptionNode("Test.Test01.zero_zero",30);
        subsriptionNode("Test.Test01.zero_one",31);
        subsriptionNode("Test.Test01.zero_two",32);
        subsriptionNode("Test.Test01.zero_three",33);
        subsriptionNode("Test.Test01.zero_four",34);
        subsriptionNode("Test.Test01.zero_five",35);
        subsriptionNode("Test.Test01.zero_six",36);
        subsriptionNode("Test.Test01.zero_seven",37);

        subsriptionNode("Vacuum.Vacuum01.zero_zero",40);
        subsriptionNode("Vacuum.Vacuum01.zero_one",41);
        subsriptionNode("Vacuum.Vacuum01.zero_two",42);

        //自动翻转机
        subsriptionNode("Vacuum.Vacuum02.zero_zero",60);
        subsriptionNode("Vacuum.Vacuum02.zero_one",61);
        subsriptionNode("Vacuum.Vacuum02.zero_two",62);

        subsriptionNode("Wash.Wash01.zero_zero",50);
        subsriptionNode("Wash.Wash01.zero_one",51);
        subsriptionNode("Wash.Wash01.zero_two",52);
        subsriptionNode("Wash.Wash01.zero_three",53);
        subsriptionNode("Wash.Wash01.zero_four",54);
    }


    private void subsriptionNode(final String nodeId,final int offset) {
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
                        Message msg = new Message();
                        msg.obj = value.getValue();
                        msg.what = offset;
                        handler.sendMessage(msg);
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
                                System.out.println(dataValue1.getValue());
                                Message msg = new Message();
                                msg.obj = dataValue1.getValue();
                                msg.what = offset;
                                handler.sendMessage(msg);
                            }
                        });
                        subscription.addItem(item);
                        client.addSubscription(subscription);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    //数据处理、UI更新
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //Assembly
                case 10: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Assembly0Btn.setBackgroundResource(R.color.green);
                    }else {
                        Assembly0Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 210: {//运行 Y20
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot10Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot10Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 211: {//提升机A急停 X7
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot11Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot11Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 220: {//运行 Y13
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot20Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot20Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 221: {//急停 X11
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot21Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot21Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                //19动态测试
                case 230: {//运行 Y14
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot30Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot30Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 231: {//急停 X20
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Robot31Btn.setBackgroundResource(R.color.green);
                    }else {
                        Robot31Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                //动态进
                case 30: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test0Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test0Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 31: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test1Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test1Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 32: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test2Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test2Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 33: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test3Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test3Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 34: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test4Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test4Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 35: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test5Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test5Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 36: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test6Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test6Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 37: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Test7Btn.setBackgroundResource(R.color.green);
                    }else {
                        Test7Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                //抽真空进线
                case 40: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum0Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum0Btn.setBackgroundResource(R.color.orange);
                    }

                    break;
                }
                case 41: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum1Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum1Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 42: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum2Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum2Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                //21清洗线
                case 50: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Wash0Btn.setBackgroundResource(R.color.green);
                    }else {
                        Wash0Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                case 51: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Wash1Btn.setBackgroundResource(R.color.green);
                    }else {
                        Wash1Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 52: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Wash2Btn.setBackgroundResource(R.color.green);
                    }else {
                        Wash2Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 53: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Wash3Btn.setBackgroundResource(R.color.green);
                    }else {
                        Wash3Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 54: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Wash4Btn.setBackgroundResource(R.color.green);
                    }else {
                        Wash4Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 60:{
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum10Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum10Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 61:{
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum11Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum11Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }case 62:{
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Vaccum12Btn.setBackgroundResource(R.color.green);
                    }else {
                        Vaccum12Btn.setBackgroundResource(R.color.orange);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 为了得到传回的数据，必须重写onActivityResult方法
     * @param requestCode 请求码，即调用startActivityForResult()传递过去的值
     * @param resultCode  结果码，结果码用于标识返回数据来自哪个新Activity
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:

            case 2:
        }
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_SCHUNK) {
            String code = event.getMessage();
            Log.i(TAG, code);
            //匹配服务器数据
//            showWaitDialog(R.string.progress_login);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, ComService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 按两次返回键退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}