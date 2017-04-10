package com.mingjiang.android.app.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mingjiang.android.app.R;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.MethodCallStatusException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.transport.security.SecurityMode;

import de.greenrobot.event.EventBus;


/**
 * Created by huaqiang
 * on 2016-01-06.
 */
public class OPCService extends Service {

    protected final static String TAG = OPCService.class.getSimpleName();

    private int PORT = 4881;//端口
    private String CardIP = "192.168.150.2";//服务器ip
    private Thread mConnectThread;
    private boolean mConnected = false;
    UaClient mClient;

    public MyBroadcastReciver myBroadcastReciver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        connectIpThread();

        //新建过滤器
        IntentFilter intentFilter = new IntentFilter();
        //添加过滤器
        intentFilter.addAction("com.mj.action.opc.opc_agvid");
        intentFilter.addAction("com.mj.action.opc.opc_programNo");
        intentFilter.addAction("com.mj.action.opc.opc_stopAGV");
        //多次调用会接收多次
        myBroadcastReciver = new MyBroadcastReciver();
        registerReceiver(myBroadcastReciver, intentFilter);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        //注销广播
        unregisterReceiver(myBroadcastReciver);
        super.onDestroy();
    }

    private void connectIpThread() {
        if (!mConnected) {
            mConnectThread = new Thread() {
                @Override
                public void run() {
                    connectIP();
                }
            };
            mConnectThread.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mConnectThread != null && !mConnectThread.isAlive()) {
            connectIpThread();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化
     */
    private void connectIP() {
        try {
            mClient = new UaClient("opc.tcp://192.168.150.2:4881");
            //mClient = new UaClient("opc.tcp://192.168.88.8:4881");
            //UaClient client = new UaClient("opc.tcp://192.168.88.177:53530/OPCUA/SimulationServer");
            mClient.setSecurityMode(SecurityMode.NONE);
            mClient.setTimeout(10 * 1000);
            mClient.connect();
            if (mClient.isConnected()) {
                Log.i(TAG, "OPCService SUCCESS");
                //nowTime(getNjqIP());
                mConnected = true;
            }
        } catch (Exception e) {
            mClient.disconnect();
            Log.i(TAG, "OPCService error");
            try {
                Thread.sleep(10 * 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            connectIP();
        }
    }

    public void startAGV(int agvId) {
        try {
            Variant inputs = new Variant(agvId);
            Variant[] outputs = new Variant[0];
            outputs = mClient.call(new NodeId(1, "MJAGV"), new NodeId(1, "MJAGV.Start"), inputs);
            Log.e("whq --- outputs : ", outputs[0].booleanValue() + "");
        } catch (ServiceException | MethodCallStatusException e) {
            e.printStackTrace();
        }
    }

    public void stopAGV(int agvId) {
        try {
            Variant inputs = new Variant(agvId);
            Variant[] outputs = new Variant[0];
            outputs = mClient.call(new NodeId(1, "MJAGV"), new NodeId(1, "MJAGV.Stop"), inputs);
            Log.e("whq --- outputs : ", outputs[0].booleanValue() + "");
        } catch (ServiceException | MethodCallStatusException e) {
            e.printStackTrace();
        }
    }

    public void setNJQParam(int njqNo, String njqIp) {
        try {
            Variant[] inPut = new Variant[1];
            //inPut[0] = new Variant(6);
            inPut[0] = new Variant(njqNo);
            //CallMethodRequest input4 =new CallMethodRequest(new NodeId(1,"Atlas"),new NodeId(1,"Atlas.SetParam_At56"),in);
            Variant[] outputsNJQ = new Variant[0];
            outputsNJQ = mClient.call(new NodeId(1, "Atlas"), new NodeId(1, "Atlas.SetParam_At" + njqIp), inPut);
            Log.i(TAG,"whq --- outputsNJQ : " + outputsNJQ[0].booleanValue());
            if (!outputsNJQ[0].booleanValue()) {
                //setNJQParam(1, getNjqIP());
                Toast.makeText(this, "请手动修改程序号！", Toast.LENGTH_LONG).show();
            }
        } catch (ServiceException | MethodCallStatusException e) {
            e.printStackTrace();
        }
    }

    //实时读取扭矩枪打点信息---订阅节点
    public void nowTime(String njqIp) {
        //读取变量值
        try {
            NodeId id = new NodeId(1, "Atlas.At_" + njqIp);
            DataValue value = mClient.readValue(id);
            //EventBus.getDefault().post(new OPCEvent(value.getValue().toString(), OPCEvent.NJQ_MSG));//com.mj.action.opc.msg_top
            //通过广播发送扭矩枪打点变化值，进行界面操作
            Intent intent = new Intent();
            intent.setAction("com.mj.action.opc.msg_top");
            // 要发送的内容
            intent.putExtra("msg_top", value.getValue().toString());
            // 发送 一个无序广播
            OPCService.this.sendBroadcast(intent);
            // 订阅变量变化
            Subscription subscription = new Subscription();
            MonitoredDataItem item = new MonitoredDataItem(id, Attributes.Value,
                    MonitoringMode.Reporting, subscription.getPublishingInterval());
            item.setDataChangeListener(new MonitoredDataItemListener() {
                @Override
                public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue newValue) {
//                    L.e("whq --atlas value:" + newValue);
                    Log.i(TAG, newValue.toString());
                    //EventBus.getDefault().post(new OPCEvent(newValue.getValue().toString(), OPCEvent.MSG));

                    //通过广播发送扭矩枪打点变化值，进行界面操作
                    Intent intent = new Intent();
                    intent.setAction("com.mj.action.opc.opc_msg");
                    // 要发送的内容
                    intent.putExtra("opc_msg", newValue.getValue().toString());
                    // 发送 一个无序广播
                    OPCService.this.sendBroadcast(intent);
                    Log.i(TAG, "whq --atlas value test:" + newValue);
                }
            });
            subscription.addItem(item);
            mClient.addSubscription(subscription);
        } catch (ServiceException | StatusException e) {
            e.printStackTrace();
        }
    }

//    public void onEventMainThread(OPCEvent opcEvent) {
//        if (opcEvent.getActionType() == OPCEvent.AGV_START) {
//            Log.i(TAG, "whq --- AGVRUNGING _ start : " + opcEvent.getNumber());
//            startAGV(opcEvent.getNumber());
//        }
//        if (opcEvent.getActionType() == OPCEvent.AGV_STOP) {
//            Log.i(TAG, "whq --- AGVRUNGING _ stop : " + opcEvent.getNumber());
//            stopAGV(opcEvent.getNumber());
//        }
//        if (opcEvent.getActionType() == OPCEvent.NJQ_WRITE) {
//            Log.i(TAG, "whq --- NJQ_WRITE _ SetParam : " + opcEvent.getMessage());
//            setNJQParam(Integer.parseInt(opcEvent.getMessage()), getNjqIP());
//        }
//    }

    //接收广播消息，进行操作
    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.mj.action.opc.opc_programNo")) {
                String opc_programNo = intent.getStringExtra("opc_programNo");
                // 在控制台显示接收到的广播内容
                Log.i(TAG, "whq --- opc_programNo : " + opc_programNo);
                if (mClient.isConnected()) {
                    setNJQParam(Integer.parseInt(opc_programNo), getNjqIP());
                }
            }
            if (action.equals("com.mj.action.opc.opc_agvid")) {
                String opc_agvid = intent.getStringExtra("opc_agvid");
                Log.i(TAG, "whq --- opc_agvid : " + opc_agvid);
                if (mClient.isConnected()) {
                    Log.i(TAG, "whq --- agvid");
                    startAGV(getAgvId(opc_agvid));
                    Log.i(TAG, "whq --- agvid end");
                }
            }
            if (action.equals("com.mj.action.opc.opc_stopAGV")) {
                String action_stopAGV = intent.getStringExtra("action_stopAGV");
                Log.i(TAG, "whq --- action_stopAGV : " + action_stopAGV);
                if (mClient.isConnected()) {
                    Log.i(TAG, "whq --- agvid");
                    stopAGV(getAgvId(action_stopAGV));
                    Log.i(TAG, "whq --- agvid end");
                }
            }
        }

    }

    //根据工位获取要定节点的ip
    public String getNjqIP() {
        String stationId = "";
        String njqIP = "";
//        if (stationId.equals(getResources().getString(R.string.station01))) {
//            njqIP = "93";
//        } else if (stationId.equals(getResources().getString(R.string.station02))) {
//            njqIP = "94";
//        } /*else if (stationId.equals(getResources().getString(R.string.station03))) {
//            njqIP = "93";
//        }*/ else if (stationId.equals(getResources().getString(R.string.station04))) {
//            njqIP = "96";
//        } else if (stationId.equals(getResources().getString(R.string.station05))) {
//            njqIP = "98";
//        } else if (stationId.equals(getResources().getString(R.string.station06))) {
//            njqIP = "102";
//        } else if (stationId.equals(getResources().getString(R.string.station07))) {
//            njqIP = "103";
//        } else if (stationId.equals(getResources().getString(R.string.station08))) {
//            njqIP = "105";
//        } else if (stationId.equals(getResources().getString(R.string.station09))) {
//            njqIP = "106";
//        } else if (stationId.equals(getResources().getString(R.string.station10))) {
//            njqIP = "108";
//        } else if (stationId.equals(getResources().getString(R.string.station11))) {
//            njqIP = "109";
//        } else if (stationId.equals(getResources().getString(R.string.station12))) {
//            njqIP = "110";
//        } else if (stationId.equals(getResources().getString(R.string.station13))) {
//            njqIP = "111";
//        } else if (stationId.equals(getResources().getString(R.string.offline01))) {
//            njqIP = "90";
//        } else if (stationId.equals(getResources().getString(R.string.offline02))) {
//            njqIP = "91";
//        } else if (stationId.equals(getResources().getString(R.string.offline03))) {
//            njqIP = "92";
//        } else if (stationId.equals(getResources().getString(R.string.offline04))) {
//            njqIP = "95";
//        } else if (stationId.equals(getResources().getString(R.string.offline05))) {
//            njqIP = "97";
//        } else if (stationId.equals(getResources().getString(R.string.offline06))) {
//            njqIP = "99";
//        } else if (stationId.equals(getResources().getString(R.string.offline07))) {
//            njqIP = "100";
//        } else if (stationId.equals(getResources().getString(R.string.offline08))) {
//            njqIP = "101";
//        } else if (stationId.equals(getResources().getString(R.string.station14))) {
//            njqIP = "107";
//        } else if (stationId.equals(getResources().getString(R.string.station_rework))) {
//            njqIP = "112";
//        }
        return njqIP;
    }

    public int getAgvId(String agvNumber) {
        int agvId = 0;
//        if (agvNumber.equals(getResources().getText(R.string.agv01))) {
//            agvId = 1;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv02))) {
//            agvId = 2;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv03))) {
//            agvId = 3;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv04))) {
//            agvId = 4;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv05))) {
//            agvId = 5;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv06))) {
//            agvId = 6;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv07))) {
//            agvId = 7;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv08))) {
//            agvId = 8;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv09))) {
//            agvId = 9;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv10))) {
//            agvId = 10;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv11))) {
//            agvId = 11;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv12))) {
//            agvId = 12;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv13))) {
//            agvId = 13;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv14))) {
//            agvId = 14;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv15))) {
//            agvId = 15;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv16))) {
//            agvId = 16;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv17))) {
//            agvId = 17;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv18))) {
//            agvId = 18;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv19))) {
//            agvId = 19;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv20))) {
//            agvId = 20;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv21))) {
//            agvId = 21;
//        } else if (agvNumber.equals(getResources().getText(R.string.agv22))) {
//            agvId = 22;
//        }
        return agvId;
    }
}
