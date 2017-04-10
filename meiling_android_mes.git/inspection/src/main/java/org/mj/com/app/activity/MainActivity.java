package org.mj.com.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.service.com.ComServiceUtils;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.api.SuperToastApi;
import org.mj.com.app.InspectionApp;
import org.mj.com.app.bean.SessionObj;
import org.mj.com.app.utils.NetCheckUtils;
import org.mj.com.app.utils.SharedPrefsUtil;
import org.mj.com.inspection.R;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MainActivity extends Activity {


//    EditText mEdittext1, mEdittext2;
//    Button mButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //注册eventbus
        EventBus.getDefault().register(this);
        initForIsNetWork();
        initViewForFind();
        initViewForEvent();
    }

    private void initForIsNetWork() {
        if (!NetCheckUtils.isNetworkAvaiable(MainActivity.this) && !NetCheckUtils.isWifiActive(MainActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
//          builder.setIcon(R.drawable.notification);
            builder.setTitle("网络异常");
            String message = "您的网络连接异常，请检查网络后重试";
            builder.setMessage(message);
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void initViewForFind() {
//        mEdittext1 = (EditText) findViewById(R.id.activity_main_edittext1);
//        mEdittext2 = (EditText) findViewById(R.id.activity_main_edittext2);
//        mButton = (Button) findViewById(R.id.activity_main_button1);
    }

    private void initViewForEvent() {
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "正在登陆", Toast.LENGTH_SHORT).show();
//                if ("123".equals(mEdittext1.getText().toString()) && "123".equals(mEdittext2.getText().toString())) {
//                    Intent intent = new Intent(MainActivity.this, CheckActivity.class);
//                    intent.putExtra("username", mEdittext1.getText().toString());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(MainActivity.this, "登录失败，请重新扫码或输入工号", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    /**
     * 显示选择对话框。
     *
     * @param postCode
     */
    private void showSelectDialog(final Context context, final String postCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.drawable.meiling);
        builder.setTitle("选择对话框");
        builder.setMessage("该用户已存在，是否重新扫描？");
        builder.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //开启服务，读取用户
                        ComServiceUtils.startService(MainActivity.this, ComEvent.INSPECTION_USERSCAN);
                    }
                });
        builder.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startUserScanActivity(postCode);
                    }
                });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String postCode = SharedPrefsUtil.getString(this, SharedPrefsUtil.POST_CODE);
        if ("".equals(postCode) || postCode == null) {//本地文件不存在，则启动扫描服务
            //开启服务，读取用户
            ComServiceUtils.startService(this, ComEvent.INSPECTION_USERSCAN);
        } else { //本地文件存在，则提示是否重新扫描
            showSelectDialog(this, postCode);
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

    }
    /**
     * 启动产品扫描界面。
     *
     * @param userCode
     */
    public void startUserScanActivity(String userCode) {
        InspectionApp.getApp().getNetService(this).isUser(userCode, "{\"verify\":0}").subscribe(new Action1<SessionObj>() {
            @Override
            public void call(SessionObj retValue) {
                //如果返回true，则为该岗位用户，否则返回false
                EventBus.getDefault().post(new ComEvent(retValue, ComEvent.CHECK_USER));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "数据校验失败！";
                Log.d(sendMsg, throwable.getMessage());
                EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.CHECK_USER_MISS));
            }
        });
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.CHECK_USER) {
            SessionObj sessionObj = (SessionObj) event.getObjectMsg();
            if (sessionObj.login!=null&&!"".equals(sessionObj.login)) {
                InspectionApp.sessionId = sessionObj.session_id;
                com.mingjiang.android.base.util.SharedPrefsUtil.setValue(this, Constants.SESSION_ID, sessionObj.session_id);//将SessionId保存本地
                Intent intent=new Intent(MainActivity.this,CheckActivity.class);
                intent.putExtra("username",sessionObj.login);
                startActivity(intent);
            } else {
                SuperToastApi.showInitSuperToast(this, "该用户不具备岗位权限！");
            }
        }else if (event.getActionType() == ComEvent.CHECK_USER_MISS) {//数据校验出问题
            Toast.makeText(MainActivity.this, "用户校验出错", Toast.LENGTH_SHORT).show();
        } else if (event.getActionType() == ComEvent.ACTION_CHECK_USER_ERROR) {//数据校验出问题
            String errorMsg = event.getMessage();
            SuperToastApi.showInitSuperToast(this, errorMsg);
            ComServiceUtils.stopService(this);//关闭串口服务
            this.finish();//退出该界面
        }else if (event.getActionType() == ComEvent.INSPECTION_USERSCAN ) {
            String postCode = event.getMessage();
            Log.d("show post code message:", postCode);//显示岗位编码数据
            SharedPrefsUtil.setValue(this, SharedPrefsUtil.POST_CODE, postCode);//将PostCode保存本地
            startUserScanActivity(postCode);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

