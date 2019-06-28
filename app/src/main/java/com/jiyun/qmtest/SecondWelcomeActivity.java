package com.jiyun.qmtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 跳过
     */
    private Button mSwcBt;
    private ImageView mSwcIv;
    private TextView mSwcTv;
    private CountDownTimer conn;
    private int index = 5;
    private AnimatorSet set;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_welcome);
        initView();
        initTimer();
    }

    private void initTimer() {
        intent = new Intent(SecondWelcomeActivity.this, ThirdlyWelcomeActivity.class);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mSwcIv, "ScaleY", 0, 2);
        set = new AnimatorSet();
        set.play(scaleY);
        set.setDuration(5000);
        set.start();
        conn = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mSwcTv.setText(index + "");
                index--;
            }

            @Override
            public void onFinish() {
                mSwcTv.setText(index + "");
                startActivity(intent);
            }
        };
        conn.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        conn.cancel();
        conn = null;
    }

    private void initView() {
        mSwcBt = (Button) findViewById(R.id.swc_bt);
        mSwcBt.setOnClickListener(this);
        mSwcIv = (ImageView) findViewById(R.id.swc_iv);
        mSwcTv = (TextView) findViewById(R.id.swc_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.swc_bt:
                startActivity(intent);
                finish();
                break;
        }
    }
}
