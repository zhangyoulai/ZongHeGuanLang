package com.mingjiang.android.app.bean;

/**
 * Created by wdongjia on 2016/9/8.
 */
public class PrintLog {

    private String printType;
    private String printCode;
    private boolean printStatus = false;//未打印

    public PrintLog(String printType, String printCode, boolean printStatus) {
        this.printType = printType;
        this.printCode = printCode;
        this.printStatus = printStatus;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getPrintCode() {
        return printCode;
    }

    public void setPrintCode(String printCode) {
        this.printCode = printCode;
    }

    public boolean isPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(boolean printStatus) {
        this.printStatus = printStatus;
    }
}
