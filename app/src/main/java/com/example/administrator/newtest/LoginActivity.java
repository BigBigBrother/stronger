package com.example.administrator.newtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import Utils.AllUtils;
import Utils.Constant;
import Utils.PreferenceUtils;

/**
 * Created by Administrator on 2015/11/24.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private MaterialEditText et_userName, et_uesrPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        findViews();
    }

    private void findViews() {
        et_userName = (MaterialEditText) findViewById(R.id.et_login_user_name);
        et_uesrPwd = (MaterialEditText) findViewById(R.id.et_login_user_pwd);
        findViewById(R.id.tv_sign_in).setOnClickListener(this);
        findViewById(R.id.tv_forgot_pwd).setOnClickListener(this);

        if (PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_NAME, "") != null &&
                (PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_PWD, "") != null)) {
            et_userName.setText(PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_NAME, ""));
            et_uesrPwd.setText((PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_PWD, null)));
        }

        String name = et_userName.getText().toString().trim();
        String pwd = et_uesrPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {//对用户名密码进行校验
            PreferenceUtils.putString(this, Constant.LOGIN_NAME, name);//保存用户名密码
            PreferenceUtils.putString(this, Constant.LOGIN_PWD, pwd);//保存用户名密码
        }
    }

    private boolean vetifyLogin(MaterialEditText et_userName, MaterialEditText et_uesrPwd) {
        String name = et_userName.getText().toString().trim();
        String pwd = et_uesrPwd.getText().toString().trim();
        if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd))) {//对用户名密码进行校验
            PreferenceUtils.putString(this, Constant.LOGIN_NAME, name);//保存用户名密码
            PreferenceUtils.putString(this, Constant.LOGIN_PWD, pwd);//保存用户名密码
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_sign_in:
//                if (vetifyLogin(et_userName, et_uesrPwd)) {
//                    intent.setClass(this, MainActivity.class);
//                    startActivity(intent);
                Log.w("ZH-DEBUG", "URL = " + URL);
                new httpRequest().execute(URL);
//                this.finish();
//                } else {
//                    AllUtils.toast(this, "user password error!!");
//                }
                break;
            case R.id.tv_forgot_pwd://find pwd
                Intent intent = new Intent();
                intent.setClass(this, FindPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    private static final String IP = "192.168.31.220";
    private static final String URL = "http://" + IP + "/login.php";

    // 通过url请求获取JsonString
    private String getJsonString(String requestUrl) {
        StringBuffer strBuf;
        strBuf = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(15000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    AllUtils.toast(LoginActivity.this, "用户名或密码错误！");
                    break;
                case 2:
                    AllUtils.toast(LoginActivity.this, "用户名或密码为空!");
                    break;
            }
        }
    };

    private class httpRequest extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... url) {
            String jsonStr = getJsonString(url[0]);
            return parseJson(jsonStr);
        }

        @Override
        protected void onPostExecute(Boolean isTrue) {
            super.onPostExecute(isTrue);
            Log.w("ZH-DEBUG", "isTrue = " + isTrue);
            if (isTrue) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        }
    }

    private boolean parseJson(String jsonStr) {
        String name = et_userName.getText().toString().trim();
        String pwd = et_uesrPwd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {//对用户名密码进行校验
            Message message = mHandler.obtainMessage();
            message.what = 2;
            mHandler.sendMessage(message);
            return false;
        }

        String pwdMD5 = null;
        try {
            pwdMD5 = AllUtils.getMD5(pwd);
            Log.w("ZH-DEBUG", "pwdMD5 = " + pwdMD5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String UID = jsonObject.getString("UID");
                String userName = jsonObject.getString("UserName");
                String password = jsonObject.getString("Password");

                if (name.equals(userName) && pwdMD5.equals(password)) {
                    Log.w("ZH-DEBUG", "UID = " + UID);
                    Log.w("ZH-DEBUG", "userName = " + userName);
                    Log.w("ZH-DEBUG", "password = " + password);

                    PreferenceUtils.putString(this, Constant.LOGIN_NAME, name);//保存用户名密码
                    PreferenceUtils.putString(this, Constant.LOGIN_PWD, pwd);//保存用户名密码
                    return true;
                } else if (i == jsonArray.length() - 1){
                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
