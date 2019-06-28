package com.jiyun.qmdemo2upanddown;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LocalBroadActivity extends AppCompatActivity {

    private TextView mMylocalbroadTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broad);
        initView();
        initLB();
    }

    private void initLB() {
        Intent intent = getIntent();
        String newmsg = intent.getStringExtra("newmsg");
        mMylocalbroadTv.setText(newmsg);
    }

    private void initView() {
        mMylocalbroadTv = (TextView) findViewById(R.id.Mylocalbroad_tv);
    }
}
