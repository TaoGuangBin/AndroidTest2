package com.jiyun.qmdemo6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpDemoActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * POST请求
     */
    private Button mOkpostBt;
    /**
     * GET请求
     */
    private Button mOkgetBt;
    private static final String TAG = "OkHttpDemoActivity";
    private TextView mOkTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_demo);
        initView();

        //http://yun918.cn/study/public/index.php/login
        //陶广滨


    }

    private void initView() {
        mOkpostBt = (Button) findViewById(R.id.okpost_bt);
        mOkpostBt.setOnClickListener(this);
        mOkgetBt = (Button) findViewById(R.id.okget_bt);
        mOkgetBt.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okpost_bt:
                //http://yun918.cn/study/public/index.php/login
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("username", "11")
                        .add("password", "11")
                        .build();
                Request build = new Request.Builder()
                        .url("http://yun918.cn/study/public/index.php/login")
                        .post(formBody)
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
                                mOkTv.setText(result);
                            }
                        });
                    }
                });
                break;
            case R.id.okget_bt:
                //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1
                OkHttpClient okHttpClient1 = new OkHttpClient();
                Request build1 = new Request.Builder()
                        .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1")
                        .build();
                okHttpClient1.newCall(build1).enqueue(new Callback() {
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
                                mOkTv.setText(result);
                            }
                        });
                    }
                });
                break;
        }
    }
}
