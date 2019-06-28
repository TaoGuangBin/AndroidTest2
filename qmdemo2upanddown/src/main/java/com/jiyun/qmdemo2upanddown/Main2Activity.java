package com.jiyun.qmdemo2upanddown;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import java.io.OutputStream;
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
import retrofit2.http.Multipart;
import retrofit2.http.Url;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    //1811A 陶广滨
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
    private static final String TAG = "Main2Activity";
    private MyLocalBroadcast myLocalBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initLocalBroadcast();
    }

    private void initLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WSL");
        myLocalBroadcast = new MyLocalBroadcast();
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcast, intentFilter);
    }

    class MyLocalBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Intent intent1 = new Intent(Main2Activity.this, LocalBroadActivity.class);
            intent1.putExtra("newmsg", msg);
            PendingIntent activity = PendingIntent.getActivity(Main2Activity.this, 100, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification build = new NotificationCompat.Builder(Main2Activity.this, "1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("我的广播")
                    .setContentText("我的广播通知")
                    .setAutoCancel(true)
                    .setContentIntent(activity)
                    .build();
            nm.notify(1, build);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadcast);
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
                initOkup();
                break;
            case R.id.retorup_bt:
                initRetorup();
                break;
            case R.id.threaddown_bt:
                initThreadDown();
                break;
            case R.id.okdown_bt:
                initOkDown();
                break;
            case R.id.mainuplb_bt:
                initSendMsg();
                break;
        }
    }

    private void initOkDown() {
        //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
        String downurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
        String filename = downurl.substring(downurl.lastIndexOf("/") + 1, downurl.length());
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
                .url(downurl)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                final long contentLength = response.body().contentLength();
                mMaindownPb.setMax((int) contentLength);
                int readleng = 0;
                int acuccleng = 0;
                byte[] bytes = new byte[1024 * 8];
                while ((readleng = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, readleng);
                    acuccleng += readleng;
                    final int finalAcuccleng = acuccleng;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMaindownPb.setProgress(finalAcuccleng);
                            mUpfileTv.setText(finalAcuccleng * 100 / contentLength + "");
                        }
                    });
                }

            }
        });

    }

    private void initThreadDown() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
                String DownUrl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
                String filename = DownUrl.substring(DownUrl.lastIndexOf("/") + 1, DownUrl.length());
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
                    URL url = new URL(DownUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    final int contentLength = conn.getContentLength();
                    if (contentLength <= 0) {
                        Log.d(TAG, "initThreadDown: " + "文件长度为0");
                    }
                    mMaindownPb.setMax(contentLength);
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int readlength = 0;
                    int aucclength = 0;
                    byte[] bytes = new byte[1024 * 8];
                    while ((readlength = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, readlength);
                        aucclength += readlength;
                        final int finalAucclength = aucclength;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMaindownPb.setProgress(finalAucclength);
                                mUpfileTv.setText(finalAucclength * 100 / contentLength + "");
                            }
                        });
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void initSendMsg() {
        Intent intent = new Intent();
        intent.setAction("WSL");
        intent.putExtra("msg", "我的广播事件呦");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void initRetorup() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorup: " + "文件为空");
        }
        RequestBody suame = RequestBody.create(MediaType.parse("text/plian"), "1811A");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        new Retrofit.Builder()
                .baseUrl(ApiService2.upurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService2.class)
                .retorfitup(suame, multipartBody)
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

    private void initOkup() {
        //http://yun918.cn/study/public/file_upload.php
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initOkup: " + "文件为空");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("key", "1811A")
                .addFormDataPart("file", file.getName(), requestBody)
                .setType(MultipartBody.FORM)
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
