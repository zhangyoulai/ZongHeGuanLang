package com.example.hasee.compare.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.hasee.compare.bean.CodeAndSpec;
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

    public static void insert(Context context, CodeAndSpec ib){
        String []args = ib.ib2Arrary();
        if (args==null){
            Toast.makeText(context, "没有信息！", Toast.LENGTH_LONG).show();
            return;
        }
        SQLiteDatabase database = getIbDatabase(context);
        String sql = "insert into code_spec(code, spec) values(?, ?)";
        database.execSQL(sql, args);
    }

    public static void delete(Context context, CodeAndSpec ib){
        String []args = ib.ib2Arrary();
        if (args==null){
            Toast.makeText(context, "没有冰箱信息！", Toast.LENGTH_LONG).show();
            return;
        }
        SQLiteDatabase database = getIbDatabase(context);
        String sql = "delete from code_spec where code=?";
        database.execSQL(sql, new String[]{ib.getCode()});
    }

    public static void update(Context context, CodeAndSpec ib){
        // 更新 结合删除和添加操作
    }

    public static void query(){

    }

    // 根据二维码找到R3码
    public static CodeAndSpec queryItem(String s, Context context){
        CodeAndSpec ib = new CodeAndSpec();
        String sql = "select code,spec from code_spec where code=?";
        SQLiteDatabase database = getIbDatabase(context);
        Cursor cursor = database.rawQuery(sql, new String[]{s});
        if(cursor.getCount()==0){
            return null;
        }
        while (cursor.moveToNext()){
            ib.setFlag(true);
            String code = cursor.getString(0);
            String spec = cursor.getString(1);
            ib.setCode(code);
            ib.setSpec(spec);
        }
        cursor.close();
        return  ib;
    }

    public static ArrayList<CodeAndSpec> queryAll(Context context){
        ArrayList<CodeAndSpec> ibList = new ArrayList<>();
        CodeAndSpec ib;
        String sql = "select code,spec from code_spec";
        SQLiteDatabase database = getIbDatabase(context);
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ib = new CodeAndSpec();
            ib.setFlag(true);
            String code = cursor.getString(0);
            String spec = cursor.getString(1);
            ib.setCode(code);
            ib.setSpec(spec);
            ibList.add(ib);
        }
        cursor.close();
        return ibList;
    }
}