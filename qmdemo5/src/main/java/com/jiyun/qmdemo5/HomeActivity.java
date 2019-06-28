package com.jiyun.qmdemo5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.jiyun.qmdemo5.Adapers.MyHomeRecAdaper;
import com.jiyun.qmdemo5.presenter.Presenterimpl;
import com.jiyun.qmdemo5.view.Myview;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;

public class HomeActivity extends AppCompatActivity implements Myview {

    private RecyclerView mHomeRec;
    private SmartRefreshLayout mHomeSm;
    private Presenterimpl presenterimpl;
    private MyHomeRecAdaper myHomeRecAdaper;
    private static final String TAG = "HomeActivity";
    private int pace = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initPresenter();
        initRec();
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mHomeRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        mHomeRec.addItemDecoration(dd);
        myHomeRecAdaper = new MyHomeRecAdaper(this);
        mHomeRec.setAdapter(myHomeRecAdaper);
        mHomeSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                presenterimpl.getlist(pace);
            }
        });
        mHomeSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                presenterimpl.getlist(pace);
            }
        });

    }

    private void initPresenter() {
        presenterimpl = new Presenterimpl(this);
        presenterimpl.getlist(pace);
    }

    private void initView() {
        mHomeRec = (RecyclerView) findViewById(R.id.home_rec);
        mHomeSm = (SmartRefreshLayout) findViewById(R.id.home_sm);
    }


    @Override
    public void succeedbannerview(List<BannerBean.DataBean> bannerlist) {
        myHomeRecAdaper.initBanner(bannerlist);
    }

    @Override
    public void errorbannerview(String error) {
        Log.d(TAG, "errorbannerview: " + error);
    }

    @Override
    public void succeedgirllistview(List<GirlsBean.ResultsBean> girllist) {
        if (pace == 1) {
            myHomeRecAdaper.initRefresh(girllist);
            mHomeSm.finishRefresh();
        } else {
            myHomeRecAdaper.initLoadMore(girllist);
            mHomeSm.finishLoadMore();
        }


    }

    @Override
    public void errorgirlview(String error) {
        Log.d(TAG, "errorbannerview: " + error);
    }
}
