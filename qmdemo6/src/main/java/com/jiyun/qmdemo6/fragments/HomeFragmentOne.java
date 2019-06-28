package com.jiyun.qmdemo6.fragments;


import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.qmdemo6.ApiService;
import com.jiyun.qmdemo6.Girlsutil;
import com.jiyun.qmdemo6.R;
import com.jiyun.qmdemo6.adapers.HomeFragmentOneRecAdaper;
import com.jiyun.qmdemo6.beans.BannerBean;
import com.jiyun.qmdemo6.beans.GirlBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
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
public class HomeFragmentOne extends Fragment {

    private static final String TAG = "HomeFragmentOne";
    private View view;
    private RecyclerView mHomeRec;
    private SmartRefreshLayout mHomeSm;
    private Banner mHomeBanner;
    private List<BannerBean.DataBean> bannerlist;
    private HomeFragmentOneRecAdaper homeFragmentOneRecAdaper;
    private int pace = 1;

    public HomeFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home_fragment_one, null);
        initView(view);
        initRec();
        initData();
        initBannerData();

        return view;
    }

    private void initBanner() {
        mHomeBanner.setImages(bannerlist).setImageLoader(new MyBannerImageLoader()).start();
    }

    class MyBannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean bannerbean = (BannerBean.DataBean) path;
            Glide.with(context).load(bannerbean.getImagePath()).into(imageView);
        }
    }

    private void initBannerData() {
        Observable<BannerBean> getbannerlist = new Retrofit.Builder()
                .baseUrl(ApiService.basebannerurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
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
                        bannerlist = bannerBean.getData();
                        initBanner();
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

    private void initData() {
        Observable<GirlBean> getgrillist = new Retrofit.Builder()
                .baseUrl(ApiService.baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getgrillist(pace);
        getgrillist.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GirlBean girlBean) {
                        List<GirlBean.ResultsBean> girllist = girlBean.getResults();
                        if (pace == 1) {
                            homeFragmentOneRecAdaper.initDate(girllist);
                            mHomeSm.finishRefresh();
                        } else {
                            homeFragmentOneRecAdaper.initLoadMore(girllist);
                            mHomeSm.finishLoadMore();
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
        mHomeRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mHomeRec.addItemDecoration(dd);
        homeFragmentOneRecAdaper = new HomeFragmentOneRecAdaper(getActivity());
        mHomeRec.setAdapter(homeFragmentOneRecAdaper);
        mHomeSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                initData();
            }
        });
        mHomeSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initData();
            }
        });
        homeFragmentOneRecAdaper.setIsChick(new HomeFragmentOneRecAdaper.IsChick() {
            @Override
            public void onechick(int i) {
                homeFragmentOneRecAdaper.insert(i);
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView(View view) {
        mHomeRec = (RecyclerView) view.findViewById(R.id.home_rec);
        mHomeSm = (SmartRefreshLayout) view.findViewById(R.id.home_sm);
        mHomeBanner = (Banner) view.findViewById(R.id.home_banner);
    }
}
