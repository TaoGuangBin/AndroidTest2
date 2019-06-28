package com.jiyun.qmdemo7.fragments;


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

import com.bumptech.glide.Glide;
import com.jiyun.qmdemo7.ApiService;
import com.jiyun.qmdemo7.Beans.BannerBean;
import com.jiyun.qmdemo7.Beans.GirlsBean;
import com.jiyun.qmdemo7.R;
import com.jiyun.qmdemo7.adapers.InsertRecAdaper;
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
public class InsertFragment extends Fragment {

    private List<BannerBean.DataBean> bannerlist;
    private View view;
    private RecyclerView mInsertRec;
    private SmartRefreshLayout mInsertSm;
    private Banner mInsertBanner;
    private int pace = 1;
    private static final String TAG = "InsertFragment";
    private InsertRecAdaper insertRecAdaper;

    public InsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, null);
        initView(view);
        initRec();
        initData();
        initBannerData();
        return view;
    }

    private void initBannerData() {
        Observable<BannerBean> getbannerlist = new Retrofit.Builder()
                .baseUrl(ApiService.bannerurl)
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
                        bannerlist = bannerBean.getData();
                        initbanner();
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

    private void initbanner() {
        mInsertBanner.setImages(bannerlist).setImageLoader(new MyImageLoader()).start();
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean bannerbean = (BannerBean.DataBean) path;
            Glide.with(getActivity()).load(bannerbean.getImagePath()).into(imageView);
        }
    }

    private void initData() {
        Observable<GirlsBean> getgirllist = new Retrofit.Builder()
                .baseUrl(ApiService.girlurl)
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
                            insertRecAdaper.initdata(girllist);
                            mInsertSm.finishRefresh();
                        } else {
                            insertRecAdaper.loadmore(girllist);
                            mInsertSm.finishLoadMore();
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
        mInsertRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mInsertRec.addItemDecoration(dd);
        insertRecAdaper = new InsertRecAdaper(getActivity());
        mInsertRec.setAdapter(insertRecAdaper);
        mInsertSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initData();
            }
        });
        mInsertSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace=1;
                initData();
            }
        });

    }

    private void initView(View view) {
        mInsertRec = (RecyclerView) view.findViewById(R.id.insert_rec);
        mInsertSm = (SmartRefreshLayout) view.findViewById(R.id.insert_sm);
        mInsertBanner = (Banner) view.findViewById(R.id.insert_banner);
    }
}
