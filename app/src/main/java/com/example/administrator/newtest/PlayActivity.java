package com.example.administrator.newtest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import MyAdapter.CommentAdapter;


public class PlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)//sdk版本号高于4.3
        {
            Window window=getWindow();
            //状态栏设置为透明
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.play_toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Play");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VideoView videoView = (VideoView) findViewById(R.id.play_video_view);
        MediaController mController=new MediaController(this);
        File file=new File("/sdcard/111.mp4");
        if(file.exists()){
            // 设置播放视频源的路径
            videoView.setVideoPath(file.getAbsolutePath());
            // 为VideoView指定MediaController
            videoView.setMediaController(mController);
            // 为MediaController指定控制的VideoView
            mController.setMediaPlayer(videoView);
            // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
            mController.setPrevNextListeners(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(PlayActivity.this, "下一个",Toast.LENGTH_LONG).show();
                }
            }, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(PlayActivity.this, "上一个",Toast.LENGTH_LONG).show();
                }
            });
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.play_comment_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentAdapter(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
