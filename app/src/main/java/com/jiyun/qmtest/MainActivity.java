package com.jiyun.qmtest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jiyun.qmtest.adapers.MainVpAdaper;
import com.jiyun.qmtest.fragments.MainInsertFragment;
import com.jiyun.qmtest.fragments.MainQueryALLFragment;
import com.jiyun.qmtest.fragments.MainShowFragment;
import com.jiyun.qmtest.fragments.MainUpFileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mMainVp;
    private TabLayout mMainTab;
    private LinearLayout mMainLl;
    private NavigationView mMainNav;
    private DrawerLayout mMainDraw;
    private Toolbar mMainToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        initDraw();
        initMainVp();
    }

    private void initMainVp() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        MainInsertFragment f1 = new MainInsertFragment();
        MainShowFragment f2 = new MainShowFragment();
        MainUpFileFragment f3 = new MainUpFileFragment();
        MainQueryALLFragment f4 = new MainQueryALLFragment();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(f4);
        MainVpAdaper mainVpAdaper = new MainVpAdaper(fm, fl);
        mMainVp.setAdapter(mainVpAdaper);
        mMainTab.setupWithViewPager(mMainVp);

    }

    private void initDraw() {
       /* ActionBarDrawerToggle bt = new ActionBarDrawerToggle(this, mMainDraw, mMainToolbar, R.string.app_name, R.string.app_name);
        bt.syncState();
        mMainDraw.addDrawerListener(bt);*/
        mMainDraw.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mMainLl.setX(mMainNav.getHeight() * slideOffset);
            }
        });
        View view = View.inflate(this, R.layout.mainheaderlayout, null);
        final View headiv = view.findViewById(R.id.mainheader_iv);
        headiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initToolBar() {
        mMainToolbar.setTitle("");
        mMainToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mMainVp = (ViewPager) findViewById(R.id.main_vp);
        mMainTab = (TabLayout) findViewById(R.id.main_tab);
        mMainLl = (LinearLayout) findViewById(R.id.main_ll);
        mMainNav = (NavigationView) findViewById(R.id.main_nav);
        mMainDraw = (DrawerLayout) findViewById(R.id.main_draw);
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
    }
}
