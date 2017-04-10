package com.mingjiang.android.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.app.AppConfig;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.util.NetDataUtil;
import com.mingjiang.android.app.bean.Code;
import com.mingjiang.android.app.bean.CodeAndSpec;
import com.mingjiang.android.app.db.IbDataBaseUtil;
import com.mingjiang.android.app.db.InsertPreparedData;
import com.mingjiang.android.app.service.ComService;
import com.mingjiang.android.app.util.HttpClientUtils;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CompareActivity extends Activity {
    private final String TAG = "CompareActivity";
    private HashMap map = new HashMap();
    private ListView mListView;
    private MyAdapter myAdapter;
    private ArrayList<Code> codes = new ArrayList<>();
    private List<CodeAndSpec> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        if (!checkTheDatabase()) {
            IbDataBaseUtil.createDataBase(this);
            InsertPreparedData.insertData(this);
        }
        //importplan
        importPlan();
        //开启服务
        startService(new Intent(this, ComService.class));
        //初始化UI组件
        initView();
        //事件注册
        EventBus.getDefault().register(this);
    }

    //读取日计划
    private void importPlan() {
        new Thread() {
            @Override
            public void run() {
                String url = Config.getBaseUrl(CompareActivity.this) + "api/interface/public/plan_manage.daily_plan/process_serial_element";
                items = NetDataUtil.getCodeAndSpec(url);
                //执行完毕后给handler发送一个空消息
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    /*初始化UI组件*/
    void initView() {
        mListView = (ListView) findViewById(R.id.ll_compare_code);
        myAdapter = new MyAdapter(codes, this);
        mListView.setAdapter(myAdapter);
    }

    private Boolean checkTheDatabase() {
        String path = getFilesDir().getAbsolutePath(); //+"/databases/";
        Log.i(TAG, path.substring(0, path.lastIndexOf("/"))); //path.substring(path.lastIndexOf("/")+1);
        path = path.substring(0, path.lastIndexOf("/")) + "/databases/";
        File file = new File(path + "meiling.db");
        if (file.exists()) {
            Toast.makeText(this, "数据库是存在的!", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(this, "数据库不存在！", Toast.LENGTH_LONG).show();
        return false;
    }

    /*后台进程 读取到两个二维码并调用比对方法*/
    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_CODE) {
            String code = event.getMessage();
            Log.i(TAG, code);
            DealWithCode(code);
        }
    }

    public void DealWithCode(String postCode) {
        if (postCode.startsWith("submit")) {
            commitData();
        } else if (postCode.startsWith("error")) {
            map.clear();
        } else {
            //把读取到的码存起来  判断存码的地方有几个码
            if (map.size() == 0) {
                map.put("code1", postCode);
                String postCode1 = postCode.substring(3, 10);
                String map1_spec = getSpecBasedOnProductCode(postCode1);
                Code code = new Code("", "", postCode, map1_spec);
//                code.setQrcode(postCode);
//                code.setQrcode_spec(map1_spec);
                codes.add(code);
                myAdapter.notifyDataSetChanged();
            } else if (map.size() == 1) {
                map.put("code2", postCode);
                compareCode();
            } else if (map.size() == 2) {
                //请先提交结果 提交后开始下一次扫码
                Toast.makeText(CompareActivity.this, "请先提交结果!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 提交一维码+二维码
     */
    private void commitData() {
        new Thread() {
            @Override
            public void run() {
                String qrCodeStr = (String) map.get("code1");//二维码
                String barCodeStr = (String) map.get("code2");//一维码
                String url = Config.getBaseUrl(CompareActivity.this) +
                        AppConfig.URL_UPLOAD_CODE;
                HttpClientUtils.sendJsonToServer(url, barCodeStr, qrCodeStr);
                handler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 根据R3码获得产品型号
     *
     * @param r3code
     */
    private String getSpecBasedOnProductCode(final String r3code) {
        String spec = "";
//        spec = IbDataBaseUtil.queryItem(r3code,this).getSpec();
        Log.i(TAG, spec);
        return spec;
    }

    /**
     * 更新UI
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2: {
                    Log.i(TAG, "上传完成...");
                    map.clear();//
                    break;
                }
                case 1: {
                    for (int i = 0; i < items.size(); i++) {
//                        IbDataBaseUtil.insert(CompareActivity.this,items.get(i));
                    }
                    Log.i(TAG, "daily plan...");
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void compareCode() {
        //开启后台进程 完成后通知UI进程
        String barcode = (String) map.get("code2");
        //code1为空时不能做任何操作
        if (barcode == null) {

        } else {
            String barcode1 = barcode.substring(3, 10);
            String bar_spec = getSpecBasedOnProductCode(barcode1);
            codes.get(codes.size() - 1).setBarcode(barcode);
            codes.get(codes.size() - 1).setBarcode_spec(bar_spec);
            codes.get(codes.size() - 1).setFlag(true);
            //更新显示
            myAdapter.notifyDataSetChanged();
            if (codes.get(codes.size() - 1).getBarcode_spec().equals(codes.get(codes.size() - 1).getQrcode_spec())) {
                //                codes.get(codes.size()-1).setFlag(true);
//                myAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(CompareActivity.this, "有错误，请注意！", Toast.LENGTH_LONG).show();
                codes.get(codes.size() - 1).setFlag(false);
                myAdapter.notifyDataSetChanged();
                map.clear();
            }
        }
    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        private ArrayList<Code> codes;
        private Context context;

        public MyAdapter(ArrayList<Code> list, Context context) {
            codes = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return codes.size();
        }

        @Override
        public Object getItem(int position) {
            return codes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView != null) {
                v = convertView;
            } else {
                v = View.inflate(context, R.layout.activity_compare_item, null);
            }
            TextView compareQrcodeTv = (TextView) v.findViewById(R.id.tv_compare_qrcode);
            TextView compareQrcodeSpecTv = (TextView) v.findViewById(R.id.tv_compare_qrcode_xh);
            TextView compareBarcodeTv = (TextView) v.findViewById(R.id.tv_compare_barcode);
            TextView compareBarcodeSpecTv = (TextView) v.findViewById(R.id.tv_compare_barcode_xh);
            Button compareBtn = (Button) v.findViewById(R.id.btn_compare);
            compareQrcodeTv.setText(codes.get(position).getQrcode());
            compareQrcodeSpecTv.setText(codes.get(position).getQrcode_spec());
            compareBarcodeTv.setText(codes.get(position).getBarcode());
            compareBarcodeSpecTv.setText(codes.get(position).getBarcode_spec());
            if (codes.get(position).getFlag()) {
                compareBtn.setBackgroundColor(Color.GREEN);
            } else {
                compareBtn.setBackgroundColor(Color.RED);
            }
            compareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //打印后向服务器发送数据 二维码+订单号
                    commitData();
                }
            });
            return v;
        }
    }
}