package com.jiyun.qmdemo6;

import android.os.Bundle;
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

import com.jiyun.qmdemo6.adapers.HomeVpAndTabAdaper;
import com.jiyun.qmdemo6.fragments.HomeFragmentFour;
import com.jiyun.qmdemo6.fragments.HomeFragmentOne;
import com.jiyun.qmdemo6.fragments.HomeFragmentThree;
import com.jiyun.qmdemo6.fragments.HomeFragmentTwo;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {

    private Toolbar mMainToolbar;
    private ViewPager mMainVp;
    private TabLayout mMainTab;
    private LinearLayout mMainLl;
    private NavigationView mMainNav;
    private DrawerLayout mMainDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        initView();
        initToolBar();
        initDraw();
        initVpAndTab();
    }

    private void initVpAndTab() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        HomeFragmentOne f1 = new HomeFragmentOne();
        HomeFragmentTwo f2 = new HomeFragmentTwo();
        HomeFragmentThree f3 = new HomeFragmentThree();
        HomeFragmentFour f4 = new HomeFragmentFour();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(f4);
        HomeVpAndTabAdaper homeVpAndTabAdaper = new HomeVpAndTabAdaper(fm, fl);
        mMainVp.setAdapter(homeVpAndTabAdaper);
        mMainTab.setupWithViewPager(mMainVp);

    }

    private void initDraw() {
        /*ActionBarDrawerToggle db = new ActionBarDrawerToggle(this, mMainDraw, mMainToolbar, R.string.app_name, R.string.app_name);
        db.syncState();
        mMainDraw.addDrawerListener(db);*/
        mMainDraw.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mMainLl.setX(mMainNav.getHeight() * slideOffset);

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
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mMainVp = (ViewPager) findViewById(R.id.main_vp);
        mMainTab = (TabLayout) findViewById(R.id.main_tab);
        mMainLl = (LinearLayout) findViewById(R.id.main_ll);
        mMainNav = (NavigationView) findViewById(R.id.main_nav);
        mMainDraw = (DrawerLayout) findViewById(R.id.main_draw);
    }
}
