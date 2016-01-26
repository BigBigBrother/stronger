package com.example.administrator.newtest;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;

import MyAdapter.CommentAdapter;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class PlayActivity extends BaseActivity {

    private CommentAdapter commentAdapter;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_play);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)//sdk版本号高于4.3
        {
            Window window = getWindow();
            //状态栏设置为透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
        initView();
    }

    private void initView() {
        VideoView videoView = (VideoView) findViewById(R.id.play_video_view);
        MediaController mController = new MediaController(this);
        File file = new File("/sdcard/111.mp4");
        if (file.exists()) {
            // 设置播放视频源的路径
            videoView.setVideoPath(file.getAbsolutePath());
            // 为VideoView指定MediaController
            videoView.setMediaController(mController);
            // 为MediaController指定控制的VideoView
            mController.setMediaPlayer(videoView);
//            // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
//            mController.setPrevNextListeners(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(PlayActivity.this, "下一个", Toast.LENGTH_LONG).show();
//                }
//            }, new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(PlayActivity.this, "上一个", Toast.LENGTH_LONG).show();
//                }
//            });
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.play_comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView textView = (TextView) findViewById(R.id.input_imageview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View inputView = inflater.inflate(R.layout.pop_input, null);

                popupWindow = new PopupWindow(inputView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                //点击popWindow外关闭popWindow
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            popupWindow.dismiss();
                        }
                        return false;
                    }
                });
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.showAtLocation(PlayActivity.this.findViewById(R.id.play_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                //popupWindow后自动显示输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

}
