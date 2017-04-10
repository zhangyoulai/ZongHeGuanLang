package com.mingjiang.android.base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MLSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {


    public static final int VERSION = 1;
    private static final String DBNAME = "meiling.db";

    public MLSQLiteOpenHelper(Context context){
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*note*/
        db.execSQL("create table note(id integer primary key autoincrement," +
                "r3code varchar(20)," +
                "icname varchar(20), "+
                "number varchar(20)," +
                "customer varchar(20)," +
                "company char(30)," +
                "address char(50)," +
                "phone varchar(20))");
        /*ib*/
        db.execSQL("create table ib(id integer primary key autoincrement," +
                "r3code varchar(20),icname varchar(20), deth varchar(20)," +
                "with varchar(20), height varchar(20), cmd varchar(20),location varchar(20))");

        db.execSQL("create table allib(id integer primary key autoincrement," +
                "r3code varchar(20),icname varchar(20), deth varchar(20)," +
                "with varchar(20), height varchar(20), cmd varchar(20),location varchar(20))");
        /*code*/
        db.execSQL("create table code_spec(id integer primary key autoincrement," +
                "code varchar(20)," +
                "spec varchar(20))");

         /*compressor*/
        db.execSQL("create table compressor(id integer primary key autoincrement," +
                "r3code varchar(20),icname varchar(20), deth varchar(20)," +
                "with varchar(20), height varchar(20), cmd varchar(20),location varchar(20))");

        db.execSQL("create table allcompressor(id integer primary key autoincrement," +
                "r3code varchar(20),icname varchar(20), deth varchar(20)," +
                "with varchar(20), height varchar(20), cmd varchar(20),location varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
