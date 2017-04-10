package com.mingjiang.android.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.mingjiang.android.app.bean.IceBox;
import com.mingjiang.android.base.dao.MLSQLiteOpenHelper;


import java.util.ArrayList;

public class IbDataBaseUtil {

    private static MLSQLiteOpenHelper ibDataBase;

    public static void createDataBase(Context context){
        ibDataBase = new MLSQLiteOpenHelper(context);
    }

    public static SQLiteDatabase getIbDatabase(Context context){
        ibDataBase = new MLSQLiteOpenHelper(context);
        return  ibDataBase.getWritableDatabase();
    }

    public static void insert(Context context, IceBox ib){
        String []args = ib.ib2Arrary();
        if (args==null){
            Toast.makeText(context, "没有冰箱信息！", Toast.LENGTH_LONG).show();
            return;
        }
        SQLiteDatabase database = getIbDatabase(context);
        String sql = "insert into note(r3code, icname,number,customer,company,address,phone) values(" +
                " ?, ?, ?, ?, ?, ?, ?)";
        database.execSQL(sql, args);
    }

    public static void delete(Context context, IceBox ib){
        String []args = ib.ib2Arrary();
        if (args==null){
            Toast.makeText(context, "没有冰箱信息！", Toast.LENGTH_LONG).show();
            return;
        }
        SQLiteDatabase database = getIbDatabase(context);
        String sql = "delete from note where r3code = ?";
        database.execSQL(sql, new String[]{ib.getR3code()});
    }

    public static void update(Context context, IceBox ib){
        // 更新 结合删除和添加操作
    }

    public static void query(){

    }

    // 根据R3码
    public static IceBox queryItem(String s, Context context){
        IceBox ib = new IceBox();
        String sql = "select r3code,icname,number,customer,company,address,phone from note where r3code=?";
        SQLiteDatabase database = getIbDatabase(context);
        Cursor cursor = database.rawQuery(sql, new String[]{s});
        if(cursor.getCount()==0){
            return null;
        }

        while (cursor.moveToNext()){
            ib.setFlag(true);
            String r3code = cursor.getString(0);
            String icname = cursor.getString(1);
            String number = cursor.getString(2);
            String customer = cursor.getString(3);
            String company = cursor.getString(4);
            String address = cursor.getString(5);
            String phone = cursor.getString(6);
            ib.setR3code(r3code);
            ib.setIceBoxName(icname);
            ib.setNumber(number);
            ib.setCustomer(customer);
            ib.setCompany(company);
            ib.setAddress(address);
            ib.setPhone(phone);
        }
        cursor.close();
        return  ib;
    }

    public static ArrayList<IceBox> queryAll(Context context){
        ArrayList<IceBox> ibList = new ArrayList<>();
        IceBox ib;
        String sql = "select r3code, icname,number,customer,company,address,phone from note";
        SQLiteDatabase database = getIbDatabase(context);
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ib = new IceBox();
            ib.setFlag(true);
            String r3code = cursor.getString(0);
            String icname = cursor.getString(1);
            String number = cursor.getString(2);
            String customer = cursor.getString(3);
            String company = cursor.getString(4);
            String address = cursor.getString(5);
            String phone = cursor.getString(6);
            ib.setR3code(r3code);
            ib.setIceBoxName(icname);
            ib.setNumber(number);
            ib.setCustomer(customer);
            ib.setCompany(company);
            ib.setAddress(address);
            ib.setPhone(phone);
            ibList.add(ib);
        }
        cursor.close();
        return ibList;
    }
}