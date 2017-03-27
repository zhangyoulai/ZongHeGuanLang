package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.melificent.myqianqi.LocalDataBase.PasswordDataBase;
import com.example.melificent.myqianqi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class PassWordActivity extends AppCompatActivity {
    @InjectView(R.id.real_name_regist)
    Button regist;
    @InjectView(R.id.virgin_password)
    EditText virgin_password;
    @InjectView(R.id.recorect_password)
    EditText recorect_password;
    @InjectView(R.id.passwordgoback)
    ImageView back;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realnameregexsetpassword);
        ButterKnife.inject(this);
        helper= new PasswordDataBase(this);
        RegistSuccess();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void RegistSuccess() {
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (virgin_password.getText().toString().trim().length() >6){
                    if (virgin_password.getText().toString().trim().equals(recorect_password.getText().toString().trim())){
                        db = helper.getReadableDatabase();


                        db.execSQL("insert into password(password) values(?)",new String[]{
                                virgin_password.getText().toString().trim()
                        });
                        db.close();
                        startActivity(new Intent(PassWordActivity.this,RegistSuccessActivity.class));
                    }else {
                        Toast.makeText(PassWordActivity.this, "密码不一致，请确认后重输!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PassWordActivity.this, "密码至少为6位", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
