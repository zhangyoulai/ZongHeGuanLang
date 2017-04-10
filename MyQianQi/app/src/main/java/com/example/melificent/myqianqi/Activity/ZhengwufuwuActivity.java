package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.melificent.myqianqi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/4/3.
 */

public class ZhengwufuwuActivity extends AppCompatActivity {
    @InjectView(R.id.zhinengwenda)
    Button zhinengwenda;
    @InjectView(R.id.changjianwenti)
    Button changjianwenti;
    @InjectView(R.id.banshizhinan)
    Button banshizhinan;
    @InjectView(R.id.banshijindu)
    Button banshijindu;
    @InjectView(R.id.xinjiandafu)
    Button xinjiandafu;
    @InjectView(R.id.wangzhantongji)
    Button wangzhantongji;
    @InjectView(R.id.zhaiquanqingdan)
    Button zhaiquanqingdan;
    @InjectView(R.id.quanliqingdan)
    Button quanliqingdan;
    @InjectView(R.id.banjiangonggao)
    Button banjiangonggao;
    @InjectView(R.id.zhongdianshixiang)
    Button zhongdianshixiang;
    @InjectView(R.id.wangzhanfabu)
    Button wangzhanfabu;
    @InjectView(R.id.chuiguanbumen)
    Button chuiguanbumen;
    @InjectView(R.id.xingzhengshenpi)
    Button xingzhengshenpi;
    @InjectView(R.id.gerenbanshi)
    Button gerenbanshi;
    @InjectView(R.id.farenbanshi)
    Button farenbanshi;
    @InjectView(R.id.woyaotousu)
    Button woyaotousu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhengwudating);
        ButterKnife.inject(this);
        setButtonListener();
    }

    private void setButtonListener() {
        zhinengwenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/intelligentQueAns.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        changjianwenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/showFAQsQueryListFrame.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        banshizhinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/guideListIndex.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        banshijindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/initCaseProgress.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        xinjiandafu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/replyMail.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        wangzhantongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/websiteStatistics.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        zhaiquanqingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/departmentList.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        quanliqingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/initPowerList.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        banjiangonggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/initYgzwShenpiIndex.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        zhongdianshixiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://xxgk.etkqq.gov.cn/zfbm_59329/qzfjbsjj/zfbgs/"));
                startActivity(i);
            }
        });
        wangzhanfabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/webPublish.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        chuiguanbumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/initPowerLists.action?districtId=420000&isVerticalDept=1"));
                startActivity(i);
            }
        });
        xingzhengshenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/initXzspPageIndex.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        gerenbanshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/grbsIndex.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        farenbanshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/frbsIndex.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
        woyaotousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://zwfw.ordos.gov.cn/tsInit.action?districtId=8a96998a48f7fb180148f9037cbc010c"));
                startActivity(i);
            }
        });
    }
}
