package com.jiyun.qmdemo4;

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

import com.jiyun.qmdemo4.adapers.MainVpAndTabAdaper;
import com.jiyun.qmdemo4.fragments.InsertFragment;
import com.jiyun.qmdemo4.fragments.QueryAllFragment;
import com.jiyun.qmdemo4.fragments.ShowFragment;
import com.jiyun.qmdemo4.fragments.UpAndDownFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mMainToolbar;
    private ViewPager mMainVp;
    private TabLayout mMainTab;
    private LinearLayout mMainLl;
    private NavigationView mMainNav;
    private DrawerLayout mMainDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initDraw();
        initVPAndTab();
    }

    private void initVPAndTab() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        InsertFragment f1 = new InsertFragment();
        ShowFragment f2 = new ShowFragment();
        UpAndDownFragment f3 = new UpAndDownFragment();
        QueryAllFragment f4 = new QueryAllFragment();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(f4);
        MainVpAndTabAdaper mainVpAndTabAdaper = new MainVpAndTabAdaper(fm, fl);
        mMainVp.setAdapter(mainVpAndTabAdaper);
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
        /*mMainTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

    }

    private void initDraw() {
        ActionBarDrawerToggle bt = new ActionBarDrawerToggle(this, mMainDraw, mMainToolbar, R.string.app_name, R.string.app_name);
        bt.syncState();
        mMainDraw.addDrawerListener(bt);
        mMainDraw.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mMainLl.setX(mMainNav.getHeight()*slideOffset);
            }
        });

    }

    private void initToolbar() {
        mMainToolbar.setTitle("");
        setSupportActionBar(mMainToolbar);
    }

    private void initView() {
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mMainVp = (ViewPager) findViewById(R.id.main_vp);
        mMainTab = (TabLayout) findViewById(R.id.main_tab);
        mMainLl = (LinearLayout) findViewById(R.id.main_ll);
        mMainNav = (NavigationView) findViewById(R.id.main_nav);
        mMainDraw = (DrawerLayout) findViewById(R.id.main_Draw);
    }
}
