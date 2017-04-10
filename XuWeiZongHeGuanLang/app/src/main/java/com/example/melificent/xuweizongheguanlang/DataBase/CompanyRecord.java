package com.example.melificent.xuweizongheguanlang.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by p on 2017/2/13.
 * Database for manage company
 */

public class CompanyRecord extends SQLiteOpenHelper {
    private static String dbname = "company";
    private static int Version = 1;
    public CompanyRecord(Context context) {
        super(context, dbname, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table company(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
