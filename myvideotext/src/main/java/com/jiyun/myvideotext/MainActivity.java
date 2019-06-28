package com.jiyun.myvideotext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 系统自带
     */
    private Button mSysVideo;
    /**
     * video
     */
    private Button mVideoClick;
    private VideoView mVideoView;
    /**
     * surfaceview
     */
    private Button mSurfaceClick;
    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mSysVideo = (Button) findViewById(R.id.sys_video);
        mSysVideo.setOnClickListener(this);
        mVideoClick = (Button) findViewById(R.id.video_click);
        mVideoClick.setOnClickListener(this);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mSurfaceClick = (Button) findViewById(R.id.surface_click);
        mSurfaceClick.setOnClickListener(this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.sys_video:
                String Videopath = Environment.getExternalStorageDirectory()+ File.separator+"Pictures"+File.separator+"qczj.mp4";
                //Videopath ===> uri
                Uri parse = Uri.parse(Videopath);
                //uri=====>intent 隐式启动
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //设置类型
                intent.setDataAndType(parse,"video/mp4");
                startActivity(intent);
                
                break;
            case R.id.video_click:
                Videopath = Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"qczj.mp4";
                parse = Uri.parse(Videopath);
                mVideoView.setVideoURI(parse);
                mVideoView.start();
                break;
            case R.id.surface_click:
                break;
        }
    }
}
