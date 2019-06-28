package com.jiyun.qmdemo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LocalbroadcastActivity extends AppCompatActivity {

    private TextView mMylbTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localbroadcast);
        initView();
        initLB();
    }

    private void initLB() {
        Intent intent = getIntent();
        String msg = intent.getStringExtra("newmsg");
        mMylbTv.setText(msg);
    }

    private void initView() {
        mMylbTv = (TextView) findViewById(R.id.mylb_tv);
    }
}
