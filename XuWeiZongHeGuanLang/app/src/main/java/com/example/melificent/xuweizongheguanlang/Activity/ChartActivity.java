package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.melificent.xuweizongheguanlang.Adapter.ChartAdapter;
import com.example.melificent.xuweizongheguanlang.Bean.Msg;
import com.example.melificent.xuweizongheguanlang.Chart.TCPServerService;
import com.example.melificent.xuweizongheguanlang.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/9.
 * Use for chart with client and server on different process
 */

public class ChartActivity extends AppCompatActivity {
    @InjectView(R.id.chart_listview)
    ListView listView;
    @InjectView(R.id.chart_send)
    Button send;
    @InjectView(R.id.chart_input)
    EditText input;

    private static final int Message_Receive_New_Message  =1;
    private static final int Message_Socket_Connected = 2;
    private PrintWriter printWriter;
    private Socket mClientSocket;

    List<Msg> msgs = new ArrayList<>();

    ChartAdapter adapter ;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Message_Receive_New_Message :{
                    msgs.add(new Msg((String) msg.obj,Msg.TYPE_RECEIVE));
                    break;
                }
                case Message_Socket_Connected:{
                    send.setEnabled(true);
                    break;
                }
                default:
                    break;
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_activity);
        ButterKnife.inject(this);
        Intent service = new Intent(this, TCPServerService.class);
        startService(service);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v== send){
                    final  String msg = input.getText().toString();
                    if (!TextUtils.isEmpty(msg)&& printWriter!=null){
                        printWriter.println(msg);
                        //this is send msg to server

                        String time = formateDateTime(System.currentTimeMillis());
                        //here can get send time now

                        String send = time + msg;
                        msgs.add(new Msg(send,Msg.TYPE_SEND));

                        adapter.notifyDataSetChanged();
//                        listView.setSelection(msgs.size());
                        input.setText("");

                    }
                }
            }
        });
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
                super.run();
            }
        }.start();

        adapter = new ChartAdapter(ChartActivity.this,msgs);
        listView.setAdapter(adapter);
    }

    private String formateDateTime(long l) {
     return  new SimpleDateFormat("(HH:mm:ss)").format(new Date(l));
    }

    @Override
    protected void onDestroy() {

        //close connect ,before that shutdown input first;
        if (mClientSocket!=null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("localhost",8688);
                mClientSocket = socket;
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                handler.sendEmptyMessage(Message_Socket_Connected);
                //here we can know connect success
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!ChartActivity.this.isFinishing()){
                String msg = br.readLine();
                Log.i("msg","Receive Msg :"+msg);
                //here can get msg from server
                if (msg != null){
                    String time = formateDateTime(System.currentTimeMillis());
                    final  String showedmsg = "server"+time+":"+msg ;

                    Log.i("receiveMsg","Receive Msg :"+showedmsg);
                    //get receive msg time
                    handler.obtainMessage(Message_Receive_New_Message,showedmsg)
                            .sendToTarget();

                }
            }
            //quit
            printWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
