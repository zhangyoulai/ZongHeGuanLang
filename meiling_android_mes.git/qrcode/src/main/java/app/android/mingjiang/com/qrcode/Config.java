package app.android.mingjiang.com.qrcode;

import java.util.ArrayList;

import app.android.mingjiang.com.qrcode.bean.PrintLog;

/**
 *
 */
public class Config {

    public static ArrayList<PrintLog> plcMsgsList = new ArrayList<>();

    //读取日计划
    public final static String URL_DAILY_PLAN =
            "api/interface/public/plan_manage.daily_plan/process_serial_element";

    //上传订单号+二维码
    public final static String URL_UPLOAD_CODE =
            "api/interface/json/public/process_control.process/trace_process";

    //报废订单 流水号
    public final static String URL_SCRAPPED_ORDER =
            "api/interface/public/quality_manage.scrapped_product/print_code";

    public final static String URL_UPLOAD_CODE1 =
            "api/interface/public/process_control.process/count_printed_by_daily";

    public final static String URL_GET_QRCODE =
            "api/interface/public/process_control.process/print_code";

}
