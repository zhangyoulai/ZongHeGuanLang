package com.mingjiang.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.service.PrintLabel;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.StringUtil;

import java.util.Date;

import de.greenrobot.event.EventBus;

public class QrcodePrintActivity extends AppCompatActivity implements View.OnClickListener {

    protected final static String TAG = QrcodePrintActivity.class.getSimpleName();
    protected String origin, category, line, code, custom, sDate, number, order;
    protected Spinner originSpinner, categorySpinner, lineSpinner;
    protected EditText matrialEditText, customEditText, orderEditText, sequenceEditText;
    protected Button printButton, returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_print);
        initView();//初始化控件
    }

    protected void initView() {
        originSpinner = (Spinner) findViewById(R.id.sp_product_origin);
        categorySpinner = (Spinner) findViewById(R.id.sp_product_category);
        lineSpinner = (Spinner) findViewById(R.id.sp_product_line);
        matrialEditText = (EditText) findViewById(R.id.et_matrial_code);
        customEditText = (EditText) findViewById(R.id.et_custom_code);
        orderEditText = (EditText) findViewById(R.id.et_order_no);
        sequenceEditText = (EditText) findViewById(R.id.et_product_sequence_number);
        printButton = (Button) findViewById(R.id.btn_print_one_item);
        returnButton = (Button) findViewById(R.id.btn_return_to_activity);
        printButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_print_one_item) {
            printOneItem();
        } else if (v.getId() == R.id.btn_return_to_activity) {
            returnToMainActivity();
        }
    }

    //打印按钮事件
    protected void printOneItem() {
        //1、获取参数值
        origin = originSpinner.getSelectedItem().toString().trim();
        category = categorySpinner.getSelectedItem().toString().trim();
        line = lineSpinner.getSelectedItem().toString().trim();
        code = matrialEditText.getText().toString().trim();
        custom = customEditText.getText().toString().trim();
        sDate = StringUtil.systemDate5(new Date()).trim();
        order = orderEditText.getText().toString().trim();
        number = sequenceEditText.getText().toString().trim();
        //2 判断输入是否合法
        if (origin.isEmpty() ||
                category.isEmpty() ||
                line.isEmpty() ||
                code.isEmpty() ||
                custom.isEmpty() ||
                order.isEmpty() ||
                number.isEmpty()) {
            Toast.makeText(this, "请输入完整", Toast.LENGTH_LONG).show();
            return;
        } else {
            //3、保存并转成二维码值  不包括订单号
            String value = StringUtil.StringMerger(origin, category, line, code, custom, sDate);
            //需要补打印二维码的顺序号
            value = value + number;
            Log.i("Print Label", value);
            //4、打印
            EventBus.getDefault().post(new ComEvent(PrintLabel.ZebraLabel(value), ComEvent.ACTION_PRINT));
        }
    }

    //返回按钮
    protected void returnToMainActivity() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("item", "补打印完成");
        //设置返回数据
        QrcodePrintActivity.this.setResult(2, intent);
        //关闭Activity
        QrcodePrintActivity.this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            //返回到主页面
            //数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("item", "补打印完成");
            //设置返回数据
            QrcodePrintActivity.this.setResult(2, intent);
            //关闭Activity
            QrcodePrintActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
