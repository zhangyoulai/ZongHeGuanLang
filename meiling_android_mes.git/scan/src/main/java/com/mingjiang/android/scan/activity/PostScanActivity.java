package com.mingjiang.android.scan.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.api.CustomerDialogAPi;
import com.mingjiang.android.base.service.com.ComServiceUtils;
import com.mingjiang.android.base.util.SharedPrefsUtil;
import com.mingjiang.android.scan.ScanApp;
import de.greenrobot.event.EventBus;

import com.mingjiang.android.scan.R;

/**
 * 备注：岗位扫描。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class PostScanActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_scan);
        EventBus.getDefault().register(this);
        //TODO 测试使用，用后删除下一行
//        SharedPrefsUtil.setValue(this, Constants.SCAN_POST_CODE, "321");
        getFunctionType();
    }

    //获取当前功能点类型
    private void getFunctionType(){
        Intent intent = getIntent();
        String functionType = intent.getStringExtra(Constants.FUNCTION_TYPE_NAME);
        ScanApp.functionType = functionType;//获取当前功能点类型

    }
    /**
     * 显示选择对话框。
     *
     */
    private void showOperDialog(){
        String title = "扫描选择";
        boolean cancleable = true;//是否可以取消
        String message = "该岗位编码已存在，是否重新扫描？";
        String leftBtn = "是";
        String rightBtn = "否";
        String logoutBtn = "清除";
        CustomerDialogAPi.showTitleDialog(this, title,cancleable, message, leftBtn, rightBtn,logoutBtn,
                new ClickOper());

    }

    class ClickOper implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){//是
                ComServiceUtils.startService(PostScanActivity.this, ComEvent.ACTION_POST_SCAN);
            }else if(which == DialogInterface.BUTTON_NEGATIVE){//否
                startUserScanActivity();
            }else if(which == DialogInterface.BUTTON_NEUTRAL){
                SharedPrefsUtil.setValue(PostScanActivity.this, Constants.SCAN_POST_CODE, "");
                ComServiceUtils.startService(PostScanActivity.this, ComEvent.ACTION_POST_SCAN);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        String postCode = SharedPrefsUtil.getString(this, Constants.SCAN_POST_CODE);
        if("".equals(postCode) || postCode == null){//本地文件不存在，则启动扫描服务
            //启动扫描服务
            ComServiceUtils.startService(this, ComEvent.ACTION_POST_SCAN);
        }else { //本地文件存在，则提示是否重新扫描
            ScanApp.postCode = postCode;
            //showSelectDialog(this,postCode);
            showOperDialog();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ComServiceUtils.stopService(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ComServiceUtils.stopService(this);
    }

    public void onEventMainThread(ComEvent event) {

        if (event.getActionType() == ComEvent.ACTION_POST_SCAN) {
            //过滤掉其他串口的数据，保留二号口的数据
            String postCode = event.getMessage();
            if (postCode.startsWith("gw")){
                postCode = postCode.replace("\r","");//不知道为啥多了个\r换行
                Log.i("show post code message:",postCode);                    //显示岗位编码数据
                SharedPrefsUtil.setValue(this, Constants.SCAN_POST_CODE, postCode);   //将PostCode保存本地
                ScanApp.postCode = postCode;                                    //设置全局变量
                startUserScanActivity();
            }
        }
    }

    /**
     * 启动用户扫描界面。
     */
    public void startUserScanActivity(){
        Intent intent = new Intent(this,UserScanActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        ComServiceUtils.stopService(this);
        super.onBackPressed();
}
}
