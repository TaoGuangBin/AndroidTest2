package com.jiyun.qmtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstWecomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFwcIv;
    private TextView mFwcTv;
    private CountDownTimer conn;
    private int index = 5;
    /**
     * 跳过
     */
    private Button mFwcBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_wecome);
        initView();
        initTimer();
    }

    private void initTimer() {
        ObjectAnimator a1 = ObjectAnimator.ofFloat(mFwcIv, "alpha", 0, 1);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(mFwcIv, "Rotation", 0, 360);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(mFwcIv, "ScaleY", 0, 1);
        ObjectAnimator a4 = ObjectAnimator.ofFloat(mFwcIv, "TranslationY", 0, 360);
        AnimatorSet set = new AnimatorSet();
        set.play(a1).with(a2).with(a3).with(a4);
        set.setDuration(5000);
        set.start();
        conn = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mFwcTv.setText(index + "");
                index--;
            }

            @Override
            public void onFinish() {
                mFwcTv.setText(index + "");
                Intent intent = new Intent(FirstWecomeActivity.this, SecondWelcomeActivity.class);
                startActivity(intent);

            }
        };
        conn.start();
    }

   /* @Override
    protected void onStop() {
        super.onStop();
        conn.cancel();
        conn = null;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        conn.cancel();
        conn = null;
    }

    private void initView() {
        mFwcIv = (ImageView) findViewById(R.id.fwc_iv);
        mFwcTv = (TextView) findViewById(R.id.fwc_tv);
        mFwcBt = (Button) findViewById(R.id.fwc_bt);
        mFwcBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fwc_bt:
                Intent intent = new Intent(FirstWecomeActivity.this, SecondWelcomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
