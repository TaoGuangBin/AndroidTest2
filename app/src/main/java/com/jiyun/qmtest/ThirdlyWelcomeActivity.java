package com.jiyun.qmtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jiyun.qmtest.adapers.ThirdlyWelcomeVpAdaper;
import com.jiyun.qmtest.fragments.ThirdlyWelcomeOneFragment;
import com.jiyun.qmtest.fragments.ThirdlyWelcomeThreeFragment;
import com.jiyun.qmtest.fragments.ThirdlyWelcomeTwoFragment;

import java.util.ArrayList;

public class ThirdlyWelcomeActivity extends AppCompatActivity {

    private ViewPager mTwcVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdly_welcome);
        initView();
        initVp();

    }

    private void initVp() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        ThirdlyWelcomeOneFragment f1 = new ThirdlyWelcomeOneFragment();
        ThirdlyWelcomeTwoFragment f2 = new ThirdlyWelcomeTwoFragment();
        ThirdlyWelcomeThreeFragment f3 = new ThirdlyWelcomeThreeFragment();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        ThirdlyWelcomeVpAdaper thirdlyWelcomeVpAdaper = new ThirdlyWelcomeVpAdaper(fm, fl);
        mTwcVp.setAdapter(thirdlyWelcomeVpAdaper);

    }

    private void initView() {
        mTwcVp = (ViewPager) findViewById(R.id.twc_vp);
    }
}
