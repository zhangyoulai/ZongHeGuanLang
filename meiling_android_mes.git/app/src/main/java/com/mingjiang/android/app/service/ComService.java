package com.mingjiang.android.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.mingjiang.android.app.R;
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

    protected final static String TAG = ComService.class.getSimpleName();
    private final static String STR_BAUD_RATE1 = "115200";
    private final static String STR_BAUD_RATE = "9600";
    private final static String STR_DEV_ONE = "/dev/ttyS1";//西铁城打印机
    private final static String STR_DEV_TWO = "/dev/ttyS2";//斑马打印机
    private final static String STR_DEV_THREE = "/dev/ttyS3";//手持扫码枪
    private final static String STR_DEV_FOUR = "/dev/ttyS4";//贴码机PLC
    SerialControl comOne, comTwo, comThree, comFour;
    public static boolean PRINT = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        super.onCreate();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        comOne.close();
        comTwo.close();
        comThree.close();
        comFour.close();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventBackgroundThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_PRINT_CITIIZEN) {
            printCitizenCode(event.getMessage());
        } else if (event.getActionType() == ComEvent.ACTION_PRINT_ZEBRA) {
            printZebraCode(event.getMessage());
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
        comOne.setBaudRate(STR_BAUD_RATE);
        comTwo.setBaudRate(STR_BAUD_RATE);
        comThree.setBaudRate(STR_BAUD_RATE1);
        comFour.setBaudRate(STR_BAUD_RATE);
        openComPort(comOne);
        openComPort(comTwo);
        openComPort(comThree);
        openComPort(comFour);
    }

    private class SerialControl extends SerialHelper {
        public SerialControl() {
        }

        //从串口收取数据
        @Override
        protected void onDataReceived(final ComBean comRecData) {
            if (comRecData.sComPort.equals(STR_DEV_THREE)) {//扫码枪
                Log.e("onDataReceived bRec", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_CODE));
                printZebraCode(code);
            } else if (comRecData.sComPort.equals(STR_DEV_FOUR)) {//PLC
                Log.e("onDataReceived bRec", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived code", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_PLC));
            }
        }

        @Override
        public void stopSend() {
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

    private void printCitizenCode(String value) {
        Log.e("ComService.PRINT", String.valueOf(ComService.PRINT));
        if (ComService.PRINT) {
            value = PrintLabel.CitizenLabel(value);
            Log.e("print_code", value);
            comOne.sendTxt(value);
        }
    }

    private void printZebraCode(String value) {
        Log.e("ComService.PRINT", String.valueOf(ComService.PRINT));
        if (ComService.PRINT) {
            value = PrintLabel.ZebraLabel(value);
            Log.e("print_code", value);
            comTwo.sendTxt(value);
        }
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
