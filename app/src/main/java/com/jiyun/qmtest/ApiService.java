package com.jiyun.qmtest;

import com.jiyun.qmtest.bean.BannerBean;
import com.jiyun.qmtest.bean.GirlsBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1
    String GirlsUrl = "http://gank.io/";

    @GET("api/data/%E7%A6%8F%E5%88%A9/20/{pace}")
    Observable<GirlsBean> getgirllist(@Path("pace") int pace);

    //http://www.wanandroid.com/banner/json
    String BannerUrl = "http://www.wanandroid.com/";

    @GET("banner/json")
    Observable<BannerBean> getbannerlist();

    //http://yun918.cn/study/public/file_upload.php
    String retorurl = "http://yun918.cn/";
    @Multipart
    @POST("study/public/file_upload.php")
    Call<ResponseBody> upfile(@Part("key") RequestBody params, @Part MultipartBody.Part MultipartBody);


}
