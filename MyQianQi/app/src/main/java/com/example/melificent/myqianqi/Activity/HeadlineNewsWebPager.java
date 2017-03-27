package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.GlobalContants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class HeadlineNewsWebPager extends AppCompatActivity {
    @InjectView(R.id.webview)
    WebView webView;
    @InjectView(R.id.news_webpage_back)
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        ButterKnife.inject(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjYxMTkyNA==&mid=2654310335&idx=1&sn=8ecfb3e1d99506675ef1ec1f304f86ac&chksm=bd6284848a150d9240726b6566aaf54b916775900136b930714e75b66b06dcdc45394b3d019d&mpshare=1&scene=23&srcid=0327tNhvtEC1zFKA4cXNQkl1#rd");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        overridePendingTransition(0,0);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
        GlobalContants.FirstClick =false;
    }
}
