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
 * Created by p on 2017/3/26.
 */

public class SheBao extends AppCompatActivity {
    @InjectView(R.id.shebao_webpage_back)
    ImageView back;
    @InjectView(R.id.webview_shebao)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shebao);
        ButterKnife.inject(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://billcloud.unionpay.com/ccfront/entry/query?category=S2&insId=1900_0002");
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
