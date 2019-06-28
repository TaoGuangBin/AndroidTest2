package com.jiyun.qmtest.fragments;


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
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiyun.qmtest.ApiService;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.ShowNTActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
public class MainUpFileFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * OKHttp上传
     */
    private Button mOkBt;
    /**
     * Retorfit上传
     */
    private Button mRetorBt;
    /**
     * 单线程文件下载
     */
    private Button mXiazaiBt;
    private TextView mUpFile;
    private String loadurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
    private ProgressBar mXiazaiPb;
    private static final String TAG = "MainUpFileFragment";
    /**
     * OkHttp文件下载
     */
    private Button mOkxiazaiBt;
    /**
     * 发送广播
     */
    private Button mMainuplbBt;
    private MyBroadcastReceiver myBroadcastReceiver;

    public MainUpFileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_up_file, null);
        initView(view);
        initLocalBroadcast();

        return view;
    }

    private void initLocalBroadcast() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("XXX");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadcastReceiver, intentFilter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Intent intent1 = new Intent(getActivity(), ShowNTActivity.class);
            intent1.putExtra("msg", msg);
            PendingIntent activity = PendingIntent.getActivity(getActivity(), 100, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            Notification build = new NotificationCompat.Builder(getActivity(), "1")
                    .setAutoCancel(true)
                    .setContentTitle("广播")
                    .setContentText("广播通知")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(activity)
                    .build();
            nm.notify(1, build);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadcastReceiver);

    }

    private void initView(View view) {
        mOkBt = (Button) view.findViewById(R.id.ok_bt);
        mOkBt.setOnClickListener(this);
        mRetorBt = (Button) view.findViewById(R.id.retor_bt);
        mRetorBt.setOnClickListener(this);
        mXiazaiBt = (Button) view.findViewById(R.id.xiazai_bt);
        mXiazaiBt.setOnClickListener(this);
        mUpFile = (TextView) view.findViewById(R.id.up_file);
        mXiazaiPb = (ProgressBar) view.findViewById(R.id.xiazai_pb);
        mOkxiazaiBt = (Button) view.findViewById(R.id.okxiazai_bt);
        mOkxiazaiBt.setOnClickListener(this);
        mUpFile.setOnClickListener(this);
        mMainuplbBt = (Button) view.findViewById(R.id.mainuplb_bt);
        mMainuplbBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ok_bt:
                initOkUpFile();
                break;
            case R.id.retor_bt:
                initRetorUpFile();
                break;
            case R.id.xiazai_bt:
                initXiaZai();
                break;
            case R.id.okxiazai_bt:
                initOkDown();
                break;

            case R.id.mainuplb_bt:
                initSendmsg();
                break;
        }
    }

    private void initSendmsg() {
        Intent intent = new Intent();
        intent.putExtra("msg", "我的广播事件");
        intent.setAction("XXX");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void initOkDown() {
        //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
        String filename2 = loadurl.substring(loadurl.lastIndexOf("/") + 1, loadurl.length());
        String filepath2 = Environment.getExternalStorageDirectory() + File.separator + filename2;


        final File file = new File(filepath2);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://yun918.cn/study/public/res/UnknowApp-1.0.apk")
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final long contentLength = response.body().contentLength();
                mXiazaiPb.setMax((int) contentLength);
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int readlength = 0;
                int currreadlength = 0;
                byte[] by = new byte[1024 * 8];
                while ((readlength = inputStream.read(by)) != -1) {
                    fileOutputStream.write(by, 0, readlength);
                    currreadlength += readlength;
                    mXiazaiPb.setProgress(currreadlength);
                    final int finalCurrreadlength = currreadlength;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mUpFile.setText((finalCurrreadlength * 100 / contentLength) + "");
                        }
                    });
                }


                inputStream.close();
                fileOutputStream.close();
            }
        });


    }

    private void initXiaZai() {
        //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
        new Thread() {
            @Override
            public void run() {
                super.run();
                //设置文件名字
                String filename = loadurl.substring(loadurl.lastIndexOf("/") + 1, loadurl.length());
                //设置 下载路径
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
                    URL url = new URL(loadurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    int contentLength = conn.getContentLength();
                    if (contentLength <= 0) {
                        Log.d(TAG, "run: " + "文件长度为0");
                        return;
                    }
                    mXiazaiPb.setMax(contentLength);
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int readlength = 0; //每次读取的长度
                    int currreadlength = 0; //下载了多少长度
                    byte[] by = new byte[1024 * 8];
                    while ((readlength = inputStream.read(by)) != -1) {
                        fileOutputStream.write(by, 0, readlength);
                        currreadlength += readlength;
                        Log.d(TAG, "run: " + currreadlength * 100 / contentLength);

                        //设置进度条
                        mXiazaiPb.setProgress(currreadlength);
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

    private void initRetorUpFile() {
        RequestBody params = RequestBody.create(MediaType.parse("text/plain"), "1811A");
        String retrofilepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(retrofilepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorUpFile: " + "文件为空");
            return;
        }

        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        new Retrofit.Builder()
                .baseUrl(ApiService.retorurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .upfile(params, multipartBody)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            Log.d(TAG, "onResponse: " + result);
                            mUpFile.setText(result);
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

    private void initOkUpFile() {
        //filepash =====> file
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        //file =======> requestbody
        if (!file.exists()) {
            Log.d(TAG, "initOkUpFile: " + "文件为空");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        //requestbody ==========> mubody
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
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);

            }
        });


    }
}
