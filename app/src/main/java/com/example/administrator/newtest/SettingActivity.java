package com.example.administrator.newtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;


/**
 * Created by huangan on 15/12/28.
 */
public class SettingActivity extends BaseActivity{

    private String TAG="SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        HttpReq();
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
}
