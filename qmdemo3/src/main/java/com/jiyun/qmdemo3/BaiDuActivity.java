package com.jiyun.qmdemo3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BaiDuActivity extends AppCompatActivity {

    private WebView mShowBaiduweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du);
        initView();
        initWeb();
    }

    private void initWeb() {
        mShowBaiduweb.setWebViewClient(new WebViewClient());
        mShowBaiduweb.loadUrl("http://www.baidu.com");
    }

    private void initView() {
        mShowBaiduweb = (WebView) findViewById(R.id.show_baiduweb);
    }
}
