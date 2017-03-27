package com.example.melificent.myqianqi.LocalDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2017/3/22.
 */

public class PasswordDataBase extends SQLiteOpenHelper {
    private static String dbName = "password";
    private static int version = 2;
    public PasswordDataBase(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table password(id integer primary key autoincrement,password varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
