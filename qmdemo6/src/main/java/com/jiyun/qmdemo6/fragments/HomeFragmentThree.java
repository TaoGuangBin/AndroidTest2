package com.jiyun.qmdemo6.fragments;


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

import com.jiyun.qmdemo6.ApiService;
import com.jiyun.qmdemo6.MyNotifiActivity;
import com.jiyun.qmdemo6.R;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentThree extends Fragment implements View.OnClickListener {

    private static final String TAG = "HomeFragmentThree";
    private View view;
    /**
     * Ok上传
     */
    private Button mOkupFile;
    /**
     * Retorfit上传
     */
    private Button mRetorupFile;
    /**
     * 单线程下载
     */
    private Button mThreaddownFile;
    /**
     * Ok下载
     */
    private Button mOkdownFile;
    /**
     * 我的广播事件
     */
    private Button mMyLbbt;
    private ProgressBar mMyProbar;
    private TextView mFthreeTv;
    private MyBroadcast myBroadcast;

    public HomeFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home_fragment_three, null);
        initView(view);
        initLocalBroadcast();
        return view;
    }

    private void initLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MYMSG");
        myBroadcast = new MyBroadcast();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadcast,intentFilter);

    }
    class MyBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Intent intent1 = new Intent(getActivity(), MyNotifiActivity.class);
            intent1.putExtra("newmsg",msg);
            PendingIntent activity = PendingIntent.getActivity(getActivity(), 100, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            Notification build = new NotificationCompat.Builder(getActivity(),"1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentTitle("广播事件")
                    .setContentText("有个广播事件")
                    .setContentIntent(activity)
                    .build();
            nm.notify(1,build);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadcast);

    }

    private void initView(View view) {
        mOkupFile = (Button) view.findViewById(R.id.okup_file);
        mOkupFile.setOnClickListener(this);
        mRetorupFile = (Button) view.findViewById(R.id.retorup_file);
        mRetorupFile.setOnClickListener(this);
        mThreaddownFile = (Button) view.findViewById(R.id.threaddown_file);
        mThreaddownFile.setOnClickListener(this);
        mOkdownFile = (Button) view.findViewById(R.id.okdown_file);
        mOkdownFile.setOnClickListener(this);
        mMyLbbt = (Button) view.findViewById(R.id.my_lbbt);
        mMyLbbt.setOnClickListener(this);
        mMyProbar = (ProgressBar) view.findViewById(R.id.my_probar);
        mFthreeTv = (TextView) view.findViewById(R.id.fthree_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okup_file:
                initOkup();
                break;
            case R.id.retorup_file:
                initRetorfitUp();
                break;
            case R.id.threaddown_file:
                initThreadDown();
                break;
            case R.id.okdown_file:
                initOkDown();
                break;
            case R.id.my_lbbt:
                sendmsg();
                break;
        }
    }

    private void sendmsg() {
        Intent intent = new Intent();
        intent.putExtra("msg","我的广播事件");
        intent.setAction("MYMSG");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void initOkDown() {
        //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
        String fileurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
        String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1, fileurl.length());
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
                .url(fileurl)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                final int contentLength = (int) response.body().contentLength();
                mMyProbar.setMax(contentLength);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int readlength = 0;
                int accusslength = 0;
                byte[] bytes = new byte[1024 * 8];
                while ((readlength = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, readlength);
                    accusslength += readlength;
                    final int finalAccusslength = accusslength;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMyProbar.setProgress(finalAccusslength);
                            mFthreeTv.setText(finalAccusslength * 100 / contentLength + "");
                        }
                    });
                }
                inputStream.close();
                fileOutputStream.close();
            }
        });


    }

    private void initThreadDown() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //http://yun918.cn/study/public/res/UnknowApp-1.0.apk
                String fileurl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
                String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1, fileurl.length());
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
                    URL url = new URL("http://yun918.cn/study/public/res/UnknowApp-1.0.apk");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    final int contentLength = conn.getContentLength();
                    mMyProbar.setMax(contentLength);
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int readlength = 0;
                    int accesslegth = 0;
                    byte[] bytes = new byte[1024 * 8];
                    while ((readlength = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, readlength);
                        accesslegth += readlength;
                        final int finalAccesslegth = accesslegth;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMyProbar.setProgress(finalAccesslegth);
                                mFthreeTv.setText(finalAccesslegth * 100 / contentLength + "");
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

    private void initRetorfitUp() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorfitUp: 文件不存在");
        }
        RequestBody request = RequestBody.create(MediaType.parse("text/plain"), "1811A");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        new Retrofit.Builder()
                .baseUrl(ApiService.fileupurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .fileup(request, multipartBody)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String requset = response.body().string();
                            mFthreeTv.setText(requset);

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
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "initOkup: " + "文件不存在");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1811A")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build1 = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(build)
                .build();
        okHttpClient.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String requset = response.body().string();
                Log.d(TAG, "onResponse: " + requset);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFthreeTv.setText(requset);
                    }
                });
            }
        });
    }
}
