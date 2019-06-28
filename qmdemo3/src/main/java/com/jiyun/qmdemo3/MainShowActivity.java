package com.jiyun.qmdemo3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiyun.qmdemo3.myadapers.MainVpAdaper;
import com.jiyun.qmdemo3.myfragments.FileUpAndDownFragment;
import com.jiyun.qmdemo3.myfragments.InsterFragment;
import com.jiyun.qmdemo3.myfragments.QueryAllFragment;
import com.jiyun.qmdemo3.myfragments.ShowFragment;

import java.util.ArrayList;

public class MainShowActivity extends AppCompatActivity {

    private Toolbar mMainToolbar;
    private ViewPager mMainVp;
    private TabLayout mMainTab;
    private LinearLayout mMainLl;
    private NavigationView mMainNav;
    private DrawerLayout mMainDraw;
    private QueryAllFragment queryAllFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_show);
        initView();
        initToolBar();
        initDraw();
        initVpAndTab();
    }

    private void initVpAndTab() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        InsterFragment f1 = new InsterFragment();
        ShowFragment f2 = new ShowFragment();
        FileUpAndDownFragment f3 = new FileUpAndDownFragment();
        queryAllFragment = new QueryAllFragment();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(queryAllFragment);
        MainVpAdaper mainVpAdaper = new MainVpAdaper(fm, fl);
        mMainVp.setAdapter(mainVpAdaper);
        mMainTab.setupWithViewPager(mMainVp);

    }

    private void initDraw() {
        //ActionBarDrawerToggle bt = new ActionBarDrawerToggle(this, mMainDraw, mMainToolbar, R.string.app_name, R.string.app_name);
        //bt.syncState();
        //mMainDraw.addDrawerListener(bt);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "线性");
        menu.add(1, 2, 2, "网格");
        menu.add(1, 3, 3, "瀑布流");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (queryAllFragment.mFqueryRec != null) {
            switch (item.getItemId()) {

                case 1:
                    queryAllFragment.mFqueryRec.setLayoutManager(new LinearLayoutManager(this));
                    break;
                case 2:
                    queryAllFragment.mFqueryRec.setLayoutManager(new GridLayoutManager(this, 2));
                    break;
                case 3:
                    queryAllFragment.mFqueryRec.setLayoutManager(new StaggeredGridLayoutManager(3, GridLayout.VERTICAL));
                    break;
            }
        } else {
            Toast.makeText(this, "你还没加载那页数据呢", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
