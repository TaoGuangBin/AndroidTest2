package com.jiyun.qmdemo4.adapers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MainVpAndTabAdaper extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fl;
    String[] title = {"添加","展示","文件","收藏"};

    public MainVpAndTabAdaper(FragmentManager fm, ArrayList<Fragment> fl) {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
