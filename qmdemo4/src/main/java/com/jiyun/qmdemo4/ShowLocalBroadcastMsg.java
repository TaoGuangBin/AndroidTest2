package com.jiyun.qmdemo4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowLocalBroadcastMsg extends AppCompatActivity {

    private TextView mShowlbTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_local_broadcast_msg);
        initView();
        initShowlb();
    }

    private void initShowlb() {
        Intent intent = getIntent();
        String showmsg = intent.getStringExtra("showmsg");
        mShowlbTv.setText(showmsg);
    }

    private void initView() {
        mShowlbTv = (TextView) findViewById(R.id.showlb_tv);
    }
}
