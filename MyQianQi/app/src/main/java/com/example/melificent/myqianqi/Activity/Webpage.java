package com.example.melificent.myqianqi.Activity;

import android.app.ActivityManager;
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
 * Created by p on 2017/3/17.
 */

public class Webpage extends AppCompatActivity {
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
        webView.loadUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MjM5MjYxMTkyNA==&hid=1&sn=84d0b64d8b8373bedad442c3cc0aaf51#wechat_redirect");
//        webView.loadUrl("http://m2.quote.eastmoney.com/h5stock/0003001.html?from=BaiduAladdin");
//        webView.loadUrl("http://v.juhe.cn/wepiao/go?key=7f2ca05c1fe588cc48537e2380c4f200");
//        webView.loadUrl("http://m.mtime.cn/#!/");
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
