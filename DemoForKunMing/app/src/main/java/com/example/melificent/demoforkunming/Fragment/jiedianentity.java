package com.example.melificent.demoforkunming.Fragment;

import android.graphics.Bitmap;

/**
 * Created by p on 2016/10/31.
 */

public class jiedianentity {
    public  String jiedianmingcheng;
    public  String jiedianweizhi;;
    public  String jiediansheshi;
    public Bitmap bitmap;

    public jiedianentity(String jiedianmingcheng, String jiedianweizhi, String jiediansheshi, Bitmap bitmap) {
        this.jiedianmingcheng = jiedianmingcheng;
        this.jiedianweizhi = jiedianweizhi;
        this.jiediansheshi = jiediansheshi;
        this.bitmap = bitmap;
    }
}
