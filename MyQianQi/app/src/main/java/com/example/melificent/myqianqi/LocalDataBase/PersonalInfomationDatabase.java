package com.example.melificent.myqianqi.LocalDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2017/3/22.
 */

public class PersonalInfomationDatabase extends SQLiteOpenHelper {
    private static String dbName = "personalInfo";
    private static int dbVersion = 1;
    public PersonalInfomationDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table personalInfo(id integer primary key autoincrement,name varchar(200),idcard varchar(200),TelNo varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
