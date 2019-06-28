package com.jiyun.qmdemo2upanddown;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * OKHttp上传
     */
    private Button mOkupBt;
    /**
     * Retorfit上传
     */
    private Button mRetorupBt;
    /**
     * 单线程文件下载
     */
    private Button mThreaddownBt;
    /**
     * OkHttp文件下载
     */
    private Button mOkdownBt;
    private ProgressBar mMaindownPb;
    /**
     * 30dp
     */
    private TextView mUpfileTv;
    /**
     * 发送广播
     */
    private Button mMainuplbBt;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mOkupBt = (Button) findViewById(R.id.okup_bt);
        mOkupBt.setOnClickListener(this);
        mRetorupBt = (Button) findViewById(R.id.retorup_bt);
        mRetorupBt.setOnClickListener(this);
        mThreaddownBt = (Button) findViewById(R.id.threaddown_bt);
        mThreaddownBt.setOnClickListener(this);
        mOkdownBt = (Button) findViewById(R.id.okdown_bt);
        mOkdownBt.setOnClickListener(this);
        mMaindownPb = (ProgressBar) findViewById(R.id.maindown_pb);
        mUpfileTv = (TextView) findViewById(R.id.upfile_tv);
        mMainuplbBt = (Button) findViewById(R.id.mainuplb_bt);
        mMainuplbBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okup_bt:
                initOkUp();
                break;
            case R.id.retorup_bt:
                initRetorup();
                break;
            case R.id.threaddown_bt:
                initThreaddown();
                break;
            case R.id.okdown_bt:
                initOkDown();
                break;
            case R.id.mainuplb_bt:
                break;
        }
    }

    private void initThreaddown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String threaddownurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
                String filename = threaddownurl.substring(threaddownurl.lastIndexOf("/") + 1, threaddownurl.length());
                String filepath = Environment.getExternalStorageDirectory() + File.separator + filename;
                File file = new File(filepath);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    URL url = new URL(threaddownurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    InputStream inputStream = conn.getInputStream();
                    final int contentLength = conn.getContentLength();
                    if (contentLength <= 0) {
                        Log.d(TAG, "initThreaddown: " + "总长度为0");
                        return;
                    }
                    mMaindownPb.setMax(contentLength);
                    int readlength = 0;
                    int currlength = 0;
                    byte[] bytes = new byte[1024 * 8];
                    while ((readlength = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, readlength);
                        currlength += readlength;
                        final int finalCurrlength = currlength;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMaindownPb.setProgress(finalCurrlength);
                                mUpfileTv.setText(finalCurrlength * 100 / contentLength + "");
                            }
                        });
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void initOkDown() {
        String okdownurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
        String filename = okdownurl.substring(okdownurl.lastIndexOf("/") + 1, okdownurl.length());
        String filepath = Environment.getExternalStorageDirectory() + File.separator + filename;
        final File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url(okdownurl)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final long alllength = response.body().contentLength();
                mMaindownPb.setMax((int) alllength);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = response.body().byteStream();
                int readlength = 0;
                int currlength = 0;
                byte[] by = new byte[1024 * 8];
                while ((readlength = inputStream.read(by)) != -1) {
                    fileOutputStream.write(by, 0, readlength);
                    currlength += readlength;
                    final int finalCurrlength = currlength;
                    final int finalCurrlength1 = currlength;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMaindownPb.setProgress(finalCurrlength);
                            mUpfileTv.setText(finalCurrlength1 * 100 / alllength + "");
                        }
                    });
                }

            }
        });

    }


    private void initRetorup() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorup: " + "文件不存在");
        }
        RequestBody resume = RequestBody.create(MediaType.parse("text/plain"), "1811A");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multipartbody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        new Retrofit.Builder()
                .baseUrl(ApiService.Upurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getfileup(resume, multipartbody)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            Log.d(TAG, "onResponse: " + result);
                            mUpfileTv.setText(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    private void initOkUp() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initOkUp: " + "文件为空");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1811A")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(multipartBody)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUpfileTv.setText(result);
                    }
                });
            }
        });
    }
}
