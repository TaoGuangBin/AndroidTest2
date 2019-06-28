package com.jiyun.qmtest.adapers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ThirdlyWelcomeVpAdaper extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fl;

    public ThirdlyWelcomeVpAdaper(FragmentManager fm, ArrayList<Fragment> fl) {
        super(fm);
        this.fl = fl;
    }

    @Override
    public Fragment getItem(int i) {
        return fl.get(i);
    }

    @Override
    public int getCount() {
        return fl.size();
    }
}
