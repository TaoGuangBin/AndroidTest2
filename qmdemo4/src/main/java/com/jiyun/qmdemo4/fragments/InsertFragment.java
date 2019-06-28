package com.jiyun.qmdemo4.fragments;


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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.qmdemo4.ApiService;
import com.jiyun.qmdemo4.Beans.BannerBean;
import com.jiyun.qmdemo4.Beans.GirlsBean;
import com.jiyun.qmdemo4.R;
import com.jiyun.qmdemo4.adapers.InsertRecAdaper;
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
    private static final String TAG = "InsertFragment";
    private int pace = 1;
    private View view;
    private RecyclerView mInsertfRec;
    private SmartRefreshLayout mInsertfSm;
    private InsertRecAdaper insertRecAdaper;
    private int index;
    private Banner mInsertBanner;
    private List<BannerBean.DataBean> bannerlist;

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

    private void initbanner() {
        mInsertBanner.setImageLoader(new MyImageLoader()).setImages(bannerlist).start();
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean BannerBean = (BannerBean.DataBean) path;
            Glide.with(context).load(BannerBean.getImagePath()).into(imageView);
        }
    }

    private void initBannerData() {
        Observable<BannerBean> bannerBeanObservable = new Retrofit.Builder()
                .baseUrl(ApiService.bannerurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getbeanlist();
        bannerBeanObservable.subscribeOn(Schedulers.newThread())
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

    private void initData() {
        Observable<GirlsBean> getgirllist = new Retrofit.Builder()
                .baseUrl(ApiService.girlsurl)
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
                        List<GirlsBean.ResultsBean> girlslist = girlsBean.getResults();
                        if (pace == 1) {
                            insertRecAdaper.Refresh(girlslist);
                            mInsertfSm.finishRefresh();
                        } else {
                            insertRecAdaper.LoadMore(girlslist);
                            mInsertfSm.finishLoadMore();
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
        mInsertfRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mInsertfRec.addItemDecoration(dd);
        insertRecAdaper = new InsertRecAdaper(getActivity());
        mInsertfRec.setAdapter(insertRecAdaper);
        mInsertfSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                initData();
            }
        });
        mInsertfSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initData();
            }
        });
        insertRecAdaper.setIsChick(new InsertRecAdaper.IsChick() {
            @Override
            public void OneChick(int i) {
                insertRecAdaper.insert(i);
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void LongChick(int i) {
                index = i;
            }
        });
        registerForContextMenu(mInsertfRec);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 1, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                insertRecAdaper.Delete(index);
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initView(View view) {
        mInsertfRec = (RecyclerView) view.findViewById(R.id.insertf_rec);
        mInsertfSm = (SmartRefreshLayout) view.findViewById(R.id.insertf_sm);
        mInsertBanner = (Banner) view.findViewById(R.id.insert_banner);
    }
}
