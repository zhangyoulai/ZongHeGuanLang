package com.shmingjiang.www.mldaq.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import com.shmingjiang.www.mldaq.R;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.transport.security.SecurityMode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 线体状态
 * Created by wangdongjia on 2016/7/13.
 */
public class LineActivity extends Activity{
    protected final String TAG = LineActivity.class.getSimpleName();
    UaClient client;//声明opcua client对象
    private volatile boolean isRun = true;  //是否结束线程
    protected ImageView Rotate12Iv,Rotate8Iv,Box8Iv,Box12Iv,Base8Iv,Base12Iv,VacuumIv,
                OfflineAIv,BlineIv,OfflineBIv,DoutIv,DinIv,WashlineIv,CaseboxIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        initView();
        initData();
    }

    private void initData() {
        //12-自动翻转
        subsriptionNode("Vacuum.Vacuum02.zero_zero",60);//翻身
//        subsriptionNode("Vacuum.Vacuum02.zero_one",61);//转角
//        subsriptionNode("Vacuum.Vacuum02.zero_two",62);//故障

        //8-箱发
//        subsriptionNode("Foam.Foam01.",91);

        //8-自动装底座
        subsriptionNode("Base.Base01.zero_two",81);//运行指示灯

        //12-自动装底座
        subsriptionNode("Base.Base02.zero_two",82);//运行指示灯

        //抽真空进线
//        subsriptionNode("Vacuum.Vacuum01.zero_zero",40);
//        subsriptionNode("Vacuum.Vacuum01.zero_one",41);
        subsriptionNode("Vacuum.Vacuum01.zero_two",42);

        //灌注机

        //超声波焊接机

        //总装外线提升机
//        subsriptionNode("Robot.Robot01.zero_zero",210);
        subsriptionNode("Robot.Robot01.zero_one",211);

        //总装内线提升机
//        subsriptionNode("Robot.Robot02.zero_zero",220);
        subsriptionNode("Robot.Robot02.zero_one",221);

        //总装内线
        subsriptionNode("Assembly.Assembly01.zero_zero",10);

        //动态进
//        subsriptionNode("Test.Test01.zero_zero",30);
        subsriptionNode("Test.Test01.zero_one",31);
//        subsriptionNode("Test.Test01.zero_two",32);
//        subsriptionNode("Test.Test01.zero_three",33);
//        subsriptionNode("Test.Test01.zero_four",34);
//        subsriptionNode("Test.Test01.zero_five",35);
//        subsriptionNode("Test.Test01.zero_six",36);
//        subsriptionNode("Test.Test01.zero_seven",37);

        //测试房

        //动态出
//        subsriptionNode("Robot.Robot03.zero_zero",230);
        subsriptionNode("Robot.Robot03.zero_one",231);

        //清洗线
//        subsriptionNode("Wash.Wash01.zero_zero",50);
        subsriptionNode("Wash.Wash01.zero_one",51);
//        subsriptionNode("Wash.Wash01.zero_two",52);
//        subsriptionNode("Wash.Wash01.zero_three",53);
//        subsriptionNode("Wash.Wash01.zero_four",54);

        //套箱

        //三项性能

        //五项性能
    }

    private void initView() {
        Rotate12Iv = (ImageView)findViewById(R.id.iv_rotate12);
        OfflineAIv = (ImageView)findViewById(R.id.iv_offlinea);
        Box8Iv = (ImageView)findViewById(R.id.iv_box8);
        VacuumIv = (ImageView)findViewById(R.id.iv_vacuum);
        Base8Iv = (ImageView)findViewById(R.id.iv_base8);
        Base12Iv = (ImageView)findViewById(R.id.iv_base12);
        BlineIv = (ImageView)findViewById(R.id.iv_bline);
        OfflineBIv = (ImageView)findViewById(R.id.iv_offlineb);
        DinIv = (ImageView)findViewById(R.id.iv_din);
        DoutIv = (ImageView)findViewById(R.id.iv_dout);
        WashlineIv = (ImageView)findViewById(R.id.iv_washline);
        CaseboxIv = (ImageView)findViewById(R.id.iv_casebox);
    }

    //数据处理、UI更新
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 60: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        Rotate12Iv.setImageResource(R.drawable.rotate_normal);
                    }else {
                        Rotate12Iv.setImageResource(R.drawable.rotate_stop);
                    }
                    break;
                }
                case 91:{
                    if (msg.obj.toString().equals("1")){
                        Box8Iv.setImageResource(R.drawable.box_normal);
                    }else {
                        Box8Iv.setImageResource(R.drawable.box_stop);
                    }
                }
                case 81:{
                    if (msg.obj.toString().equals("1")){
                        Base8Iv.setImageResource(R.drawable.base_stop);
                    }else {
                        Base8Iv.setImageResource(R.drawable.base_normal);
                    }
                }
                case 82:{
                    if (msg.obj.toString().equals("1")){
                        Base12Iv.setImageResource(R.drawable.base_stop);
                    }else {
                        Base12Iv.setImageResource(R.drawable.base_normal);
                    }
                }
                case 42: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        VacuumIv.setImageResource(R.drawable.vacuum_normal);
                    }else {
                        VacuumIv.setImageResource(R.drawable.vacuum_stop);
                    }
                    break;
                }
                case 211: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        OfflineAIv.setImageResource(R.drawable.offline_normal);
                    }else {
                        OfflineAIv.setImageResource(R.drawable.offline_stop);
                    }
                    break;
                }
                case 221: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        OfflineBIv.setImageResource(R.drawable.offline_normal);
                    }else {
                        OfflineBIv.setImageResource(R.drawable.offline_stop);
                    }
                    break;
                }
                case 10: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        BlineIv.setImageResource(R.drawable.bline_stop);
                    }else {
                        BlineIv.setImageResource(R.drawable.bline_normal);
                    }
                    break;
                }
                case 31: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        DinIv.setImageResource(R.drawable.din_normal);
                    }else {
                        DinIv.setImageResource(R.drawable.din_stop);
                    }
                    break;
                }
                case 231: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        DoutIv.setImageResource(R.drawable.dout_normal);
                    }else {
                        DoutIv.setImageResource(R.drawable.dout_stop);
                    }
                    break;
                }
                case 51: {
                    Log.i(TAG,msg.obj.toString());
                    if (msg.obj.toString().equals("1")){
                        WashlineIv.setImageResource(R.drawable.washline_normal);
                    }else {
                        WashlineIv.setImageResource(R.drawable.washline_stop);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        Log.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG,"onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG,"onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
        finish();
    }

    /**
     * 判断服务器连接状态
     */
    private void pingIp() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    boolean status_connect_server = InetAddress.getByName("10.18.113.200").isReachable(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 20000);//1000ms 1s
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
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
