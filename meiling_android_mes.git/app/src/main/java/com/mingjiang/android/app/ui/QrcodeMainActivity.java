package com.mingjiang.android.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mingjiang.android.app.AppConfig;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.adapter.DailyPlanAdapter;
import com.mingjiang.android.app.adapter.PrintLogAdapter;
import com.mingjiang.android.app.util.NetDataUtil;
import com.mingjiang.android.app.bean.Plan;
import com.mingjiang.android.app.bean.PrintLog;
import com.mingjiang.android.app.service.ComService;
import com.mingjiang.android.app.service.PrintLabel;
import com.mingjiang.android.app.util.HttpClientUtils;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.StringQueue;
import com.mingjiang.android.base.util.StringUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


import de.greenrobot.event.EventBus;

public class QrcodeMainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    protected final static String TAG = QrcodeMainActivity.class.getSimpleName();

    protected StringQueue queue = new StringQueue(300);//实例化一个队列
    protected ListView listView, logListview;
    protected List<Plan> plans = new ArrayList<Plan>();
    public static ArrayList<PrintLog> printMsgsList = new ArrayList<>();
    protected DailyPlanAdapter dailyPlanAdapter;
    protected PrintLogAdapter printLogAdapter;
    protected Button importPlanButton, addPrintButton, printBrokenCodeButton,
            newPaperButton;
    protected ToggleButton twoPaperButton;
    protected TextView paperNumTextview;
    protected List<String> brokencodes = new ArrayList<>();
    protected DatePicker datePicker;
    protected String beginTime;
    protected String qrCode;
    protected int paperNum;
    //isEmpty表示贴码机PLC请求二维码的两种结果：有或没有
    private volatile boolean isEmpty = true;  //开始为空
    private volatile boolean isTwo = false;
    protected View preView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_main);
        EventBus.getDefault().register(this);
        startService(new Intent(this, ComService.class));//开启串口服务
        initView();
        initData();
        checkPaperNum();//检测是否需要换纸
    }

    private void kouJianPaper() {
        paperNum = paperNum - 1;
        com.mingjiang.android.base.util.Config.setPaperNum(this, paperNum);
        checkPaperNum();
    }

    /**
     * 更换纸张，使用按钮触发
     */
    private void newPaper() {
        com.mingjiang.android.base.util.Config.setPaperNum(this, 4000);
        checkPaperNum();
    }

    private void checkPaperNum() {
        paperNum = com.mingjiang.android.base.util.Config.getPaperNum(this);
        Log.i(TAG, String.valueOf(paperNum));
        paperNumTextview.setText(String.valueOf(paperNum));
        if (paperNum < 850) {
            //提示换纸
            Toast.makeText(QrcodeMainActivity.this, "请更换纸张!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initData() {
        printMsgsList.clear();
        printMsgsList.addAll(getSendPlcList());
        printLogAdapter = new PrintLogAdapter(this, printMsgsList);
        logListview.setAdapter(printLogAdapter);
    }

    protected void initView() {
        listView = (ListView) findViewById(R.id.lv_item);
        logListview = (ListView) findViewById(R.id.lv_info);
        importPlanButton = (Button) findViewById(R.id.btn_import_plan);
        addPrintButton = (Button) findViewById(R.id.btn_add_print);
        printBrokenCodeButton = (Button) findViewById(R.id.btn_print_broken_code);
        newPaperButton = (Button) findViewById(R.id.btn_new_paper);
        importPlanButton.setOnClickListener(this);
        addPrintButton.setOnClickListener(this);
        printBrokenCodeButton.setOnClickListener(this);
        newPaperButton.setOnClickListener(this);
        paperNumTextview = (TextView) findViewById(R.id.tv_paper_num);
        datePicker = (DatePicker) findViewById(R.id.dp_plan_date);
        datePicker.setCalendarViewShown(false);//datepickek设置成只显示下拉，不显示整月视图
        dailyPlanAdapter = new DailyPlanAdapter(this, plans, onClickListener);//适配数据
        listView.setAdapter(dailyPlanAdapter);//显示数据
//        listView.setOnItemClickListener(new OnItemClickHandler());//按钮绑定事件
        twoPaperButton = (ToggleButton) findViewById(R.id.tbtn_two_paper);
        twoPaperButton.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tbtn_two_paper:
                if (compoundButton.isChecked()) {
                    isTwo = true;
                    Toast.makeText(this, "双码功能开启", Toast.LENGTH_SHORT).show();
                } else {
                    isTwo = false;
                    Toast.makeText(this, "双码功能关闭", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * item点击事件
     */
    private class OnItemClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position,
                                long id) {
            System.out.println(" position1 :" + position);
            System.out.println(" data1 :" + plans.get(position).toString());
            for (int i = 0; i < parent.getCount(); i++) {//循环设置每个item的背景色
                if (parent.getChildAt(i) == view) {//如果是当前选中的就设置为特俗的背景色
                    view.setBackgroundResource(R.color.red);
                } else {//其他的就设置为默认色
                    parent.getChildAt(i).setBackgroundResource(R.color.blue);
                }
            }
        }
    }

    /**
     * item中button点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            //点击了哪一条
            final int pos = (Integer) btn.getTag();
            System.out.println(" position2 :" + pos);
            System.out.println(" data2 :" + plans.get(pos).toString());
            if (preView == null) {
                view.setBackgroundResource(R.color.orange);
//                listView.getChildAt(pos).setBackgroundResource(R.color.orange);
                preView = view;//preView定义为全局的，不在listener中定义
            } else {
                preView.setBackgroundDrawable(view.getBackground());
                view.setBackgroundResource(R.color.orange);
                preView = view;
            }
            plans.get(pos).setCompleted(plans.get(pos).getCompleted() + 1);
            listView.setSelection(pos);
            dailyPlanAdapter.notifyDataSetChanged();
            //获取二维码
            final String submitData = "export_id=" + plans.get(pos).getExport_id() + "&" +
                    "export=" + plans.get(pos).getExport() + "&" +
                    "order_id=" + plans.get(pos).getOrder() + "&" +
                    "plan_date=" + plans.get(pos).getDate() + "&" +
                    "product_code=" + plans.get(pos).getR3code();
            final String url = com.mingjiang.android.base.util.Config.getBaseUrl(QrcodeMainActivity.this) +
                    AppConfig.URL_GET_QRCODE + "?" + submitData;
            Log.i(TAG, submitData);
            new Thread() {
                @Override
                public void run() {
                    try {
                        qrCode = HttpClientUtils.getDataFromServer(url, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(2);
                }
            }.start();
        }
    };

    private ArrayList<PrintLog> getSendPlcList() {
        ArrayList<PrintLog> a = new ArrayList<>();
        for (PrintLog msg : AppConfig.printLogArrayList) {
            a.add(msg);
        }
        return a;
    }

    public void onEventBackgroundThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_PLC) {
            String message = event.getMessage().toUpperCase();
            if (message.contains("X")) {
                //接收到PLC指令，打印、报警
                try {
                    Thread.sleep(3500);//延时3.5s打印
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (AppConfig.printLogArrayList.size() == 0) {
                    // 没有冰箱消息在队列中 给予警告
                    Log.i(TAG, "queue empty");
                    isEmpty = true;
                    return;
                }
                //按顺序发送参数
                for (int i = 0; i < AppConfig.printLogArrayList.size(); i++) {
                    PrintLog msg = AppConfig.printLogArrayList.get(i);
                    if (msg.isPrintStatus() == false) {//未打印
                        kouJianPaper();
                        //将消息发送给打印机
                        msg.setPrintStatus(true);
                        EventBus.getDefault().post(new ComEvent(msg.getPrintCode(),
                                ComEvent.ACTION_PRINT_CITIIZEN));
                        isEmpty = false;
                        return;
                    }
                }
                isEmpty = true;
                Log.i(TAG, "queue empty");
//                //判断队列是否为空,
//                if (!queue.isEmpty()) {
//                    EventBus.getDefault().post(new ComEvent(queue.remove(),
//                            ComEvent.ACTION_PRINT_CITIIZEN));
//                    isEmpty = false;
//                    //
//                } else {
//                    Log.i(TAG, "queue empty");
//                    isEmpty = true;
//                }
            }
            printMsgsList.clear();
            printMsgsList.addAll(getSendPlcList());
            printLogAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 按钮点击事件
     *
     * @param v view对象
     */
    public void onClick(View v) {
        if (v.getId() == R.id.btn_import_plan) {
            importDailyPlan();
        } else if (v.getId() == R.id.btn_add_print) {
            addPrint();
        } else if (v.getId() == R.id.btn_print_broken_code) {
            printBrokenCode();
        } else if (v.getId() == R.id.btn_new_paper) {
            newPaper();
        }
    }

    //读取日计划
    protected void importDailyPlan() {
        beginTime = StringUtil.getTheDatePicker(datePicker);
        Log.i(TAG, beginTime);
        new Thread() {
            @Override
            public void run() {
                String url = com.mingjiang.android.base.util.Config.getBaseUrl(QrcodeMainActivity.this) +
                        AppConfig.URL_DAILY_PLAN;
                plans = NetDataUtil.getDailyPlanItems(url, beginTime, "1");
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    Log.i(TAG, "读取日计划...");
                    //读取到日计划，更新ListView
                    dailyPlanAdapter = new DailyPlanAdapter(QrcodeMainActivity.this, plans, onClickListener);//适配数据
                    listView.setAdapter(dailyPlanAdapter);//显示数据
//                    listView.setOnItemClickListener(new OnItemClickHandler());
                    break;
                }
                case 2: {
                    qrCode = qrCode.substring(1, qrCode.length() - 2);//去引号
                    Log.i(TAG, qrCode);
                    //打印二维码
                    if (isTwo) {//双码功能开启
                        EventBus.getDefault().post(new ComEvent(qrCode, ComEvent.ACTION_PRINT_ZEBRA));
                        EventBus.getDefault().post(new ComEvent(qrCode, ComEvent.ACTION_PRINT_ZEBRA));
                    } else {
                        EventBus.getDefault().post(new ComEvent(qrCode, ComEvent.ACTION_PRINT_ZEBRA));
                        //将西铁城的码存到队列
                        if (isEmpty) {//标志位true直接打印
                            EventBus.getDefault().post(new ComEvent(qrCode, ComEvent.ACTION_PRINT_CITIIZEN));
                            isEmpty = false;
                            PrintLog printLog = new PrintLog(qrCode, qrCode, true);//已打印
                            kouJianPaper();
                            AppConfig.printLogArrayList.add(printLog);
                            printLogAdapter.mDatas = AppConfig.printLogArrayList;
                            printLogAdapter.notifyDataSetChanged();
                        } else {
//                        queue.insert(qrCode);
                            PrintLog printLog = new PrintLog(qrCode, qrCode, false);//未打印
                            AppConfig.printLogArrayList.add(printLog);
                            printLogAdapter.mDatas = AppConfig.printLogArrayList;
                            printLogAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                }
                case 3: {
                    Log.i(TAG, brokencodes.get(0));
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 打印报废流水号
     */
    protected void printBrokenCode() {
        new Thread() {
            @Override
            public void run() {
                String url = com.mingjiang.android.base.util.Config.getBaseUrl(QrcodeMainActivity.this) +
                        AppConfig.URL_SCRAPPED_ORDER;
                try {
                    brokencodes = NetDataUtil.getScrappedCodes(url, "12345");
                    for (int i = 0; i < brokencodes.size(); i++) {
                        Log.i(TAG, brokencodes.get(i));
                        EventBus.getDefault().post(new ComEvent(PrintLabel.ZebraLabel(brokencodes.get(i)),
                                ComEvent.ACTION_PRINT));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 补打印
     */
    protected void addPrint() {
        //跳转到补打印页面
        startActivityForResult(new Intent(this, QrcodePrintActivity.class), 2);
    }

    /**
     * 为了得到传回的数据，必须重写onActivityResult方法
     *
     * @param requestCode 请求码，即调用startActivityForResult()传递过去的值
     * @param resultCode  结果码，结果码用于标识返回数据来自哪个新Activity
     * @param data1       传回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        switch (requestCode) {
            case 2:
                //来自补打印按钮的请求，作相应业务处理
                //得到新Activity 关闭后返回的数据
                String add_print_result = data1.getExtras().getString("item");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, ComService.class));
        AppConfig.printLogArrayList.clear();
    }
}