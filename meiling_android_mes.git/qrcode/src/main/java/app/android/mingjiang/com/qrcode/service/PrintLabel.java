package app.android.mingjiang.com.qrcode.service;

public class PrintLabel {

    /*
    * 请将所有的打印标签保存在这里
    *
    * */
    public final static String SAMPLE_LABEL = "^XA^FO100,75^BY3^B3N,N,100,Y,N^FD123ABC^XZ";

    /**
     * @param value
     * @return 斑马打印机需要的二维码指令 格式是utf-8
     * BQ,2,10  2代表模式2
     */
    public final static String ZebraLabel(String value) {
        return "^XA" +
                "^FO2,2" +
                "^BQ,3,7" +
                "^FDHA," + value + "^FS" +
                "^XZ";
    }

    /**
     * @param value
     * @return 西铁城打印机需要的二维码指令 格式是utf-8
     * BQ,2,10  2代表模式2
     */
    public final static String CitizenLabel(String value) {
        return "^XA" +
                "^FO12,40" +
                "^BQN,3,10" +
                "^FDHA," + value + "^FS" +
                "^XZ";
    }

}
