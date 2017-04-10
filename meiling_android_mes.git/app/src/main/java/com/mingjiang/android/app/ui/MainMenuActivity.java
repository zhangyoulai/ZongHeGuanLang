package com.mingjiang.android.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.update.UpDataUtils;
import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.base.util.Constants;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

/**
 * 目前还不确定业务是不是都要进行岗位扫描和人员扫描，服务端业务对各个功能点出来都一样，所以暂时不统一处理。
 * 如果将来确定后再统一处理。
 */
public class MainMenuActivity extends Activity implements CircleLayout.OnItemSelectedListener,
        CircleLayout.OnItemClickListener, CircleLayout.OnRotationFinishedListener, CircleLayout.OnCenterClickListener {
    TextView selectedTextView;
    CircleLayout circleMenu;
    UpDataUtils mUpDataUtils;
    String log = "MainMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUpDataUtils = new UpDataUtils(MainMenuActivity.this);
        try {
            mUpDataUtils.checkUpData();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(log, e.getLocalizedMessage());
        }
        initView();
        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);
        circleMenu.setOnRotationFinishedListener(this);
        circleMenu.setOnCenterClickListener(this);
        selectedTextView.setText(((CircleImageView) circleMenu
                .getSelectedItem()).getName());
    }

    private void initView() {
        selectedTextView = (TextView) findViewById(R.id.selected_textView);
        circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
    }

    /**
     * 点击事件。
     *
     * @param view
     */
    @Override
    public void onItemClick(View view) {
        String name = null;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }
        selectedTextView.setText(name);
        //执行页面跳转
        getForwardActivityName(name);
    }

    /**
     * 点击中间部位事件：用于设置URL。
     */
    @Override
    public void onCenterClick() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);

        EditText edittext_Msg = (EditText) view.findViewById(R.id.dialog_view_edittext);
        EditText editTextBaud1 = (EditText) view.findViewById(R.id.dialog_view_edittext1);
        EditText editTextBaud2 = (EditText) view.findViewById(R.id.dialog_view_edittext2);
        EditText editTextBaud3 = (EditText) view.findViewById(R.id.dialog_view_edittext3);
        EditText editTextBaud4 = (EditText) view.findViewById(R.id.dialog_view_edittext4);

        Button updataBtn = (Button) view.findViewById(R.id.dialog_button_checkupdata);
        updataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mUpDataUtils.checkUpData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edittext_Msg.setText(Config.getBaseUrl(this));
        editTextBaud1.setText(Config.getBaseBaud1(this));
        editTextBaud2.setText(Config.getBaseBaud2(this));
        editTextBaud3.setText(Config.getBaseBaud3(this));
        editTextBaud4.setText(Config.getBaseBaud4(this));

        setEdittestStyle(edittext_Msg);
        setEdittestStyle(editTextBaud1);
        setEdittestStyle(editTextBaud2);
        setEdittestStyle(editTextBaud3);
        setEdittestStyle(editTextBaud4);

        builder.setView(view);
        builder.setTitle("IP及串口配置");
        builder.setPositiveButton("确定", new ConfigListener(edittext_Msg, editTextBaud1, editTextBaud2, editTextBaud3, editTextBaud4, true));
        builder.setNegativeButton("取消", new ConfigListener(edittext_Msg, editTextBaud1, editTextBaud2, editTextBaud3, editTextBaud4, false));
        builder.show();
    }

    private void setEdittestStyle(EditText editText) {
        editText.setTextColor(this.getResources().getColor(com.mingjiang.android.base.R.color.alertex_dlg_edit_text_color));
        editText.setBackgroundDrawable(this.getResources().getDrawable(com.mingjiang.android.base.R.drawable.herily_alertex_dlg_textinput_drawable));
    }

    /**
     * 设置URL事件处理。
     */
    class ConfigListener implements DialogInterface.OnClickListener {

        private EditText editText = null;
        private EditText editTextBaud1 = null;
        private EditText editTextBaud2 = null;
        private EditText editTextBaud3 = null;
        private EditText editTextBaud4 = null;
        private boolean isConfig = false;

        ConfigListener(EditText editText, EditText editTextBaud1, EditText editTextBaud2, EditText editTextBaud3, EditText editTextBaud4, boolean isConfig) {
            this.editText = editText;
            this.editTextBaud1 = editTextBaud1;
            this.editTextBaud2 = editTextBaud2;
            this.editTextBaud3 = editTextBaud3;
            this.editTextBaud4 = editTextBaud4;
            this.isConfig = isConfig;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (isConfig) {
                String baseUrl = editText.getText().toString();
                Config.setBaseUrl(MainMenuActivity.this, baseUrl);

                String baseBaud1 = editTextBaud1.getText().toString();
                Config.setBaseBaud1(MainMenuActivity.this, baseBaud1);

                String baseBaud2 = editTextBaud2.getText().toString();
                Config.setBaseBaud2(MainMenuActivity.this, baseBaud2);

                String baseBaud3 = editTextBaud3.getText().toString();
                Config.setBaseBaud3(MainMenuActivity.this, baseBaud3);

                String baseBaud4 = editTextBaud4.getText().toString();
                Config.setBaseBaud4(MainMenuActivity.this, baseBaud4);
            }
            dialog.cancel();
        }
    }

    /**
     * 选择事件。
     *
     * @param view
     */
    @Override
    public void onItemSelected(View view) {
        final String name;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        } else {
            name = null;
        }
        selectedTextView.setText(name);

    }
    /**
     * 旋转操作。
     *
     * @param view
     */
    @Override
    public void onRotationFinished(View view) {
        final String name;
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        } else {
            name = null;
        }
        selectedTextView.setText(name);
    }

    /**
     * 执行页面跳转
     *
     * @param name 由onItemClick监听到的点击事件传来的，电机item的名称
     */
    public void getForwardActivityName(String name) {
        Intent intent = null;
        if (getString(R.string.oper_instruction).equals(name)) {//岗位指导书
            intent = new Intent(MainMenuActivity.this, PostScanActivity.class);
            intent.putExtra(Constants.FUNCTION_TYPE_NAME, Constants.OPER_INSTRUCTION);
        } else if (getString(R.string.print_qr_code).equals(name)) {//打印二维码
            intent = new Intent(MainMenuActivity.this, QrcodeMainActivity.class);
        } else if (getString(R.string.quality_inspection).equals(name)) {//质量检测
            intent = new Intent(MainMenuActivity.this, InspectionMainActivity.class);
        } else if (getString(R.string.product_monitor).equals(name)) {//生产监控
            intent = new Intent(MainMenuActivity.this, SpectacularsActivity.class);
        } else if (getString(R.string.line_storage).equals(name)) {//点检记录
            intent = new Intent(MainMenuActivity.this, LineStorageLoginActivity.class);
        } else if (getString(R.string.materials_management).equals(name)) {//物料管理
            intent = new Intent(MainMenuActivity.this, MaterialLoginActivity.class);
        } else if (getString(R.string.onoff_line).equals(name)) {//上下线
            intent = new Intent(MainMenuActivity.this, PostScanActivity.class);
            intent.putExtra(Constants.FUNCTION_TYPE_NAME, Constants.ON_OFF_LINE);
        } else if (getString(R.string.print_delivery_notes).equals(name)) {//打印送货单
            intent = new Intent(MainMenuActivity.this, DeliveryNotesActivity.class);
        } else if (getString(R.string.compare_code).equals(name)) {
            intent = new Intent(MainMenuActivity.this, CompareActivity.class);
        }
        if (intent != null) {
            this.startActivity(intent);
        }
    }
}