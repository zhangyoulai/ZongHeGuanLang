package com.example.melificent.demoforkunming.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/10/27.
 */
public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText et_email;
    EditText et_password;

    MyDatabaseHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        login = (Button) findViewById(R.id.button_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        //为了便于用户体验，在界面的一开始就设置用户名输入框
        et_email.setText("Admin@YHD.COM");
            //新建数据库LoginTable
        helper = new MyDatabaseHelper(this,"LoginTable.db",null,1);
        db = helper.getWritableDatabase();
        //初始化数据库数据
        initDataBaseData();
        //登录
        login();
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    private void login() {
        //为登录按钮设置监听器
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码输入框中的内容
                String e = et_email.getText().toString().trim();
                String p = et_password.getText().toString().trim();
                //如果两个输入框的内容都是空，toast提示
                if (e.equals("")||p.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                    //查询数据表，匹配用户名和密码
                String table="login";
                String[] columns={
                      "email","password"
                };
                //调用query方法查询数据库
                Cursor c = db.query(table,columns,null,null,null,null,null);
                if (c.moveToPosition(0)){
                    String email = c.getString(0);
                    String password = c.getString(1);
                    //将查询到的结果进行判断，仅当用户名和密码都正确时，才能正常登录，跳转
                    if (email.equals(e)&&password.equals(p)){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else {
                        //用户名或密码不正确，toast提示
                        Toast.makeText(LoginActivity.this,"用户名或密码错误！！！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }





//给数据库数据初始化
    private void initDataBaseData() {
        ContentValues values = new ContentValues();
        values.put("email","Admin@YHD.COM");
        values.put("password","123456");
        db.insert("login",null,values);
    }
            //新建login数据表
    class  MyDatabaseHelper extends SQLiteOpenHelper{
        private  Context mcontext;

        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mcontext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_LOGINTABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        public  static  final  String CREATE_LOGINTABLE ="create table login(" +
                "id integer primary key autoincrement,"
                +"email text,"+
                "password text"+
               ")";
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
