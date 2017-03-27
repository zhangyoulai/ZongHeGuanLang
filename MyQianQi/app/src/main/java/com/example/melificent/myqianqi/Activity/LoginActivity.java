package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.myqianqi.LocalDataBase.PasswordDataBase;
import com.example.melificent.myqianqi.LocalDataBase.PersonalInfomationDatabase;
import com.example.melificent.myqianqi.R;


import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by p on 2017/3/22.
 */

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.goregex)
    TextView goregex;
    @InjectView(R.id.user_name)
    EditText username;
    @InjectView(R.id.user_password)
    EditText password;
    @InjectView(R.id.user_login)
    Button login;
    @InjectView(R.id.loginback)
    ImageView back;
    SQLiteDatabase db,db1;

    SQLiteOpenHelper helper,helper1;
    String username1 ;
    String password1;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        helper = new PasswordDataBase(this);
        helper1 = new PersonalInfomationDatabase(this);
        GoRegex();
        LoginRegex();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void LoginRegex() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = helper.getReadableDatabase();
                Cursor c = db.rawQuery("select * from password",null);


                if (c.moveToFirst()){
                    do {

                        password1 = c.getString(c.getColumnIndex("password"));
                    }while (c.moveToNext());

                }
                c.close();
                db.close();

                db1 = helper1.getReadableDatabase();
                Cursor c1= db1.rawQuery("select * from personalInfo",null);
                if (c1.moveToFirst()){
                    do {
                        name = c1.getString(c1.getColumnIndex("name"));
                        username1 = c1.getString(c1.getColumnIndex("idcard"));
                    }while (c1.moveToNext());

                }
                if (username1.equals(username.getText().toString().trim()) && password1.equals(password.getText().toString().trim())){
                    Intent intent = new Intent(LoginActivity.this,FrameActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            }
        });

    }

    private void GoRegex() {
        goregex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegexActivity.class));
            }
        });
    }
}
