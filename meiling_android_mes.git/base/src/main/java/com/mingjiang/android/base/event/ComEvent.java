package com.mingjiang.android.base.event;

/**
 * 备注：用于EventBus数据处理。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComEvent {
    protected final static String TAG = ComEvent.class.getSimpleName();
    /*************************************
     * 串口读取数据服务
     ******************************************/
    public static final int ACTION_POST_SCAN = 100;                     //岗位扫描
    public static final int ACTION_USER_SCAN = 101;                     //用户扫描
    public static final int ACTION_MATERIAL_USER_SCAN = 103;           //物料管理-用户登录
    public static final int ACTION_INSTRUCTION_FRIDGE_SCAN = 104;     //岗位指导书-冰箱扫描
    public static final int ACTION_INSTRUCTION_PDF_SCAN = 105;     //岗位指导书-冰箱扫描
    public final static int ACTION_GET_SCHUNK = 98; //
    /*************************************串口读取数据服务******************************************/

    /************************************
     * 岗位用户扫描（scan）
     ***************************************/
    public static final int ACTION_CHECK_USER = 10;                     //校验用户
    public static final int ACTION_CHECK_USER_ERROR = 11;              //校验用户出错
    /************************************岗位用户扫描（scan）***************************************/

    /**************************************
     * 岗位指导书
     ***********************************************/
    public static final int ACTION_LOAD_SERVER = 20;                   //加载服务数据（JSON）
    public static final int ACTION_LOAD_SERVER_ERROR = 21;            //加载服务数据出错
    public static final int ACTION_LOAD_IMAGE = 22;                    //加载操作步骤图片
    public static final int ACTION_LOAD_IMAGE_ERROR = 23;             //加载操作步骤图片出错
    public static final int ACTION_LOAD_WHELL_HIDE = 24;              //加载图片进度框消失
    /**************************************岗位指导书***********************************************/

    /**************************************
     * 岗位指导书PDF
     ***********************************************/
    public static final int PDF_UPLOAD_SUCCEED = 30;                   //加载服务数据（JSON）
    public static final int PDF_UPLOAD_ERROR = 31;            //加载服务数据出错
    public static final int PDF_DOWNLOAD_SUCCEED = 32;                    //加载操作步骤图片
    public static final int PDF_DOWNLOAD_ERROR = 33;             //加载操作步骤图片出错
    public static final int PDF_LOAD_WHELL_HIDE = 34;              //加载图片进度框消失
    public static final int GET_YIELF_SUCCEED = 35;             //加载操作步骤图片出错
    public static final int GET_YIELD_ERROR = 36;              //加载图片进度框消失
    /**************************************岗位指导书PDF***********************************************/

    /**************************************
     * 线边仓库
     *************************************************/
    public static final int REQUEST_LINSE_STORAGE_MARRTIAL = 401; //线边库物料请求
    public static final int ADJUSTMNET_MARRTIAL = 402;          //调整物料成功
    public static final int ADJUSTMENT_MARRTIAL_MISS = 403;     //调整物料失败
    public static final int RECEIVE_MARRTIAL_DATA = 411;          //调整物料成功
    public static final int RECEIVE_MARRTIAL = 412;          //请求卸料成功
    public static final int RECEIVE_MARRTIAL_MISS = 413;     //请求卸料失败
    public static final int RECEIVE_MARRTIAL_COMMIT = 414;          //请求卸料成功
    public static final int RECEIVE_MARRTIAL_COMMIT_MISS = 415;     //请求卸料失败
    public static final int REQUEST_MARRTIAL_SUCCEED = 431;     //手动叫料成功
    public static final int REQUEST_MARRTIAL_MISS = 432;     //手动叫料信息提交失败
    public static final int REQUEST_RETURN_MARRTIAL = 421;          //线边库物料请求
    public static final int RETURN_MARRTIAL = 422;          //线边库
    public static final int RETURN_MARRTIAL_MISS = 423;          //线边库
    /**************************************线边仓库*************************************************/

    /**************************************
     * 物料管理
     *************************************************/
    public static final int ACTION_MIDDLE_LIBARY_QUERY = 60;          //中间库
    public static final int ACTION_AROUND_LIBARY_QUERY = 61;          //线边库
    public static final int ACTION_AROUND_LIBARY_QUERY1 = 610;          //线边库
    public static final int ACTION_GET_SESSION = 62;                   //获取Session数据
    public static final int ACTION_ADDLIB_TIP = 63;                    //一键入库
    public static final int ACTION_ADDLIB_SCAN_CODE = 64;             //扫描出库单获取物料信息
    public static final int ACTION_ADDLIB_GET_MATERIALS = 65;        //获取物料信息
    /**************************************物料管理*************************************************/

    /**************************************
     * 上下线
     ***************************************************/
    public static final int ONOFF_FRIDGE_SCAN = 73;            //扫描生产流水号
    public static final int ACTION_ONOFF_SUCCESS = 70;                //上下线数据提交成功
    public static final int ACTION_ONOFF_FAILUE = 71;                 //上下线数据提交失败
    public static final int ACTION_ONOFF_ERROR_SERIAL_NUM = 72;     //生产流水号错误
    /**************************************上下线***************************************************/

    /**************************************
     * 打印二维码
     ***********************************************/
    public final static int ACTION_PRINT = 81;                       //打印操作
    public final static int ACTION_PRINT_ZEBRA = 500;
    public final static int ACTION_PRINT_CITIIZEN = 501;
    public final static int ACTION_GET_CODE = 82;                   //获取编码
    public final static int ACTION_WAIT = 83;                       //等待
    public final static int ACTION_SEND_PCI = 84;  //
    public final static int ACTION_SEND_ROB = 85; //
    public final static int ACTION_SEND_PLC = 86; //
    public final static int ACTION_SEND_GUN = 110;
    public final static int ACTION_GET_PCI = 87;  //
    public final static int ACTION_GET_ROB = 88; //
    public final static int ACTION_GET_PLC = 89; //
    /**************************************打印二维码***********************************************/

    /**************************************
     * 质量检验
     *************************************************/
    public static final int CHECK_USER = 90;                     //校验用户
    public static final int CHECK_USER_MISS = 91;                     //校验用户失败
    public static final int CHECK_MASTER_SUCCEED = 92;                     //是质检站长
    public static final int CHECK_MASTER_MISS = 93;                     //不是质检站长
    public static final int INSPECTION_USERSCAN = 96;     //质检岗位用户扫描
    public static final int INSPECTION_CHECKSCAN = 97;     //质检岗位检查界面扫描
    /**********************************质量检验*************************************************/

    protected String message;       //传递字符串数据
    protected int actionType;       //事件类型
    private Object objectMsg = null;//传递对象数据
    private byte[] bytes; //
    private StringBuffer stringBuffer;

    public ComEvent(Object objectMsg, int actionType) {
        this.objectMsg = objectMsg;
        this.actionType = actionType;
    }

    public ComEvent(String message, int actionType) {
        this.message = message;
        this.actionType = actionType;
    }

    public ComEvent(byte[] message, int actionType) {
        this.bytes = message;
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    public String getMessage() {
        return message;
    }

    public Object getObjectMsg() {
        return objectMsg;
    }

    public byte[] getBytes() {
        return bytes;
    }

    //    StringBuffer
    public ComEvent(StringBuffer stringBuffer, int actionType) {
        this.stringBuffer = stringBuffer;
        this.actionType = actionType;
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }
}
