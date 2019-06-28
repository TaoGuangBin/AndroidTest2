package com.jiyun.qmtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BaiDuActivity extends AppCompatActivity {

    private WebView mBaiduWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du);
        initView();
        initWeb();
    }

    private void initWeb() {
        mBaiduWeb.setWebViewClient(new WebViewClient());
        mBaiduWeb.loadUrl("Http://www.baidu.com");
    }

    private void initView() {
        mBaiduWeb = (WebView) findViewById(R.id.baidu_web);
    }
}
