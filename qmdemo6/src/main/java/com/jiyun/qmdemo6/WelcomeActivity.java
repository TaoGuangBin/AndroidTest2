package com.jiyun.qmdemo6;

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

    private ImageView mWcIv;
    private TextView mWcTv;
    private CountDownTimer countDownTimer;
    private int index = 5;
    /**
     * 跳过
     */
    private Button mWcBt;
    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initTimer();
    }

    private void initTimer() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mWcIv, "alpha", 0, 1);
        ObjectAnimator Rotation = ObjectAnimator.ofFloat(mWcIv, "Rotation", 0, 360);
        ObjectAnimator ScaleY = ObjectAnimator.ofFloat(mWcIv, "ScaleY", 0, 1);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(mWcIv, "TranslationY", 0, 360);
        set = new AnimatorSet();
        set.play(alpha).with(Rotation).with(ScaleY).with(TranslationY);
        set.setDuration(5000);
        set.start();
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mWcTv.setText(index + "");
                index--;
            }

            @Override
            public void onFinish() {
                mWcTv.setText(index + "");
                Intent intent = new Intent(WelcomeActivity.this, SecondWelcomeActivity.class);
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
        mWcIv = (ImageView) findViewById(R.id.wc_iv);
        mWcTv = (TextView) findViewById(R.id.wc_tv);
        mWcBt = (Button) findViewById(R.id.wc_bt);
        mWcBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.wc_bt:
                Intent intent = new Intent(WelcomeActivity.this, SecondWelcomeActivity.class);
                startActivity(intent);
                countDownTimer.cancel();
                countDownTimer = null;
                set.cancel();
                break;
        }
    }
}
