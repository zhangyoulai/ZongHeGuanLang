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
 * Created by p on 2017/3/22.
 */

public class ElectricChargeWebview extends AppCompatActivity {
    @InjectView(R.id.electric_charge_webpage_back)
    ImageView back;
    @InjectView(R.id.webview_electric_charge)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electric_charge_webview);
        ButterKnife.inject(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://billcloud.unionpay.com/ccfront/entry/query?category=D1&insId=1900_0401");
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
