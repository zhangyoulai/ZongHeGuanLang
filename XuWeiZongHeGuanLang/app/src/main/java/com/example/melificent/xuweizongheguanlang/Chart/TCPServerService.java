package com.example.melificent.xuweizongheguanlang.Chart;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {
    public TCPServerService() {
    }
    private  boolean mIsServiceDestroy = false;
    private  String[] mDdefinedMessages  = new String[]{
            "hello","hello2","hello3","hello5","hello6"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
            return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroy = true;
        super.onDestroy();
    }

    private class  TcpServer implements  Runnable{
        @Override
        public void run() {
            ServerSocket ss = null;
            try {
                ss= new ServerSocket(8688);
            } catch (IOException e) {
                Log.w("faile","connect port 8688 faile");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroy){
                try {
                    final Socket clinet = ss.accept();
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(clinet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private  void responseClient(Socket client) throws IOException{
        //for receive messages from client
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //send messages to client
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);

        while (!mIsServiceDestroy){
            String str = in.readLine();
            //here can output messages from client
            Log.i("ReceiveClientMsg",""+str);
            if (str==null){
                //disconnect between client and server
                out.println("welcome to here !");
                break;
            }

            int i = new Random().nextInt(mDdefinedMessages.length);
            String msg = mDdefinedMessages[i];
            out.println(msg);
            //here to send msg to client from server ,and can print to look it's value
            Log.i("sendMsg",""+msg);
        }
        //quit
        //close the stream
        out.close();
        in.close();
        client.close();
    }
}
