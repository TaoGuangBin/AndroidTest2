package com.jiyun.qmtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowNTActivity extends AppCompatActivity {

    private TextView mShowntTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_nt);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        mShowntTv.setText(msg);
    }

    private void initView() {
        mShowntTv = (TextView) findViewById(R.id.shownt_tv);
    }
}
