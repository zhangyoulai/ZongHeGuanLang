package meiling.mingjiang.appupdata;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.mingjiang.android.base.util.Config;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import app.android.mingjiang.com.herilyalertdialog.HerilyAlertDialog;


/**
 * Created by kouzeping on 2016/3/14.
 * email：kouzeping@shmingjiang.org.cn
 */
public class UpDataUtils {
    private final String TAG = this.getClass().getName();
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int DOWN_ERROR = 4;
    private ProgressDialog mPd;
    private UpdataInfo mInfo;
    private Context mContext;
    private String mLocalVersion;

    public UpDataUtils(Context mContext) {
        this.mContext = mContext;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_NONEED:
                    Toast.makeText(mContext, "当前已是最新版本",
                            Toast.LENGTH_SHORT).show();
                    break;
                case UPDATA_CLIENT:
                    //对话框通知用户升级程序
                    showUpdataDialog();
                    break;
                case GET_UNDATAINFO_ERROR:
                    //服务器超时
                    Toast.makeText(mContext, "没有更新信息", Toast.LENGTH_SHORT).show();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(mContext, "下载新版本失败", Toast.LENGTH_SHORT).show();
                    mPd.dismiss(); //结束掉进度条对话框
                    break;
            }
        }
    };

    public void checkUpData() throws Exception {
        try {
            mLocalVersion = getVersionName();
            CheckVersionTask cv = new CheckVersionTask();
            new Thread(cv).start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {

        //getPackageName()是你当前类的包名，0代表是获取版本信息

        PackageManager packageManager = mContext.getPackageManager();

        PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),

                0);
        return packInfo.versionName;

    }
    public class CheckVersionTask implements Runnable {
        InputStream is;
        public void run() {
            try {
                String path = Config.getBaseUrl(mContext)+"api/interface/public/common_api.client_app_upgrade/upgrade";
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("POST");
                //设置请求参数
                OutputStream os = conn.getOutputStream();
                String param = new String();
                param = "client_type=IntegratedMachine";
                os.write(param.getBytes());

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    is = conn.getInputStream();
                }
                mInfo = UpdataInfoParser.getUpdataInfo(is);
                if (mInfo.getVersion().equals(mLocalVersion)) {
                    Log.i(TAG, "版本号相同");
                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);
                    // LoginMain();
                } else {
                    Log.i(TAG, "版本号不相同 ");
                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }


    /**
	 *
	 * 弹出对话框通知用户更新程序
	 *
	 * 弹出对话框的步骤：
	 *  1.创建alertDialog的builder.
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮
	 *  3.通过builder 创建一个对话框
	 *  4.对话框show()出来
	 */
    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new HerilyAlertDialog.Builder(mContext);
        builer.setTitle("版本升级");
        builer.setMessage("新版本：" + mInfo.getVersion() + "将替换现有版本");
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk,更新");
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    /**
	 * 从服务器中下载APK
	 */
    protected void downLoadApk() {
            //进度条对话框
        mPd = new  ProgressDialog(mContext);
        mPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPd.setMessage("正在下载更新");
        mPd.show();

        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
//                    model=common_api.client_app_upgrade
//                    field= id= filename_field=
                    Map<String,String > map=new HashMap<String, String>();
                    map.put("model","common_api.client_app_upgrade");
                    map.put("field", mInfo.getField());
                    map.put("id",mInfo.getId());
                    map.put("filename_field",mInfo.getFilename_field());
                    File file = DownLoadManager.getFileFromServer(Config.getBaseUrl(mContext)+"web/binary/saveas",map, mPd);
                    Thread.sleep(3000);
                    installApk(file);
                    mPd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }

            }
        }).start();
    }
    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}

