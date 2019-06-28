package com.jiyun.qmdemo7;

import com.jiyun.qmdemo7.Beans.BannerBean;
import com.jiyun.qmdemo7.Beans.GirlsBean;

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
    String girlurl = "http://gank.io/";

    @GET("api/data/%E7%A6%8F%E5%88%A9/20/{pace}")
    Observable<GirlsBean> getgirllist(@Path("pace") int pace);


    //http://www.wanandroid.com/banner/json
    String bannerurl = "http://www.wanandroid.com/";

    @GET("banner/json")
    Observable<BannerBean> getbannerlist();

    //http://yun918.cn/study/public/file_upload.php
    String fileupurl = "http://yun918.cn/";

    @POST("study/public/file_upload.php")
    @Multipart
    Call<ResponseBody> fileup(@Part("key") RequestBody requestBody, @Part MultipartBody.Part multipartBody);


}
