package com.example.melificent.xuweizongheguanlang.Utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by p on 2017/3/1.
 * This is a utils for format date ,by the method formatDate you can get time format "yyyy-MM-dd"
 * param Date date ,date is you need to transmit time and you want to format.
 * @return String ,you can get a time format String "yyyy-MM-dd"after format;
 */

public class TimeFormatUtils {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
       String dString = simpleDateFormat.format(date);
     return dString;
    }
}
