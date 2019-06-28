package com.jiyun.qmdemo4.fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiyun.qmdemo4.ApiService;
import com.jiyun.qmdemo4.R;
import com.jiyun.qmdemo4.ShowLocalBroadcastMsg;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class UpAndDownFragment extends Fragment implements View.OnClickListener {


    private View view;
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
    private static final String TAG = "UpAndDownFragment";
    private MyBroadcastReceive myBroadcastReceive;

    public UpAndDownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_and_down, null);
        initView(view);
        initLocalBroadcast();
        return view;
    }

    private void initLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MyMsg");
        myBroadcastReceive = new MyBroadcastReceive();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadcastReceive, intentFilter);
    }

    class MyBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("MSG");
            Intent intent1 = new Intent(getActivity(), ShowLocalBroadcastMsg.class);
            intent1.putExtra("showmsg", msg);
            PendingIntent activity = PendingIntent.getActivity(getActivity(), 100, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            Notification build = new NotificationCompat.Builder(getActivity(), "1")
                    .setAutoCancel(true)
                    .setContentTitle("广播通知")
                    .setContentText("您有一个广播通知")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(activity)
                    .build();
            nm.notify(1, build);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadcastReceive);
    }

    private void initView(View view) {
        mOkupBt = (Button) view.findViewById(R.id.okup_bt);
        mOkupBt.setOnClickListener(this);
        mRetorupBt = (Button) view.findViewById(R.id.retorup_bt);
        mRetorupBt.setOnClickListener(this);
        mThreaddownBt = (Button) view.findViewById(R.id.threaddown_bt);
        mThreaddownBt.setOnClickListener(this);
        mOkdownBt = (Button) view.findViewById(R.id.okdown_bt);
        mOkdownBt.setOnClickListener(this);
        mMaindownPb = (ProgressBar) view.findViewById(R.id.maindown_pb);
        mUpfileTv = (TextView) view.findViewById(R.id.upfile_tv);
        mMainuplbBt = (Button) view.findViewById(R.id.mainuplb_bt);
        mMainuplbBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okup_bt:
                initOKUP();
                break;
            case R.id.retorup_bt:
                initRetorup();
                break;
            case R.id.threaddown_bt:
                initthreaddown();
                break;
            case R.id.okdown_bt:
                initOkdown();
                break;
            case R.id.mainuplb_bt:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        Intent intent = new Intent();
        intent.putExtra("MSG", "我的广播信息");
        intent.setAction("MyMsg");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void initOkdown() {
        String FileDownUrl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
        String FileName = FileDownUrl.substring(FileDownUrl.lastIndexOf("/") + 1, FileDownUrl.length());
        String FilePath = Environment.getExternalStorageDirectory() + File.separator + FileName;
        final File file = new File(FilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url(FileDownUrl)
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
                final int contentLength = (int) response.body().contentLength();
                mMaindownPb.setMax(contentLength);
                int readlength = 0;
                int acculength = 0;
                byte[] bytes = new byte[1024 * 8];
                while ((readlength = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, readlength);
                    acculength += readlength;
                    final int finalAcculength = acculength;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMaindownPb.setProgress(finalAcculength);
                            mUpfileTv.setText(finalAcculength * 100 / contentLength + "");
                        }
                    });

                }
                inputStream.close();
                fileOutputStream.close();
            }
        });


    }

    private void initthreaddown() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String FileDownUrl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
                String FileName = FileDownUrl.substring(FileDownUrl.lastIndexOf("/") + 1, FileDownUrl.length());
                String filepath = Environment.getExternalStorageDirectory() + File.separator + FileName;
                File file = new File(filepath);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    URL url = new URL(FileDownUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    final int contentLength = conn.getContentLength();
                    mMaindownPb.setMax(contentLength);
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int readlength = 0;
                    int acculength = 0;
                    byte[] bytes = new byte[1024 * 8];
                    while ((readlength = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, readlength);
                        acculength += readlength;
                        final int finalAcculength = acculength;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMaindownPb.setProgress(finalAcculength);
                                mUpfileTv.setText(finalAcculength * 100 / contentLength + "");
                            }
                        });
                    }
                    inputStream.close();
                    fileOutputStream.close();
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void initRetorup() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorup: " + "文件为空");
        }
        RequestBody suame = RequestBody.create(MediaType.parse("text/plain"), "1811A");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multpartbody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        new Retrofit.Builder()
                .baseUrl(ApiService.retorupurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .retorup(suame, multpartbody)
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

    //下载接口
    //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
    //上传 网址
    //http://yun918.cn/study/public/file_upload.php
    private void initOKUP() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initOKUP: " + "文件为空");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1811c")
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUpfileTv.setText(result);
                    }
                });
            }
        });


    }
}
