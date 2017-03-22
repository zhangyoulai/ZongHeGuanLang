package com.example.melificent.myqianqi.View;

import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexResult;

/**
 * Created by p on 2017/3/17.
 */

public interface GetStrockIndex {
    public void getStrockIndexSuccess(StrockIndexResult result);
    public void getStrockIndexFial(String Msg);
}
