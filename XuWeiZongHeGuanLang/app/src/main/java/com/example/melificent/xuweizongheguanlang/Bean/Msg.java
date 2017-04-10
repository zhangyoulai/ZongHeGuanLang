package com.example.melificent.xuweizongheguanlang.Bean;

/**
 * Created by p on 2017/2/9.
 */

public class Msg {
    public String content;
    public  int type ;
    public static  final  int TYPE_RECEIVE = 0;
    public static final int TYPE_SEND = 1;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
