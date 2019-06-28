package com.jiyun.qmtest.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jiyun.qmtest.ApiService;
import com.jiyun.qmtest.GirlUtils;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.adapers.MainInsertAdaper;
import com.jiyun.qmtest.bean.BannerBean;
import com.jiyun.qmtest.bean.GirlsBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainInsertFragment extends Fragment {
    private static final String TAG = "MainInsertFragment";
    private int pace = 1;
    private View view;
    private RecyclerView mMaininsertRec;
    private SmartRefreshLayout mMaininsertSmart;
    private Banner mMaininsertBanner;
    private MainInsertAdaper mainInsertAdaper;
    private List<BannerBean.DataBean> bannerlist;
    private int index;
    //1811A 陶广滨
    public MainInsertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment_main_insert
        View view = inflater.inflate(R.layout.fragment_mains_insert, null);
        initView(view);
        initRec();


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            initData();
            initBannerData();
        }
    }

    private void initBannerData() {
        Observable<BannerBean> getbannerlist = new Retrofit.Builder()
                .baseUrl(ApiService.BannerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getbannerlist();
        getbannerlist.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (mainInsertAdaper!=null){
                            bannerlist = bannerBean.getData();
                            initBanner();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initBanner() {
        mMaininsertBanner.setImages(bannerlist).setImageLoader(new MyImageLoader()).start();
    }
    class MyImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean bannerbean = (BannerBean.DataBean) path;
            Glide.with(getActivity()).load(bannerbean.getImagePath()).into(imageView);
        }
    }

    private void initData() {
        Observable<GirlsBean> getgirllist = new Retrofit.Builder()
                .baseUrl(ApiService.GirlsUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getgirllist(pace);
        getgirllist.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        List<GirlsBean.ResultsBean> girllist = girlsBean.getResults();
                        if (pace == 1) {
                            if (mainInsertAdaper!=null){
                                mainInsertAdaper.initDate(girllist);
                                mMaininsertSmart.finishRefresh();
                            }

                        }else {
                            if (mainInsertAdaper!=null){
                                mainInsertAdaper.initLoadmore(girllist);
                                mMaininsertSmart.finishLoadMore();
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mMaininsertRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mMaininsertRec.addItemDecoration(dd);
        mainInsertAdaper = new MainInsertAdaper(getActivity());
        mMaininsertRec.setAdapter(mainInsertAdaper);
        mMaininsertSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initData();
            }
        });
        mMaininsertSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace=1;
                initData();
            }
        });
        registerForContextMenu(mMaininsertRec);
        mainInsertAdaper.setIsChick(new MainInsertAdaper.IsChick() {
            @Override
            public void onechick(int i) {
                mainInsertAdaper.initinsert(i);
            }

            @Override
            public void longchick(int i) {
                index = i;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,1,1,"删除");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
                mainInsertAdaper.initdelete(index);
            break;

        }
        return super.onContextItemSelected(item);
    }

    private void initView(View view) {
        mMaininsertRec = (RecyclerView) view.findViewById(R.id.maininsert_rec);
        mMaininsertSmart = (SmartRefreshLayout) view.findViewById(R.id.maininsert_smart);
        mMaininsertBanner = (Banner) view.findViewById(R.id.maininsert_banner);
    }


}
