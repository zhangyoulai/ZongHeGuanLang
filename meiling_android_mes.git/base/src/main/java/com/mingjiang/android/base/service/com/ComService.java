package com.mingjiang.android.base.service.com;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mingjiang.android.base.R;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;

import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.ComBean;
import android_serialport_api.SerialHelper;
import de.greenrobot.event.EventBus;

/**
 * 备注：读取串口数据服务。
 * 作者：wangzs on 2016/2/24 19:22
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComService extends Service{

    private final static String TAG = "ComService";
    private final static String STR_DEV_ONE = "/dev/ttyS1";
    private final static String STR_DEV_TWO = "/dev/ttyS2";
    private final static String STR_DEV_THREE = "/dev/ttyS3";
    private final static String STR_DEV_FOUR = "/dev/ttyS4";
    SerialControl comA, comB,comC, comD;
    private static int activityCode = 0;
    private static String baudCodeOne="9600";
    private static String baudCodeTwo="9600";
    private static String baudCodeThree="9600";
    private static String baudCodeFour="9600";

    @Override
    public int onStartCommand(Intent intent, int flags,int startId) {
        Log.i(TAG,"onStartCommand");
        //TODO 出现过空指针异常
        if (intent!=null){
            activityCode = intent.getIntExtra(Constants.ACTIVITY_CODE,0);
            baudCodeOne=intent.getStringExtra(Constants.BAUD_CODE_ONE);
            baudCodeTwo=intent.getStringExtra(Constants.BAUD_CODE_TWO);
            baudCodeThree=intent.getStringExtra(Constants.BAUD_CODE_THREE);
            baudCodeFour=intent.getStringExtra(Constants.BAUD_CODE_FOUR);
        }
        initData();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onEventBackgroundThread(ComEvent event) {
        if (ComEvent.ACTION_SEND_PCI == event.getActionType()) {
            Log.i(TAG,"向板卡发送信号");
            comC.sendTxt(event.getMessage());
        }else if (ComEvent.ACTION_SEND_GUN == event.getActionType()){
            Log.i(TAG,"e or d");
            comB.sendTxt(event.getMessage());
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        super.onCreate();
        EventBus.getDefault().register(this);

    }
    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void initData() {
        comA = new SerialControl();
        comB = new SerialControl();
        comC = new SerialControl();
        comD = new SerialControl();
        Log.i(TAG,baudCodeTwo);
        comA.setPort(STR_DEV_ONE);
        comB.setPort(STR_DEV_TWO);
        comC.setPort(STR_DEV_THREE);
        comD.setPort(STR_DEV_FOUR);
        comA.setBaudRate(baudCodeOne);
        comB.setBaudRate(baudCodeTwo);
        comC.setBaudRate(baudCodeThree);
        comD.setBaudRate(baudCodeFour);
        openComPort(comA);
        openComPort(comB);
        openComPort(comC);
        openComPort(comD);
    }

    private class SerialControl extends SerialHelper {
        public SerialControl(){
        }

        @Override
        protected void onDataReceived(final ComBean comRecData)
        {
            String scanData=new String(comRecData.bRec);
            Log.e(TAG, "received data : " + scanData);
            if (scanData.length()<50){
                if(ComEvent.ACTION_POST_SCAN == activityCode){//岗位扫描
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_POST_SCAN));
                }else if(ComEvent.ACTION_USER_SCAN == activityCode){//用户扫描
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_USER_SCAN));
                }else if(ComEvent.ACTION_MATERIAL_USER_SCAN == activityCode){//物料管理-用户登录
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_MATERIAL_USER_SCAN));
                }else if(ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN == activityCode){//岗位指导书-冰箱扫描
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN));
                }else if(ComEvent.ACTION_ADDLIB_SCAN_CODE == activityCode){//一键入库-出库单扫描
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.ACTION_ADDLIB_SCAN_CODE));
                }else if (ComEvent.INSPECTION_USERSCAN==activityCode){
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.INSPECTION_USERSCAN));
                }else if (ComEvent.INSPECTION_CHECKSCAN==activityCode){
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.INSPECTION_CHECKSCAN));
                }else if (ComEvent.ONOFF_FRIDGE_SCAN==activityCode){
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.ONOFF_FRIDGE_SCAN));
                }else if (ComEvent.ACTION_INSTRUCTION_PDF_SCAN==activityCode){//获取pdf岗位指导书的冰箱扫码
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.ACTION_INSTRUCTION_PDF_SCAN));
                }else if (ComEvent.ACTION_GET_SCHUNK==activityCode){//超声波焊接机数据
                    EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.ACTION_GET_SCHUNK));
                }
            }
        }
    }

    /**
     * 打开串口。
     */
    private void openComPort(SerialHelper ComPort){
        try
        {
            ComPort.open();
            showMessage(R.string.com_start_success);
        } catch (SecurityException e) {
            showMessage(R.string.com_start_fail_security);
        } catch (IOException e) {
            showMessage(R.string.com_start_fail_io);
        } catch (InvalidParameterException e) {
            showMessage(R.string.com_start_fail_param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void showMessage(int sMsg)
    {
        Toast.makeText(getApplicationContext(),sMsg,Toast.LENGTH_SHORT).show();
    }
}
