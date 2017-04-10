package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.melificent.xuweizongheguanlang.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/15.
 * Comprehensive monitor everypart , in here different part will to different activity
 */

public class ComprehensiveMonitorActivity extends AppCompatActivity {
    @InjectView(R.id.jichengjiankong)
    LinearLayout jichengjiankong;
    @InjectView(R.id.dituzhanshi)
    LinearLayout dituzhanshi;
    @InjectView(R.id.yunxingzhuangtai)
    LinearLayout yunxingzhuangtai;
    @InjectView(R.id.renyuandingwei)
    LinearLayout renyuandingwei;
    @InjectView(R.id.jiankongzixitong)
    LinearLayout jiankongzixitong;
    @InjectView(R.id.jiegoujiance)
    LinearLayout jiegoujiance;
    @InjectView(R.id.shebeijiance)
    LinearLayout shebeijiance;
    @InjectView(R.id.huanjingjiance)
    LinearLayout huanjingjiance;
    @InjectView(R.id.anfangjiankong)
    LinearLayout anfangjiankong;
    @InjectView(R.id.baojingxitong)
    LinearLayout baojingxitong;
    @InjectView(R.id.guanxianjiankong)
    LinearLayout guanxianjiankong;
    @InjectView(R.id.zaixiantongxin)
    LinearLayout zaixiantongxin;
    @InjectView(R.id.xitongliandong)
    LinearLayout xitongliandong;
    @InjectView(R.id.baojingchaxun)
    LinearLayout baojingchaxun;
    @InjectView(R.id.anquanpinggu)
    LinearLayout anquanpinggu;
    @InjectView(R.id.baobiaoguanli)
    LinearLayout baobiaoguanli;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_monitor_activity);
        ButterKnife.inject(this);
        SetLinearLayoutListener();
    }

    private void SetLinearLayoutListener() {
        jichengjiankong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComprehensiveMonitorActivity.this,OprationActivity.class));

            }
        });
        jiegoujiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComprehensiveMonitorActivity.this,ConstructionActivity.class));
            }
        });
        huanjingjiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComprehensiveMonitorActivity.this,EnvironmentMonitorActivity.class));
            }
        });
        shebeijiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComprehensiveMonitorActivity.this,EquipmentMonitorActivity.class));
            }
        });
        anfangjiankong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComprehensiveMonitorActivity.this,SecurityMonitorActivity.class));
            }
        });

    }
}
