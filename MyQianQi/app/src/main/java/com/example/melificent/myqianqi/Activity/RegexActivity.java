package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
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

import com.example.melificent.myqianqi.Bean.RealNameRegex.RealNameRegexResult;
import com.example.melificent.myqianqi.LocalDataBase.PersonalInfomationDatabase;
import com.example.melificent.myqianqi.Presenter.IGetRealNameRegexResultPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetRealNameRegexResultPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetRealNameRegexResult;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class RegexActivity extends AppCompatActivity implements GetRealNameRegexResult {
    @InjectView(R.id.realname_regex)
    Button realname_regex;
    @InjectView(R.id.realname)
    EditText realname;
    @InjectView(R.id.idcardNo)
    EditText idCardNo;
    @InjectView(R.id.TelNo)
    EditText TelNo;
    @InjectView(R.id.realnameregexgoback)
    ImageView back;
    RealNameRegexResult result ;
    IGetRealNameRegexResultPresenter presenter = new IGetRealNameRegexResultPresenterImpl(this);
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realnameregexactivity);
        ButterKnife.inject(this);
        setButtonListener();
        helper = new PersonalInfomationDatabase(this);

    }

    private void setButtonListener() {
        realname_regex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCardNo.getText().toString().trim().length() == 18 && TelNo.getText().toString().trim().length() ==11){
                    presenter.getRealNameRegexResultPresenter(realname.getText().toString().trim(),idCardNo.getText().toString().trim());
                }else {
                    Toast.makeText(RegexActivity.this, "身份证号或电话号不正确，请查证后重输！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void getRealNameRegexSuccess(RealNameRegexResult result) {
        db = helper.getWritableDatabase();
            if (result.res == 1){
                db.execSQL("insert into personalInfo(name,idcard,TelNo) values(?,?,?)",new String[]{
                      realname.getText().toString().trim(),  idCardNo.getText().toString().trim(),TelNo.getText().toString().trim()
                });
                db.close();
                startActivity(new Intent(RegexActivity.this,PassWordActivity.class));

            }else {
                Toast.makeText(this, "认证失败，请确认信息！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegexActivity.this,RegistFail.class));
            }
    }
}
