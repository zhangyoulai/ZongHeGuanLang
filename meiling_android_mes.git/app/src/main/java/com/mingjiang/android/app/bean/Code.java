package com.mingjiang.android.app.bean;

/**
 * Created by hasee on 2016/3/31.
 * Email:wangdongjia@shmingjiang.org.cn
 */
public class Code {

    private String barcode;
    private String barcode_spec;
    private String qrcode;
    private String qrcode_spec;
    private Boolean flag = false;

    public Code(){
        flag = true;
    }

    public  Code(String barcode,String barcode_spec,String qrcode,String qrcode_spec){
        this.barcode = barcode;
        this.barcode_spec = barcode_spec;
        this.qrcode = qrcode;
        this.qrcode_spec = qrcode_spec;
        this.flag = true;

    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode_spec() {
        return barcode_spec;
    }

    public void setBarcode_spec(String barcode_spec) {
        this.barcode_spec = barcode_spec;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode_spec() {
        return qrcode_spec;
    }

    public void setQrcode_spec(String qrcode_spec) {
        this.qrcode_spec = qrcode_spec;
    }
}
