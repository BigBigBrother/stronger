package com.example.administrator.newtest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;


/**
 * Created by huangan on 15/12/28.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    private String TAG = "SettingActivity";
    private TextView tv_name, tv_sign, tv_about, tv_version;
    private ImageView iv_head;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            findViewById(R.id.main_no_title).setVisibility(View.VISIBLE); //显示透明遮罩层
        }
        findViews();
        HttpReq();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        iv_head = (ImageView) findViewById(R.id.setting_image);
        iv_head.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.setting_name);
        tv_name.setOnClickListener(this);
        tv_sign = (TextView) findViewById(R.id.setting_sign);
        tv_sign.setOnClickListener(this);
        tv_about = (TextView) findViewById(R.id.setting_about);
        tv_about.setOnClickListener(this);
        tv_version = (TextView) findViewById(R.id.setting_version);
        tv_version.setOnClickListener(this);
    }

    private void HttpReq() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams parm = new RequestParams();
//        parm.put("name","123");
//        parm.put("pwd","456");
        String url = "baidu.com";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e(TAG, "onSuccess=" + responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.e(TAG,"onFailure="+responseBody.toString());
                Log.e(TAG, "onFailure=" + error.toString());
            }
        });

    }

    public void createDialog(Context context, final TextView textView, String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final AlertDialog dialog = builder.create();
        View inflaterView = inflater.inflate(R.layout.layout_dialog, null);
        dialog.setView(inflaterView);
        dialog.show();
        /*
         * 获取弹框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        lp.x = 100; // 新位置X坐标
//        lp.y = 100; // 新位置Y坐标
        lp.width = 900; // 宽度
        lp.height = 530; // 高度
        lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        TextView tv_dialog_title = (TextView) inflaterView.findViewById(R.id.dialog_title);
        if (title.equals("name")) {
            tv_dialog_title.setText("昵称");
        } else if (title.equals("sign")) {
            tv_dialog_title.setText("个性签名");
        } else if (title.equals("about")) {
            tv_dialog_title.setText("关于我们");
        } else if (title.equals("version")) {
            tv_dialog_title.setText("版本信息");
        }
        final EditText et_dialog_content = (EditText) inflaterView.findViewById(R.id.dialog_content);


        inflaterView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflaterView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(et_dialog_content.getText().toString().trim());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_image://跳转到系统相册   选择图片上传
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.setting_name:
                createDialog(SettingActivity.this, tv_name, "name");
                break;
            case R.id.setting_sign:
                createDialog(SettingActivity.this, tv_sign, "sign");
                break;
            case R.id.setting_about:
                createDialog(SettingActivity.this, tv_about, "about");
                break;
            case R.id.setting_version:
                createDialog(SettingActivity.this, tv_version, "version");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //ImageView imageView = (ImageView) findViewById(R.id.imgView);
            iv_head.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }
}
