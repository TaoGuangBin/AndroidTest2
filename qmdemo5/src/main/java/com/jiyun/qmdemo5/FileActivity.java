package com.jiyun.qmdemo5;

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

public class FileActivity extends AppCompatActivity implements View.OnClickListener {

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
    private static final String TAG = "FileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
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
                initUPOK();
                break;
            case R.id.retorup_bt:
                initRetorUp();
                break;
            case R.id.threaddown_bt:
                initThreadDown();
                break;
            case R.id.okdown_bt:
                initOKDown();
                break;
            case R.id.mainuplb_bt:
                break;
        }
    }

    private void initOKDown() {
        String DownFileUrl = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
        String FileName = DownFileUrl.substring(DownFileUrl.lastIndexOf("/") + 1, DownFileUrl.length());
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
                .url(DownFileUrl)
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
                mMaindownPb.setMax(contentLength);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int readlength = 0;
                int aucceelength = 0;
                byte[] bytes = new byte[1024 * 8];
                while ((readlength = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, readlength);
                    aucceelength += readlength;
                    final int finalAucceelength = aucceelength;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMaindownPb.setProgress(finalAucceelength);
                            mUpfileTv.setText(finalAucceelength * 100 / contentLength+"");
                        }
                    });
                }
                fileOutputStream.close();
                inputStream.close();
            }
        });


    }

    private void initThreadDown() {
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
                    URL url = new URL("http://yun918.cn/study/public/res/UnknowApp-1.0.apk");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    final int contentLength = conn.getContentLength();
                    if (contentLength <= 0) {
                        Log.d(TAG, "initThreadDown: " + "长度为0");
                    }
                    mMaindownPb.setMax(contentLength);
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int readlength = 0;
                    int aucceelength = 0;
                    byte[] bytes = new byte[1024 * 8];
                    while ((readlength = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, readlength);
                        aucceelength += readlength;
                        final int finalAucceelength = aucceelength;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mUpfileTv.setText(finalAucceelength * 100 / contentLength + "");
                                mMaindownPb.setProgress(finalAucceelength);
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

    private void initRetorUp() {
        String Filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(Filepath);
        if (!file.exists()) {
            Log.d(TAG, "initRetorUp: " + "文件不存在");
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "key");
        RequestBody mediaType = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), mediaType);
        new Retrofit.Builder()
                .baseUrl(ApiService.FileUp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getFileUp(requestBody, multipartBody)
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

    private void initUPOK() {
        String FilePath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(FilePath);
        if (!file.exists()) {
            Log.d(TAG, "initUPOK: " + "文件为空");
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
