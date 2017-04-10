package com.example.melificent.testforsearchhistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2017/2/7.
 */

public class RecordDbOpenHelper extends SQLiteOpenHelper {
    public static  String name = "temp.db";
    public  static  Integer version = 1;
    public RecordDbOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records (id integer primary key autoincrement ,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
