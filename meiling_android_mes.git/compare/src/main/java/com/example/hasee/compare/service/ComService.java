package com.example.hasee.compare.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.hasee.compare.R;
import com.mingjiang.android.base.event.ComEvent;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import android_serialport_api.ComBean;
import android_serialport_api.SerialHelper;
import de.greenrobot.event.EventBus;

/*
* 串口服务类
* 这里可以控制串口的启动和串口数据的分发
* */
public class ComService extends Service {
    private final static String STR_BAUD_RATE1 = "9600";
    private final static String STR_BAUD_RATE2 = "115200";
    private final static String STR_DEV_ONE = "/dev/ttyS1";//24V在左下
    private final static String STR_DEV_TWO = "/dev/ttyS2";
    private final static String STR_DEV_THREE = "/dev/ttyS3";
    private final static String STR_DEV_FOUR = "/dev/ttyS4";//24V在右上
    SerialControl comOne, comTwo, comThree, comFour;
    public static boolean PRINT = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("ComService", "onCreate()");
        super.onCreate();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        Log.e("ComService", "onDestroy()");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventBackgroundThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_SEND_PCI) {
                comOne.sendTxt(event.getMessage());
        }else if (event.getActionType() == ComEvent.ACTION_SEND_PLC) {
                comThree.send(event.getBytes());
        }else if (event.getActionType() == ComEvent.ACTION_SEND_ROB) {
                comTwo.send(event.getBytes());
        }
    }

    public void initData() {
        comOne = new SerialControl();
        comTwo = new SerialControl();
        comThree = new SerialControl();
        comFour = new SerialControl();
        comOne.setPort(STR_DEV_ONE);
        comTwo.setPort(STR_DEV_TWO);
        comThree.setPort(STR_DEV_THREE);
        comFour.setPort(STR_DEV_FOUR);
        comOne.setBaudRate(STR_BAUD_RATE1);
        comTwo.setBaudRate(STR_BAUD_RATE1);
        comThree.setBaudRate(STR_BAUD_RATE1);
        comFour.setBaudRate(STR_BAUD_RATE1);
        openComPort(comOne);
        openComPort(comTwo);
        openComPort(comThree);
        openComPort(comFour);
    }

    public class SerialControl extends SerialHelper {
        public SerialControl() {
        }

        //从串口收取数据
        @Override
        public void onDataReceived(final ComBean comRecData) {
            if (comRecData.sComPort.equals(STR_DEV_ONE)) {//
                Log.e("onDataReceived bRec 1", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code 1", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_PCI));
            }else if (comRecData.sComPort.equals(STR_DEV_TWO)){//机器人
                Log.e("onDataReceived bRec 2", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code 2", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_ROB));
            }else if (comRecData.sComPort.equals(STR_DEV_THREE)){//PLC
                Log.e("onDataReceived bRec 3", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code 3", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_PLC));
            }else if (comRecData.sComPort.equals(STR_DEV_FOUR)){//固定扫码枪
                Log.e("onDataReceived bRec 4", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code 4", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_CODE));
            }
        }

        @Override
        public void stopSend() {
            Log.e("ComService", "stopSend()");
            super.stopSend();
        }
    }

    private void openComPort(SerialHelper ComPort) {
        try {
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

    private void showMessage(int sMsg) {
        Toast.makeText(this.getApplicationContext(),
                getString(sMsg), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
