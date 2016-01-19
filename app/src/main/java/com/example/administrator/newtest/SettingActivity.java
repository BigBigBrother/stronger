package com.example.administrator.newtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;


/**
 * Created by huangan on 15/12/28.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private String TAG="SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        findViews();
        HttpReq();
    }

    private void findViews() {
        findViewById(R.id.setting_image).setOnClickListener(this);
        findViewById(R.id.setting_name).setOnClickListener(this);
        findViewById(R.id.setting_sign).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        findViewById(R.id.setting_version).setOnClickListener(this);
    }

    private void HttpReq() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams parm=new RequestParams();
//        parm.put("name","123");
//        parm.put("pwd","456");
        String url="baidu.com";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e(TAG,"onSuccess="+responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.e(TAG,"onFailure="+responseBody.toString());
                Log.e(TAG,"onFailure="+error.toString());
            }
        });

    }

    public void createDialog(Context context){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        final AlertDialog dialog = builder.create();
        View inflaterView=inflater.inflate(R.layout.layout_dialog,null);
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


        inflaterView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflaterView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_image:
                break;
            case R.id.setting_name:
                createDialog(SettingActivity.this);
                break;
            case R.id.setting_sign:
                break;
            case R.id.setting_about:
                break;
            case R.id.setting_version:
                break;
        }
    }
}
