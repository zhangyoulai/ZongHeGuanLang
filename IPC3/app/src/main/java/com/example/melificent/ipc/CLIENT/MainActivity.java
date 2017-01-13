package com.example.melificent.ipc.CLIENT;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.melificent.ipc.R;
import com.example.melificent.ipc.SERVICE.MyService;

import java.util.List;

import AIDL.Book;
import AIDL.IBookManager;
import AIDL.IOnNewBookArrivedListener;

public class MainActivity extends AppCompatActivity {
        private  static  final  String TAG = "BookManagerActivity";
    private  static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBookManager Remotemanager ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED :
                    Log.i(TAG,"recieve new book ");
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    };
    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager manager = IBookManager.Stub.asInterface(service);
            Remotemanager = manager;
            try {
                List<Book> list = manager.getBookList();
                Log.i(TAG,"query book list, list type:"+list.getClass().getCanonicalName());
                Log.i(TAG,"query book list："+list.toString());
                Book book =new Book(3,"c");
                manager.addBook(book);
                Log.i(TAG,"add book :"+book);
                List<Book> newbookList = manager.getBookList();
                Log.i(TAG,"query new book list:"+newbookList.toString());
                manager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void OnNewBookArrived(Book book) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,book).sendToTarget();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Remotemanager==null&&Remotemanager.asBinder().isBinderAlive()){
            Log.i(TAG,"unregister listener:"+mOnNewBookArrivedListener);
            try {
                Remotemanager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
