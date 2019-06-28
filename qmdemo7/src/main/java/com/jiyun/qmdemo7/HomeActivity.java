package com.jiyun.qmdemo7;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jiyun.qmdemo7.adapers.HomeVpAndTabAdaper;
import com.jiyun.qmdemo7.fragments.FileFragment;
import com.jiyun.qmdemo7.fragments.InsertFragment;
import com.jiyun.qmdemo7.fragments.QueryAllFragment;
import com.jiyun.qmdemo7.fragments.ShowFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mMainToolbar;
    private ViewPager mMainVp;
    private LinearLayout mMainLl;
    private NavigationView mMainNav;
    private DrawerLayout mMainDraw;
    private TabLayout mMainTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initToolBar();
        initDraw();
        initVpAndTab();
    }

    private void initVpAndTab() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        InsertFragment f1 = new InsertFragment();
        ShowFragment f2 = new ShowFragment();
        FileFragment f3 = new FileFragment();
        QueryAllFragment f4 = new QueryAllFragment();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(f4);
        HomeVpAndTabAdaper homeVpAndTabAdaper = new HomeVpAndTabAdaper(fm, fl);
        mMainVp.setAdapter(homeVpAndTabAdaper);
        mMainTab.setupWithViewPager(mMainVp);
        mMainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mMainToolbar.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initDraw() {
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
        mMainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mMainToolbar);

    }

    private void initView() {
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mMainVp = (ViewPager) findViewById(R.id.main_vp);
        mMainLl = (LinearLayout) findViewById(R.id.main_ll);
        mMainNav = (NavigationView) findViewById(R.id.main_nav);
        mMainDraw = (DrawerLayout) findViewById(R.id.main_draw);
        mMainTab = (TabLayout) findViewById(R.id.main_tab);
    }



}
