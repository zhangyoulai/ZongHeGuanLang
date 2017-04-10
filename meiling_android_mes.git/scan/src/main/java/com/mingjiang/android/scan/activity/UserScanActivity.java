package com.mingjiang.android.scan.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.service.com.ComServiceUtils;
import com.mingjiang.android.base.api.CustomerDialogAPi;
import com.mingjiang.android.base.util.SharedPrefsUtil;
import com.mingjiang.android.base.api.SuperToastApi;
import com.mingjiang.android.scan.R;
import com.mingjiang.android.scan.ScanApp;
import com.mingjiang.android.scan.entity.SessionObj;

import de.greenrobot.event.EventBus;
import meiling.mingjiang.instructionpdf.Activity.InstructionPdfActivity;
import rx.functions.Action1;

import com.mingjiang.android.onoffline.activity.OnOffLineActivity;

/**
 * 备注：用户工牌扫描。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class UserScanActivity extends Activity {

    protected static final String TAG = UserScanActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scan);
        //TODO 测试使用，用后删除下一行
//        SharedPrefsUtil.setValue(this, Constants.SCAN_USER_CODE, "GH:atbjQ5BebwI+hHnqscMwlg==");
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String userCode = SharedPrefsUtil.getString(this, Constants.SCAN_USER_CODE);
        if("".equals(userCode) || userCode == null){//本地文件不存在，则启动扫描服务
            //启动扫描服务
            ComServiceUtils.startService(this, ComEvent.ACTION_USER_SCAN);
        }else { //本地文件存在，则提示是否重新扫描
            ScanApp.userCode = userCode;
            showOperDialog();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
//        ComServiceUtils.stopService(this);//关闭服务
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        ComServiceUtils.stopService(this);//关闭服务
    }
    //返回操作
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_USER_SCAN) {
            String userCode = event.getMessage();
            if (userCode.startsWith("GH")){
                Log.d("显示用户编码信息:", userCode);//显示用户编码数据
                SharedPrefsUtil.setValue(this, Constants.SCAN_USER_CODE, userCode);   //将userCode保存本地
                ScanApp.userCode = userCode;
                checkUserCode();
            }
        }else if(event.getActionType() == ComEvent.ACTION_CHECK_USER){
            SessionObj sessionObj = (SessionObj)event.getObjectMsg();
            if("".equals(sessionObj.error) || sessionObj.error == null) {
                ScanApp.sessionId = sessionObj.session_id;
                SharedPrefsUtil.setValue(this, Constants.SESSION_ID, sessionObj.session_id);//将SessionId保存本地
                startOperActivity();
            }else {
                SuperToastApi.showInitSuperToast(this, "该用户不具备岗位权限！");
            }
        }else if(event.getActionType() == ComEvent.ACTION_CHECK_USER_ERROR){//数据校验出问题
            String errorMsg = event.getMessage();
            SuperToastApi.showInitSuperToast(this, errorMsg);
        }
    }
    /**
     * 显示选择对话框。
     */

    private void showOperDialog(){
        String title = "用户已存在";
        boolean cancleable = true;//是否可以取消
        String message = "该用户已存在，是否重新扫描？";
        String leftBtn = "是";
        String rightBtn = "否";
        String logoutBtn = "清除";
        CustomerDialogAPi.showTitleDialog(this, title, cancleable, message, leftBtn, rightBtn, logoutBtn,
                new ClickOper());

    }
    class ClickOper implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){//是
                ComServiceUtils.startService(UserScanActivity.this, ComEvent.ACTION_USER_SCAN);
            }else if(which == DialogInterface.BUTTON_NEGATIVE){//否
                checkUserCode();
            }else if(which == DialogInterface.BUTTON_NEUTRAL){
                SharedPrefsUtil.setValue(UserScanActivity.this, Constants.SCAN_USER_CODE, "");
                //开启串口服务
                ComServiceUtils.startService(UserScanActivity.this, ComEvent.ACTION_USER_SCAN);
            }
        }
    }
    /**
     * 启动功能操作。
     */
    private void startOperActivity(){
        ComServiceUtils.stopService(this);
        Intent intent = null;
        if(Constants.OPER_INSTRUCTION.equals(ScanApp.functionType)){             //岗位作业指导书
            intent = new Intent(this,InstructionPdfActivity.class);
            intent.putExtra(Constants.SESSION_ID,ScanApp.sessionId);
            intent.putExtra(Constants.SCAN_POST_CODE, ScanApp.postCode);
        }else if(Constants.PRIINT_QR_CODE.equals(ScanApp.functionType)){         //打印二维码
            //TODO:
        }else if(Constants.QUALITY_INSPECTION.equals(ScanApp.functionType)){     //质量检测
            //TODO:
        }else if(Constants.EQUIPMENT_MONITOR.equals(ScanApp.functionType)){      //设备监控
            //TODO:
        }else if(Constants.PRODUCT_MONITOR.equals(ScanApp.functionType)){        //生产监控
            //TODO:
        }else if(Constants.EQUIPMENT_INSPECTION.equals(ScanApp.functionType)){   //设备点检
            //TODO:
        }else if(Constants.FIXE_SCAN_CODE_GUN_PROOF.equals(ScanApp.functionType)){//固定扫码枪防错
            //TODO:
        }else if(Constants.MATERIALS_MANAGEMENT.equals(ScanApp.functionType)){   //物料管理
            //TODO:
        }else if(Constants.ON_OFF_LINE.equals(ScanApp.functionType)){            //上下线
            intent = new Intent(this,OnOffLineActivity.class);
            intent.putExtra(Constants.SESSION_ID,ScanApp.sessionId);
            intent.putExtra(Constants.SCAN_POST_CODE, ScanApp.postCode);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 校验用户是否正确。
     */
    private void checkUserCode(){
        final String  userCode = ScanApp.userCode;
        ScanApp.getApp().getNetService(this).isRightUser(userCode,ScanApp.postCode).subscribe(new Action1<SessionObj>() {
            @Override
            public void call(SessionObj retValue) {
                //如果返回true，则为该岗位用户，否则返回false
                EventBus.getDefault().post(new ComEvent(retValue, ComEvent.ACTION_CHECK_USER));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "岗位编号为：" + ScanApp.postCode + "-用户编号为："+userCode+"-数据校验失败！";
                Log.i(TAG,sendMsg);
                Log.d(sendMsg, throwable.getMessage());
                EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.ACTION_CHECK_USER_ERROR));
            }
        });
    }
}