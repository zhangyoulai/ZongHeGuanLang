package com.mingjiang.android.app.util;

import android.content.Context;
import android.widget.EditText;

import com.mingjiang.android.base.util.SharedPrefsUtil;

/**
 * Created by kouzeping on 2016/4/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class SetLineUtils {
    private static final String LINE_NUM="line_num";
    public static  void setLineNum(EditText editText,Context context){
        String lineNum=SharedPrefsUtil.getString(context,LINE_NUM);
        editText.setText(lineNum);
    }

    public static  void updateLineNum(EditText editText,Context context){
        SharedPrefsUtil.setValue(context, LINE_NUM, editText.getText().toString());
    }

}
