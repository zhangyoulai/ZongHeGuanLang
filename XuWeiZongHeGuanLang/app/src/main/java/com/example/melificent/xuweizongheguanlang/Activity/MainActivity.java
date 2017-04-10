package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.melificent.xuweizongheguanlang.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主页构建分成四大模块，运维、商务、安全、应急。
 * 点击相应的按钮跳转到相应的业务模块。
 */

public class MainActivity extends AppCompatActivity {
    //百度地图AK： 	FRi6c0kyCMz5Mpnpp5eG4loVN7mwo2F4
    @InjectView(R.id.btn_business)
    Button business;
    @InjectView(R.id.btn_security)
    Button security;
    @InjectView(R.id.btn_opration)
    Button opration;
    @InjectView(R.id.btn_emergency)
    Button emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //监听按钮的点击
        setButtonListener();
    }

    private void setButtonListener() {
        opration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,NewOprationActivity.class));
            }
        });
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ComprehensiveMonitorActivity.class));
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BusinessActivity.class));
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmergencyActivity.class));
            }
        });
    }
}
