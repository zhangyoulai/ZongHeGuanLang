package com.mingjiang.android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.mingjiang.android.app.bean.Pol1;
import com.mingjiang.android.app.bean.Pol2;
import com.mingjiang.android.app.bean.Pol3;
import com.mingjiang.android.app.bean.Pol4;
import com.mingjiang.android.app.bean.PrintLog;
import com.mingjiang.android.app.bean.SendPLCMsg;
import com.mingjiang.android.app.bean.SessionObj;
import com.mingjiang.android.app.bean.WeldPower;
import com.mingjiang.android.app.bean.WeldThick;
import com.mingjiang.android.app.bean.WeldTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 */
public class AppConfig {

    private final static String APP_CONFIG = "config";

    public final static String CONF_COOKIE = "cookie";

    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

    public static String dataBaseName = "allib_info.db";

    public Map<String, String> materialMap = null;

    // 存放所有R3码 检查重复
    public static ArrayList<String> r3CodeList = new ArrayList<>();

    public static ArrayList<SendPLCMsg> plcMsgsList = new ArrayList<>();

    public static ArrayList<PrintLog> printLogArrayList = new ArrayList<>();

    //读取日计划
    public final static String URL_DAILY_PLAN =
            "/api/interface/public/plan_manage.daily_plan/process_serial_element";
    //报废订单 流水号
    public final static String URL_SCRAPPED_ORDER =
            "/api/interface/public/quality_manage.scrapped_product/print_code";
    //获取二维码
    public final static String URL_GET_QRCODE =
            "/api/interface/public/process_control.process/print_code";
    //获取产品的URL
    public final static String PRODUCT_URL = "/loadproduct/";
    //上传检测结果时的URL
    public final static String RESULT_URL = "/loadtestdetail";
    //上传订单号+二维码的URL
    public final static String URL_UPLOAD_CODE =
            "api/interface/json/public/process_control.process/trace_process";

    public static String POST_CODE = "post_code";//可以将岗位编号进行保存操作
    public static String CHECK_POST_CODE = "check_post_code";//可以将检验岗位编号进行保存操作
    public static String CHECK_CLASSES_CODE = "user_code";//可将用户编码进行保存


    public static WeldPower weldPower = new WeldPower();//焊接能量

    public static WeldThick weldThick = new WeldThick();//焊接壁厚

    public static WeldTime weldTime = new WeldTime();//焊接时间

    public static Pol1 pol1 = new Pol1();
    public static Pol2 pol2 = new Pol2();
    public static Pol3 pol3 = new Pol3();
    public static Pol4 pol4 = new Pol4();

    public static String userCode = "";        //岗位ID
    public static String postCode = "";        //员工ID
    public static String functionType = "";    //功能点类型
    public static String sessionId = "";       //session信息

    public static final String OFF_LINE_TIP = "下线需要填写下线原因!";//
    public static final String FRIDGE_NOT_SCAN = "冰箱二维码为空，重新扫描!";//冰箱二维码为空，重新扫描!
    public static final String SUBMIT_SUCCESS = "提交信息成功！";//提交上线/下线信息成功！
    public static final String SUBMINT_FAILUE = "提交信息失败！";//提交上线/下线信息失败！
    public static final String ERROR_SERIAL_NUMBER = "生产流水号错误！";//生产流水号错误！

    public static final String KEY_LOAD_IMAGE = "KEY_LOAD_IMAGE";
    public static final String KEY_NOTIFICATION_ACCEPT = "KEY_NOTIFICATION_ACCEPT";
    public static final String KEY_NOTIFICATION_SOUND = "KEY_NOTIFICATION_SOUND";
    public static final String KEY_NOTIFICATION_VIBRATION = "KEY_NOTIFICATION_VIBRATION";
    public static final String KEY_NOTIFICATION_DISABLE_WHEN_EXIT = "KEY_NOTIFICATION_DISABLE_WHEN_EXIT";
    public static final String KEY_CHECK_UPDATE = "KEY_CHECK_UPDATE";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";

    public static final String KEY_TWEET_DRAFT = "KEY_TWEET_DRAFT";
    public static final String KEY_NOTE_DRAFT = "KEY_NOTE_DRAFT";

    public static final String KEY_FRITST_START = "KEY_FRIST_START";

    public static final String KEY_NIGHT_MODE_SWITCH = "night_mode_switch";

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "MEILING"
            + File.separator + "ml_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "MEILING"
            + File.separator + "download" + File.separator;

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String get(String key) {
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            // 读取files目录下的config
            // fis = activity.openFileInput(APP_CONFIG);

            // 读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator
                    + APP_CONFIG);

            props.load(fis);
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在files目录下
            // fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public void set(Properties ps) {
        Properties props = get();
        props.putAll(ps);
        setProps(props);
    }

    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);
    }

    /**
     * 比较时间是否在1小时内，是则为有效期
     *
     * @param time time
     * @return isExpiryDate true or false
     */
    public static boolean isExpiryDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = format.parse(time);
            long delta = new Date().getTime() - date.getTime();
            if (delta < 24L * 3600000L)
                return true;
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
