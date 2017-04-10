package app.android.mingjiang.com.logshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * 用于显示程序崩溃出错信息。
 */
public class ShowLogActivity extends Activity {

    TextView textView = null;
    Button restartBtn,mRestartBtnUpload;
    String mErrorMessage,mBaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        textView = (TextView)findViewById(R.id.showError);
        restartBtn = (Button)findViewById(R.id.restartApp);
        mRestartBtnUpload=(Button)findViewById(R.id.restartAppUpLoad);

        Intent intent = getIntent();
        mErrorMessage = intent.getStringExtra("error");
        mBaseUrl = intent.getStringExtra("base_url");


        final String appPacakge = intent.getStringExtra("package");
        textView.setText(mErrorMessage);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherActivity(ShowLogActivity.this, appPacakge);
            }
        });
        mRestartBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadErrorMessage(mErrorMessage);
                startOtherActivity(ShowLogActivity.this, appPacakge);
            }
        });
    }

    //将原出错APP重新启动一下。
    private void startOtherActivity(Context context,String pacakge) {
        Intent intent = context.getPackageManager().
                getLaunchIntentForPackage(pacakge);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(),
                    "您没有安装显示出错信息应用，请本地查看出错信息,联系开发人员!",
                    Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 上传出错信息
     */
    private void upLoadErrorMessage(String errorMessage){
        Toast.makeText(ShowLogActivity.this, "正在上传出错信息", Toast.LENGTH_SHORT).show();
        String equipment_name= URLEncoder.encode("一体机");// 中文数据需要经过URL编码
        String equipment_address=getLocalHostIp();//获取IP
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String error_time = formatter.format(curDate);
        String errortext=URLEncoder.encode(errorMessage);//中文数据需要经过URL编码
        String url=mBaseUrl+"api/interface/public/common_api.android_error_log/error_log";
        String submitData="equipment_name="+equipment_name+"&equipment_address="+equipment_address+"&error_time="+error_time+"&error_text="+errortext;
        System.out.println("------submitdata:" + submitData);
        new MyAsyncTask(new MyAsyncTask.OnResultListener() {
            @Override
            public void onResultListener(boolean result) {
                if (result==true){
                    Toast.makeText(ShowLogActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShowLogActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(url, submitData);
    }
    /**
     * 获得Ip地址
     */
    public String getLocalHostIp()
    {
        String ipaddress = "";
        try
        {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements())
            {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements())
                {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()&&ip.getHostAddress().length()<20)//筛选出ipv4的ip。
                    {
                        return ipaddress =ip.getHostAddress();
                    }
                }

            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return ipaddress;

    }
}
