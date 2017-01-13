package com.example.melificent.messenger.SERVER;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.melificent.messenger.MyConstant;

public class MyService extends Service {
    public MyService() {
    }
    private static final  String TAG ="MessengerService";
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MyConstant.MSG_FROM_CLINET:
                    Log.i(TAG,"recieve msg from Client:"+msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message Msg = Message.obtain(null,2);
                    Bundle data = new Bundle();
                    data.putString("server","this is reply to client: ok,I have recieved MSG!");
                    Msg.setData(data);
                    try {
                        client.send(Msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    public static Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();

    }
}
