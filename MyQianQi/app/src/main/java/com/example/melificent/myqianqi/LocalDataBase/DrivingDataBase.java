package com.example.melificent.myqianqi.LocalDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2017/3/23.
 */

public class DrivingDataBase extends SQLiteOpenHelper {
    private static String dbName = "Driving";
    private static int version= 1;
    public DrivingDataBase(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table drive(id integer primary key autoincrement,carNo varchar(200),frameNo varchar(200),engineNo varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
