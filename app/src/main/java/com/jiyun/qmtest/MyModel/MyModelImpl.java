package com.jiyun.qmtest.MyModel;

import android.util.Log;

import com.jiyun.qmtest.ApiService;
import com.jiyun.qmtest.MyCallBack;
import com.jiyun.qmtest.bean.GirlsBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyModelImpl implements MyModel{
    private int pace = 1;
    private static final String TAG = "MyModelImpl";
    @Override
    public void getGirllist(final MyCallBack myCallBack) {
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
                        myCallBack.succeed(girllist);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        myCallBack.defeated(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
