package com.jiyun.qmdemo4.model;

import android.util.Log;

import com.jiyun.qmdemo4.ApiService;
import com.jiyun.qmdemo4.Beans.GirlsBean;
import com.jiyun.qmdemo4.MyCallBack;

import java.util.List;

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
    public void getgirllist(int pace, final MyCallBack callBack) {
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
                        List<GirlsBean.ResultsBean> girllist = girlsBean.getResults();
                        callBack.succeed(girllist);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        callBack.error(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
