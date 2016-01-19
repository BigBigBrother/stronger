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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import MyAdapter.CommentAdapter;
import Utils.Constant;
import Utils.HttpUtils;


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
//        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        commentPop.dismiss();
//        editsendLayout.setVisibility(View.VISIBLE);
//        et.setFocusableInTouchMode(true);
//        et.requestFocus();

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

    private static final String USERIMGPATH = "http://" + Constant.IP + "/GoTravel/Resource/Image/UserImg1.jpg";

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
            // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
            mController.setPrevNextListeners(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PlayActivity.this, "下一个", Toast.LENGTH_LONG).show();
                }
            }, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(PlayActivity.this, "上一个", Toast.LENGTH_LONG).show();
                }
            });
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.play_comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            LayoutInflater inflater = LayoutInflater.from(this);
            View inputView = inflater.inflate(R.layout.comment_input, null);

            popupWindow = new PopupWindow(inputView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popupWindow.showAtLocation(PlayActivity.this.findViewById(R.id.play_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


            final EditText editText = (EditText) inputView.findViewById(R.id.comment_content_edittext);
            Button button = (Button) inputView.findViewById(R.id.comment_content_commit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commentContext = editText.getText().toString();
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String date = sDateFormat.format(new java.util.Date());

                    String url = "";
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userimg", R.mipmap.ic_launcher);
                    map.put("username", "小安子" + ":");
                    map.put("usercomment", commentContext);
                    map.put("commenttime", date);
                    map.put("zancount", commentAdapter.getItemCount() + "");
                    commentAdapter.getDataList().add(map);
                    commentAdapter.notifyDataSetChanged();
                    String[] commentKey = {"UserName", "UserComment", "UserImgPath", "CommentData"};
                    String[] commentValue = {"小安子", commentContext, USERIMGPATH, date};
                    HttpUtils.AsyncHttpClientPost(Constant.URL_POST_COMMENT, commentKey, commentValue);
                }
            });
        }
    }

}
