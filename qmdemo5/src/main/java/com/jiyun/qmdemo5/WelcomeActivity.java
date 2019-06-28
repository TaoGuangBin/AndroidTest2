package com.jiyun.qmdemo5;

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

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mWelcomeIv;
    private TextView mWelcomeTv;
    private int index = 5;
    private CountDownTimer countDownTimer;
    /**
     * 跳过
     */
    private Button mHomeBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initTimer();
    }

    private void initTimer() {
        ObjectAnimator a1 = ObjectAnimator.ofFloat(mWelcomeIv, "alpha", 0, 1);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(mWelcomeIv, "Rotation", 0, 360);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(mWelcomeIv, "ScaleY", 0, 1);
        ObjectAnimator a4 = ObjectAnimator.ofFloat(mWelcomeIv, "TranslationY", 0, 360);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(5000);
        set.play(a1).with(a2).with(a3).with(a4);
        set.start();
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mWelcomeTv.setText(index + "");
                index--;
            }

            @Override
            public void onFinish() {
                mWelcomeTv.setText(index + "");
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        };
        countDownTimer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer = null;
    }

    private void initView() {
        mWelcomeIv = (ImageView) findViewById(R.id.welcome_iv);
        mWelcomeTv = (TextView) findViewById(R.id.welcome_tv);
        mHomeBt = (Button) findViewById(R.id.home_bt);
        mHomeBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.home_bt:
                initGo();
                break;
        }
    }

    private void initGo() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intent);
        countDownTimer.cancel();
        countDownTimer = null;
    }
}
