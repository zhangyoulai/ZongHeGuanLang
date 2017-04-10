package com.mingjiang.android.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mingjiang.android.app.AppConfig;
import com.mingjiang.android.app.AppContext;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.OnOffLineData;
import com.mingjiang.android.base.api.SuperToastApi;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.service.com.ComServiceUtils;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.SharedPrefsUtil;


import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：上线/下线处理界面。
 * 作者：wangzs on 2016/2/19 09:39
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class OnOffLineActivity extends Activity {

    private TextView logoutText = null;
    private EditText serialNumberEdit = null;
    private Spinner operationSpinner = null;
    private EditText postCodeEdit = null;
    private EditText reasonEdit = null;
    private EditText mOnOffCodeEdit = null;
    private Button submitOnoffLineBtn = null;
    OnOffLineData onOffLineData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onoff);
        ComServiceUtils.startService(this, ComEvent.ONOFF_FRIDGE_SCAN);
        EventBus.getDefault().register(this);
        onOffLineData = new OnOffLineData();
        getIntentExtra();
        initViewForFind();
        initViewForEvent();

    }

    /**
     * 获取传入参数。
     */
    private void getIntentExtra() {
        Intent intent = getIntent();
        String sessionId = intent.getStringExtra(Constants.SESSION_ID);
        String postCode = intent.getStringExtra(Constants.SCAN_POST_CODE);
        AppConfig.sessionId = sessionId;
        AppConfig.postCode = postCode;
    }

    private void initViewForEvent() {
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitOnoffLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录上下线点编号
                SharedPrefsUtil.setValue(OnOffLineActivity.this, SharedPrefsUtil.LINECODE, mOnOffCodeEdit.getText().toString());
                long itemId = operationSpinner.getSelectedItemId();
                onOffLineData.post_code = mOnOffCodeEdit.getText().toString();
                onOffLineData.reason = reasonEdit.getText().toString();
                onOffLineData.serial_number = serialNumberEdit.getText().toString();
                if ("".equals(onOffLineData.serial_number)) {
                    SuperToastApi.showInitSuperToast(OnOffLineActivity.this, AppConfig.FRIDGE_NOT_SCAN);
                } else {
                    if (itemId == 0) {//上线
                        onOffLineData.operation = OnOffLineData.ON_LINE;
                        submitOnOffLine(onOffLineData);
                    } else {//下线
                        if ("".equals(onOffLineData.reason)) {
                            SuperToastApi.showInitSuperToast(OnOffLineActivity.this, AppConfig.OFF_LINE_TIP);
                        } else {
                            onOffLineData.operation = OnOffLineData.OFF_LINE;
                            submitOnOffLine(onOffLineData);
                        }
                    }
                }
            }
        });
    }

    private void initViewForFind() {
        logoutText = (TextView) findViewById(R.id.logout);

        serialNumberEdit = (EditText) findViewById(R.id.serial_number);
        mOnOffCodeEdit = (EditText) findViewById(R.id.onoff_code);
        operationSpinner = (Spinner) findViewById(R.id.operation);
        postCodeEdit = (EditText) findViewById(R.id.post_code);
        postCodeEdit.setText(AppConfig.postCode);
        postCodeEdit.setFocusable(false);
        reasonEdit = (EditText) findViewById(R.id.reason);
        submitOnoffLineBtn = (Button) findViewById(R.id.submit_onoff_line);

    }

    /**
     * 提交上线/下线数据到服务器。
     *
     * @param onOffLineData
     */
    private void submitOnOffLine(OnOffLineData onOffLineData) {
        Map<String, String> dataMap = onOffLineData.setData();
        AppContext.getApp().getNetService(this).submitOnOffLine(dataMap, AppContext.sessionId).subscribe(new Action1<String>() {
            @Override
            public void call(String fridgeResponse) {
                //加载数据成功
                if (fridgeResponse.equalsIgnoreCase(OnOffLineData.RESPONSE_SUCCESS)) {
                    EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_ONOFF_SUCCESS));
                } else if (fridgeResponse.equalsIgnoreCase(OnOffLineData.SERIAL_NUMBER_ERROR)) {
                    EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_ONOFF_ERROR_SERIAL_NUM));
                } else {
                    EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_ONOFF_FAILUE));
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_ONOFF_FAILUE));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String lineCode = SharedPrefsUtil.getString(OnOffLineActivity.this, SharedPrefsUtil.LINECODE);
        if (!"".equals(lineCode)) {
            mOnOffCodeEdit.setText(lineCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComServiceUtils.stopService(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ComEvent event) {
        if (ComEvent.ONOFF_FRIDGE_SCAN == event.getActionType()) {//扫描电冰箱二维码成功
            serialNumberEdit.setText(event.getMessage());//扫描成功设置显示值
        } else if (ComEvent.ACTION_ONOFF_SUCCESS == event.getActionType()) {//数据提交成功
            SuperToastApi.showInitSuperToast(OnOffLineActivity.this, AppConfig.SUBMIT_SUCCESS);
            serialNumberEdit.setText("");
            reasonEdit.setText("");
        } else if (ComEvent.ACTION_ONOFF_FAILUE == event.getActionType()) {//数据提交失败
            SuperToastApi.showInitSuperToast(OnOffLineActivity.this, AppConfig.SUBMINT_FAILUE);
        } else if (ComEvent.ACTION_ONOFF_ERROR_SERIAL_NUM == event.getActionType()) {//生产流水号错误
            SuperToastApi.showInitSuperToast(OnOffLineActivity.this, AppConfig.ERROR_SERIAL_NUMBER);
        }
    }
}
