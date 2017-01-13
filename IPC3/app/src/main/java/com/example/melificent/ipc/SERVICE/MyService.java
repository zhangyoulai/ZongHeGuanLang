package com.example.melificent.ipc.SERVICE;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import AIDL.Book;
import AIDL.IBookManager;
import AIDL.IOnNewBookArrivedListener;

public class MyService extends Service {
    public MyService() {
    }
    private static  final  String TAG = "BMS";
    private AtomicBoolean mIsServiceDestroy = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> booklist = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> Listener = new RemoteCallbackList<>();
    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return booklist;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            booklist.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
           Listener.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
           Listener.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        booklist.add(new Book(1,"a"));
        booklist.add(new Book(2,"b"));
        booklist.add(new Book(3,"c"));

    }
private  class  ServiceWorker implements Runnable{

    @Override
    public void run() {
        while (!mIsServiceDestroy.get()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int bookId = booklist.size()+1;
            Book book = new Book(bookId,"new book #"+bookId);
            try {
                OnNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
 private  void OnNewBookArrived(Book book) throws RemoteException{
     booklist.add(book);
     final int N = Listener.beginBroadcast();
     for (int i =0;i<N;i++){
         IOnNewBookArrivedListener listener= Listener.getBroadcastItem(i);
         if (listener!=null){
            try{
                listener.OnNewBookArrived(book);
            }catch (RemoteException e){
                e.printStackTrace();
            }
         }
     }
     Listener.finishBroadcast();
 }
    @Override
    public IBinder onBind(Intent intent) {
      return mBinder;
    }
}
