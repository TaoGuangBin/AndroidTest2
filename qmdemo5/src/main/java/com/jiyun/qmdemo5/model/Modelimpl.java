package com.jiyun.qmdemo5.model;

import android.util.Log;

import com.jiyun.qmdemo5.ApiService;
import com.jiyun.qmdemo5.MyCallBack;

import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Modelimpl implements MyModel {
    private static final String TAG = "Modelimpl";

    @Override
    public void getlist(int pace, MyCallBack callBack) {
        initGirls(callBack, pace);
        initBanner(callBack);
    }

    private void initBanner(final MyCallBack callBack) {
        Observable<BannerBean> getbanner = new Retrofit.Builder()
                .baseUrl(ApiService.bannerurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getbanner();
        getbanner.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        List<BannerBean.DataBean> bannerlist = bannerBean.getData();
                        callBack.bannersucceed(bannerlist);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        callBack.bannererror(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initGirls(final MyCallBack callBack, int pace) {
        Observable<GirlsBean> getgirllist = new Retrofit.Builder()
                .baseUrl(ApiService.girllisturl)
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
                        callBack.girlsucceed(girllist);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        callBack.girlerror(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
