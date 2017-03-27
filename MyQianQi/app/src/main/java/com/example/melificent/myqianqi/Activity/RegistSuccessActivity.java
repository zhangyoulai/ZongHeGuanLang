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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.myqianqi.LocalDataBase.PersonalInfomationDatabase;
import com.example.melificent.myqianqi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class RegistSuccessActivity extends AppCompatActivity {
    @InjectView(R.id.regist_success_idcard)
    TextView idcard;
    @InjectView(R.id.regist_success_realname)
    TextView realName;
    @InjectView(R.id.regist_success_telNo)
    TextView telNo;
    @InjectView(R.id.realnameregexsuccessgoback)
    ImageView back;
    SQLiteDatabase db;
    SQLiteOpenHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realnameregexsuccess);
        ButterKnife.inject(this);
        helper = new PersonalInfomationDatabase(this);
        db= helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from personalInfo",null);
        if (c.moveToFirst()){
            do {
                idcard.setText(c.getString(c.getColumnIndex("idcard")).substring(0,6)+"********"+c.getString(c.getColumnIndex("idcard")).substring(14));
                Log.i("idcard", "IdCard: "+c.getString(c.getColumnIndex("idcard")).substring(0,6)+"********"+c.getString(c.getColumnIndex("idcard")).substring(14));
                realName.setText(c.getString(c.getColumnIndex("name")));
                telNo.setText(c.getString(c.getColumnIndex("TelNo")).substring(0,3)+"****"+c.getString(c.getColumnIndex("TelNo")).substring(7));
            }while (c.moveToNext());

        }
        c.close();
        db.close();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistSuccessActivity.this,LoginActivity.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegistSuccessActivity.this,LoginActivity.class));
    }
}
