package com.jiyun.qmdemo7;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jiyun.qmdemo7.Beans.GirlsBean;
import com.jiyun.qmdemo7.adapers.ShowRecAdaper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmLoadMoreAndRefreshActivity extends AppCompatActivity {
    private RecyclerView mMainRec;
    private SmartRefreshLayout mMainSm;
    private int pace = 1;
    private static final String TAG = "SmLoadMoreAndRefreshAct";
    private ShowRecAdaper showRecAdaper;

    //陶广滨
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_load_more_and_refresh);
        initView();
        initRec();
        initData();
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/" + pace)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
                Gson gson = new Gson();
                GirlsBean girlsBean = gson.fromJson(result, GirlsBean.class);
                final List<GirlsBean.ResultsBean> girllist = girlsBean.getResults();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pace == 1) {
                            showRecAdaper.initdata(girllist);
                            mMainSm.finishRefresh();
                        } else {
                            showRecAdaper.loadmore(girllist);
                            mMainSm.finishLoadMore();
                        }
                    }
                });


            }
        });


    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mMainRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        mMainRec.addItemDecoration(dd);
        showRecAdaper = new ShowRecAdaper(this);
        mMainRec.setAdapter(showRecAdaper);
        mMainSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                initData();
            }
        });
        mMainSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initData();
            }
        });

    }

    private void initView() {
        mMainRec = (RecyclerView) findViewById(R.id.main_rec);
        mMainSm = (SmartRefreshLayout) findViewById(R.id.main_sm);
    }
}
