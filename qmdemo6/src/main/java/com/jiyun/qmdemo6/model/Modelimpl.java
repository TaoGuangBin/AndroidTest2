package com.jiyun.qmdemo6.model;

import android.util.Log;

import com.jiyun.qmdemo6.ApiService;
import com.jiyun.qmdemo6.MyCallBack;
import com.jiyun.qmdemo6.beans.GirlBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Modelimpl implements MyModel{
    private static final String TAG = "Modelimpl";
    @Override
    public void getgirllist(final MyCallBack callBack) {
        Observable<GirlBean> getgrillist = new Retrofit.Builder()
                .baseUrl(ApiService.baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getgrillist(1);
        getgrillist.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GirlBean girlBean) {
                        List<GirlBean.ResultsBean> girllist = girlBean.getResults();
                        callBack.succeed(girllist);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                        callBack.error(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
