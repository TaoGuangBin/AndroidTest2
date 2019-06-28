package com.jiyun.qmdemo3.myfragments;


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
import com.jiyun.qmdemo3.ApiService;
import com.jiyun.qmdemo3.GirlsUtils;
import com.jiyun.qmdemo3.R;
import com.jiyun.qmdemo3.mainbean.BannerBean;
import com.jiyun.qmdemo3.mainbean.GirlsBean;
import com.jiyun.qmdemo3.myadapers.InsterFragmentRecAdaper;
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
public class InsterFragment extends Fragment {
    private int pace = 1;
    private static final String TAG = "InsterFragment";
    private View view;
    private Banner mFinsertBanner;
    private RecyclerView mFinsertRec;
    private SmartRefreshLayout mFinsertSmart;
    private InsterFragmentRecAdaper insterFragmentRecAdaper;
    private List<BannerBean.DataBean> bannerlist;
    private int index;

    public InsterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inster, null);
        initView(view);
        initRec();
        initRecData();
        initBannerData();
        return view;
    }

    private void initBanner() {
        mFinsertBanner.setImages(bannerlist).setImageLoader(new MyImageLoader()).start();
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean banners = (BannerBean.DataBean) path;
            Glide.with(getActivity()).load(banners.getImagePath()).into(imageView);
        }
    }

    private void initRecData() {
        Observable<GirlsBean> getgirllist = new Retrofit.Builder()
                .baseUrl(ApiService.girlUrl)
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
                            insterFragmentRecAdaper.initData(girllist);
                            mFinsertSmart.finishRefresh();
                        } else {
                            insterFragmentRecAdaper.initLoadMore(girllist);
                            mFinsertSmart.finishLoadMore();
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
        mFinsertRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mFinsertRec.addItemDecoration(dd);
        insterFragmentRecAdaper = new InsterFragmentRecAdaper(getActivity());
        mFinsertRec.setAdapter(insterFragmentRecAdaper);
        mFinsertSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                initRecData();
            }
        });
        mFinsertSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                initRecData();
            }
        });
        registerForContextMenu(mFinsertRec);
        insterFragmentRecAdaper.setIsChick(new InsterFragmentRecAdaper.IsChick() {
            @Override
            public void onechick(int i) {
                insterFragmentRecAdaper.insert(i);
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Longchick(int i) {
                index = i;
            }
        });
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
                insterFragmentRecAdaper.Delete(index);
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initBannerData() {
        Observable<BannerBean> getbannerlist = new Retrofit.Builder()
                .baseUrl(ApiService.bannerUrl)
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

    private void initView(View view) {
        mFinsertBanner = (Banner) view.findViewById(R.id.Finsert_banner);
        mFinsertRec = (RecyclerView) view.findViewById(R.id.Finsert_rec);
        mFinsertSmart = (SmartRefreshLayout) view.findViewById(R.id.Finsert_smart);
    }
}
