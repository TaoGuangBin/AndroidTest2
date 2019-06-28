package com.jiyun.qmdemo7.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiyun.qmdemo7.ApiService;
import com.jiyun.qmdemo7.R;

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
public class FileFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * OK上传文件
     */
    private Button mOkupfile;
    /**
     * Retrofit上传文件
     */
    private Button mRetrofitupfile;
    /**
     * 单线程下载
     */
    private Button mThreaddownfile;
    /**
     * OK下载文件
     */

    private Button mOkdownfile;
    /**
     * 我的广播事件
     */
    private static final String TAG = "FileFragment";
    private Button mMyLb;
    private ProgressBar mFilePb;
    private TextView mFileTv;

    public FileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mOkupfile = (Button) view.findViewById(R.id.okupfile);
        mOkupfile.setOnClickListener(this);
        mRetrofitupfile = (Button) view.findViewById(R.id.retrofitupfile);
        mRetrofitupfile.setOnClickListener(this);
        mThreaddownfile = (Button) view.findViewById(R.id.threaddownfile);
        mThreaddownfile.setOnClickListener(this);
        mOkdownfile = (Button) view.findViewById(R.id.okdownfile);
        mOkdownfile.setOnClickListener(this);
        mMyLb = (Button) view.findViewById(R.id.my_lb);
        mMyLb.setOnClickListener(this);
        mFilePb = (ProgressBar) view.findViewById(R.id.file_pb);
        mFileTv = (TextView) view.findViewById(R.id.file_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okupfile:
                initOKup();
                break;
            case R.id.retrofitupfile:
                initRetorup();
                break;
            case R.id.threaddownfile:
                initThreadDown();
                break;
            case R.id.okdownfile:
                initOkDown();
                break;
            case R.id.my_lb:
                break;
        }
    }

    private void initOkDown() {
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
                mFilePb.setMax(contentLength);
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
                            mFilePb.setProgress(finalAccusslength);
                            mFileTv.setText(finalAccusslength * 100 / contentLength + "");
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
                    URL url = new URL(fileurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    final int contentLength = conn.getContentLength();
                    mFilePb.setMax(contentLength);
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
                                mFilePb.setProgress(finalAccusslength);
                                mFileTv.setText(finalAccusslength * 100 / contentLength + "");
                            }
                        });

                    }


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
            Log.d(TAG, "onClick: " + "文件为空");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "1811a");
        RequestBody imagerequestBody = requestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), imagerequestBody);
        new Retrofit.Builder()
                .baseUrl(ApiService.fileupurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .fileup(requestBody, part)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            Log.d(TAG, "onResponse: " + result);
                            mFileTv.setText(result);
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

    private void initOKup() {
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "sddc.jpg";
        File file = new File(filepath);
        if (!file.exists()) {
            Log.d(TAG, "onClick: " + "文件为空");
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFileTv.setText(result);
                    }
                });
            }
        });
    }


}
