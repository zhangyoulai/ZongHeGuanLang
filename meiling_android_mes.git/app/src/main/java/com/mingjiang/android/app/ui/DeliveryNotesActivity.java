package com.mingjiang.android.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.app.AppConfig;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.IceBox;
import com.mingjiang.android.app.bean.SendPLCMsg;
import com.mingjiang.android.app.db.IbDataBaseUtil;
import com.mingjiang.android.app.db.InsertPreparedData;
import com.mingjiang.android.app.service.ComService;
import com.mingjiang.android.app.service.PrintLabel;
import com.mingjiang.android.base.event.ComEvent;

import java.io.File;
import java.util.ArrayList;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import de.greenrobot.event.EventBus;

public class DeliveryNotesActivity extends Activity implements View.OnClickListener {
    protected final static String TAG = DeliveryNotesActivity.class.getSimpleName();
    private TextView tv;
    private ListView lv;
    private ListView lv_plc1;
    private IceBox ib;
    private IceBox ib_update;
    private ArrayList<IceBox> ibList = new ArrayList<>();
    private ArrayList<SendPLCMsg> plcMsgsList = new ArrayList<>();
    private AlertDialog dialog;
    private MyAdapter myAdapter;
    private MyPlc1Adapter myPlc1Adapter;
    private BootstrapButton addBtn, addItemOkBtn, addItemCancelBtn, updateBtn, deleteBtn, udBtn, queryIcBtn,
            queryIcOkBtn, queryIcCancelBtn, queryAllBtn, openComBtn, closeComBtn;
    private BootstrapEditText r3Et, icsEt, queryEt, numberEt, customerEt, companyEt, addressEt, phoneEt;
    private int count, currentNum;
    private long frsClick, secClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);
        init();
        EventBus.getDefault().register(this);
    }

    private IceBox searchItemFromR2code(String s) {
        if (s.length() < 11) {
            return null;
        }
        s = s.substring(3, 10);
        Log.i(TAG, "s=====" + s);
        return IbDataBaseUtil.queryItem(s, this);
    }

    private void init() {
        lv_plc1 = (ListView) findViewById(R.id.plc1_lv);
        lv = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.scanningcode);
        queryIcBtn = (BootstrapButton) findViewById(R.id.btn_queryic);
        addBtn = (BootstrapButton) findViewById(R.id.btn_add);
        queryAllBtn = (BootstrapButton) findViewById(R.id.btn_queryall);
        openComBtn = (BootstrapButton) findViewById(R.id.btn_opencom);
        closeComBtn = (BootstrapButton) findViewById(R.id.btn_closecom);
        queryIcBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        queryAllBtn.setOnClickListener(this);
        openComBtn.setOnClickListener(this);
        closeComBtn.setOnClickListener(this);
        if (!checkTheDatabase()) {
            IbDataBaseUtil.createDataBase(this);
            InsertPreparedData.insertData(this);
        }
        ibList = IbDataBaseUtil.queryAll(this);
        plcMsgsList.clear();
        plcMsgsList.addAll(getSendPlcList());
        myPlc1Adapter = new MyPlc1Adapter(plcMsgsList, this);
        myAdapter = new MyAdapter(ibList, this);
        lv_plc1.setAdapter(myPlc1Adapter);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // int count = 0;
                count++;
                if (count == 1) {
                    frsClick = System.currentTimeMillis();
                    currentNum = position;
                } else if (count == 2) {
                    secClick = System.currentTimeMillis();
                    if ((secClick - frsClick <= 1500) && (currentNum == position)) {
                        //双击事件
                        Toast.makeText(DeliveryNotesActivity.this, "双击了!", Toast.LENGTH_LONG).show();
                        updateOrDeleteItem(ibList.get(position));
                    }
                    count = 0;
                    frsClick = 0;
                    secClick = 0;
                }
            }
        });
    }

    private void updateOrDeleteItem(IceBox ib) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.activity_notes_update_delete, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
        updateBtn = (BootstrapButton) view.findViewById(R.id.update_btn);
        deleteBtn = (BootstrapButton) view.findViewById(R.id.delete_btn);
        udBtn = (BootstrapButton) view.findViewById(R.id.ud_cancel_btn);
        deleteBtn.setOnClickListener(this);
        udBtn.setOnClickListener(this);
        this.ib_update = ib;
    }

    private Boolean checkTheDatabase() {
        String path = getFilesDir().getAbsolutePath(); //+"/databases/";
        Log.i(TAG, path.substring(0, path.lastIndexOf("/"))); //path.substring(path.lastIndexOf("/")+1);
        path = path.substring(0, path.lastIndexOf("/")) + "/databases/";
        File file = new File(path + AppConfig.dataBaseName);
        if (file.exists()) {
            Toast.makeText(this, "数据库是存在的!", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(this, "数据库不存在！", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            addIcItemDialog();
        } else if (v.getId() == R.id.btn_queryic) {
            queryIcDialog();
        } else if (v.getId() == R.id.btn_queryall) {
            queryAllAndUpdateList();
        } else if (v.getId() == R.id.additem_btn) {
            checkTheDataAndInert2DB();
        } else if (v.getId() == R.id.btn_opencom) {
            openCom();
        } else if (v.getId() == R.id.btn_closecom) {
            closeCom();
        } else if (v.getId() == R.id.cancelitem_btn) {
            dialog.dismiss();
        } else if (v.getId() == R.id.delete_btn) {
            deleteClickedItem();
        } else if (v.getId() == R.id.ud_cancel_btn) {
            dialog.dismiss();
        } else if (v.getId() == R.id.btn_query_cancel) {
            dialog.dismiss();
        } else if (v.getId() == R.id.btn_query_ok) {
            queryItemAndUpdateList();
        }
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_CODE) {
            String code = event.getMessage();
            Log.i(TAG, code);
            ib = searchItemFromR2code(code);
            if (ib == null) {
                // 没有找到冰箱
                tv.setText("没有匹配到冰箱");
            } else {
                //找到冰箱了 显示出扫到的冰箱信息
                ibList.clear();
                ibList.add(ib);
                myAdapter.notifyDataSetChanged();
                SendPLCMsg msg = new SendPLCMsg(ib);
                msg.setSt(true);
                AppConfig.plcMsgsList.add(msg);
                plcMsgsList.clear();
                plcMsgsList.addAll(getSendPlcList());
                myPlc1Adapter.notifyDataSetChanged();
                tv.setText(ib.toString());
                //将参数转成打印机可以识别的命令
                String code1 = PrintLabel.test2(ib.getNumber(), ib.getCustomer(), ib.getPhone(), ib.getAddress(), ib.getIceBoxName());
                //发送打印命令
                EventBus.getDefault().post(new ComEvent(code1, ComEvent.ACTION_PRINT));
            }

        }
    }

    private void openCom() {
        openComBtn.setText("正在通信");
        openComBtn.setEnabled(false);
        closeComBtn.setEnabled(true);
        //开启服务
        startService(new Intent(this, ComService.class));
    }

    private void closeCom() {
        openComBtn.setText("开始通信");
        openComBtn.setEnabled(true);
        closeComBtn.setEnabled(false);
        stopService(new Intent(this, ComService.class));//停止服务
//        logTv.setText("已经停止通信");
    }

    private void queryAllAndUpdateList() {
        ibList.clear();
        ibList.addAll(IbDataBaseUtil.queryAll(this));
        myAdapter.notifyDataSetChanged();
    }

    private void queryItemAndUpdateList() {
        dialog.dismiss();
        String s = queryEt.getText().toString();
        IceBox ib = IbDataBaseUtil.queryItem(s, this);
        ibList.clear();
        ibList.add(ib);
        myAdapter.notifyDataSetChanged();
    }

    private void queryIcDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.activity_notes_queryitem, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
        queryIcOkBtn = (BootstrapButton) view.findViewById(R.id.btn_query_ok);
        queryIcCancelBtn = (BootstrapButton) view.findViewById(R.id.btn_query_cancel);
        queryEt = (BootstrapEditText) view.findViewById(R.id.et_query);
        queryIcOkBtn.setOnClickListener(this);
        queryIcCancelBtn.setOnClickListener(this);
    }

    private void updateClikedItem() {
        // 先删除该行 再添加
        dialog.dismiss();
    }

    private void deleteClickedItem() {
        // 删除该行
        dialog.dismiss();
        IbDataBaseUtil.delete(this, ib_update);
        ibList.clear();
        ibList.addAll(IbDataBaseUtil.queryAll(this));
        myAdapter.notifyDataSetChanged();
    }

    private void checkTheDataAndInert2DB() {
        String r3code = r3Et.getText().toString().trim();
        String ics = icsEt.getText().toString().trim();
        String number = numberEt.getText().toString().trim();
        String customer = customerEt.getText().toString().trim();
        String company = companyEt.getText().toString().trim();
        String address = addressEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        if (r3code.isEmpty() ||
                ics.isEmpty() ||
                number.isEmpty() ||
                customer.isEmpty() ||
                company.isEmpty() ||
                address.isEmpty() ||
                phone.isEmpty()) {
            Toast.makeText(this, "请输入完整", Toast.LENGTH_LONG).show();
            return;
        } else {
            IceBox ic = new IceBox(r3code, ics, number, customer, company, address, phone);
            IbDataBaseUtil.insert(this, ic);
            ibList.clear();
            ibList.addAll(IbDataBaseUtil.queryAll(this));
            myAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    private void addIcItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.activity_notes_additem, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
        addItemOkBtn = (BootstrapButton) view.findViewById(R.id.additem_btn);
        addItemCancelBtn = (BootstrapButton) view.findViewById(R.id.cancelitem_btn);
        r3Et = (BootstrapEditText) view.findViewById(R.id.r3_et);
        icsEt = (BootstrapEditText) view.findViewById(R.id.ics_et);
        numberEt = (BootstrapEditText) view.findViewById(R.id.number_et);
        customerEt = (BootstrapEditText) view.findViewById(R.id.customer_et);
        companyEt = (BootstrapEditText) view.findViewById(R.id.company_et);
        addressEt = (BootstrapEditText) view.findViewById(R.id.address_et);
        phoneEt = (BootstrapEditText) view.findViewById(R.id.phone_et);
        addItemOkBtn.setOnClickListener(this);
        addItemCancelBtn.setOnClickListener(this);
    }

    private class MyAdapter extends BaseAdapter {
        private ArrayList<IceBox> ibList;
        private Context context;

        public MyAdapter(ArrayList<IceBox> list, Context context) {
            ibList = list;
            this.context = context;
            Log.i(TAG, list.size() + "~~~~~~~~~~~~");
        }

        @Override
        public int getCount() {
            return ibList.size();
        }

        @Override
        public Object getItem(int position) {
            return ibList.get(position);
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
                v = View.inflate(context, R.layout.activity_notes_list, null);
            }
            //
            TextView r3View = (TextView) v.findViewById(R.id.r3code);
            TextView styView = (TextView) v.findViewById(R.id.icstyle);
            TextView numberView = (TextView) v.findViewById(R.id.number);
            TextView customerView = (TextView) v.findViewById(R.id.customer);
            TextView companyView = (TextView) v.findViewById(R.id.company);
            TextView addressView = (TextView) v.findViewById(R.id.address);
            TextView phoneView = (TextView) v.findViewById(R.id.phone);
            r3View.setText(ibList.get(position).getR3code());
            styView.setText(ibList.get(position).getIceBoxName());
            numberView.setText(ibList.get(position).getNumber());
            customerView.setText(ibList.get(position).getCustomer());
            companyView.setText(ibList.get(position).getCompany());
            addressView.setText(ibList.get(position).getAddress());
            phoneView.setText(ibList.get(position).getPhone());
            return v;
        }
    }

    private ArrayList<SendPLCMsg> getSendPlcList() {
        ArrayList<SendPLCMsg> a = new ArrayList<>();
        for (SendPLCMsg msg : AppConfig.plcMsgsList) {
            a.add(msg);
        }
        return a;
    }

    private class MyPlc1Adapter extends BaseAdapter {
        public ArrayList<SendPLCMsg> plcMsgsList;
        private Context context;

        public MyPlc1Adapter(ArrayList<SendPLCMsg> msg, Context context) {
            plcMsgsList = msg;
            this.context = context;
        }

        @Override
        public int getCount() {
            return plcMsgsList.size();
        }

        @Override
        public Object getItem(int position) {
            return plcMsgsList.get(position);
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
                v = View.inflate(context, R.layout.activity_notes_print_list, null);
            }
            //在此显示要打印的所有条目的信息
            TextView r3 = (TextView) v.findViewById(R.id.plc1_r3);
            TextView nm = (TextView) v.findViewById(R.id.plc1_nm);
            TextView xm = (TextView) v.findViewById(R.id.plc1_xm);
            TextView sj = (TextView) v.findViewById(R.id.plc1_sj);
            TextView dz = (TextView) v.findViewById(R.id.plc1_dz);
            TextView xh = (TextView) v.findViewById(R.id.plc1_xh);
            TextView st = (TextView) v.findViewById(R.id.plc1_st);

            r3.setText(plcMsgsList.get(position).getIb().getR3code());//R3码
            nm.setText(plcMsgsList.get(position).getIb().getNumber());//订单号
            xm.setText(plcMsgsList.get(position).getIb().getCustomer());//姓名
            sj.setText(plcMsgsList.get(position).getIb().getPhone());//联系方式
            dz.setText(plcMsgsList.get(position).getIb().getAddress());//地址
            xh.setText(plcMsgsList.get(position).getIb().getIceBoxName());//是否打印
            //获取打印状态
            if (plcMsgsList.get(position).getSt()) {
                st.setTextColor(Color.RED);
                st.setText("已打印");
            } else {
                st.setTextColor(Color.BLACK);
                st.setText("未打印");
            }
            return v;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, ComService.class));
        AppConfig.plcMsgsList.clear();
        plcMsgsList.clear();
    }

}