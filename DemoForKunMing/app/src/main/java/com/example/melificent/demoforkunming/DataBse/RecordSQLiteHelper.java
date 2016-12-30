package com.example.melificent.demoforkunming.DataBse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2016/12/20.
 */

public class RecordSQLiteHelper extends SQLiteOpenHelper {
    private  static  String name = "record.db";
    private  static  Integer version = 1;
    public RecordSQLiteHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table record (id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
