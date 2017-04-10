package com.mingjiang.android.equipmonitor.event;

/**
 * 备注：用于EventBus数据处理。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComEvent {
    /**************************************
     * 串口读取
     ***********************************************/
    public final static int ACTION_GET_SCHUNK = 82;                   //超声波焊接机
    public final static int ACTION_SEND_PCI = 84;  //
    public final static int ACTION_SEND_ROB = 85; //
    public final static int ACTION_SEND_PLC = 86; //
    /**************************************串口读取***********************************************/

    /**************************************设备监控*************************************************/

    /**************************************设备监控*************************************************/


    protected String message;       //传递字符串数据
    protected int actionType;       //事件类型
    private Object objectMsg = null;//传递对象数据
    private byte[] bytes; //

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
}
