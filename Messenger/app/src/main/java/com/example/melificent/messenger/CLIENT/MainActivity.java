package com.example.melificent.messenger.CLIENT;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.melificent.messenger.MyConstant;
import com.example.melificent.messenger.R;
import com.example.melificent.messenger.SERVER.MyService;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG = "MessengerActivity";
    private  Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Log.i(TAG,""+msg.getData().getString("server"));
                    break;
            }
        }
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService= new Messenger(service);
            Message Msg = Message.obtain(null, MyConstant.MSG_FROM_CLINET);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello,this is client");
            Msg.setData(bundle);
            Msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(Msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
