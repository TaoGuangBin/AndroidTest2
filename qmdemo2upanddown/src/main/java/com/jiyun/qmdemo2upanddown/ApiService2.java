package com.jiyun.qmdemo2upanddown;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService2 {
    //http://yun918.cn/study/public/file_upload.php
    String upurl = "http://yun918.cn/";
    @Multipart
    @POST("study/public/file_upload.php")
    Call<ResponseBody> retorfitup(@Part("key")RequestBody suame, @Part MultipartBody.Part MultipartBody);


}
