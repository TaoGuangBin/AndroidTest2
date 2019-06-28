package com.jiyun.qmdemo6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MyNotifiActivity extends AppCompatActivity {

    private TextView mMynotifiTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifi);
        initView();
        Intent intent = getIntent();
        String newmsg = intent.getStringExtra("newmsg");
        mMynotifiTv.setText(newmsg);
    }

    private void initView() {
        mMynotifiTv = (TextView) findViewById(R.id.mynotifi_tv);
    }
}
