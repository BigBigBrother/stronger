package com.example.administrator.newtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.rengwuxian.materialedittext.MaterialEditText;

import Utils.AllUtils;
import Utils.Constant;
import Utils.PreferenceUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

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
            et_uesrPwd.setText(PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_NAME, ""));
            et_userName.setText((PreferenceUtils.getString(LoginActivity.this, Constant.LOGIN_PWD, null)));
        }

        String name = et_userName.getText().toString().trim();
        String pwd = et_uesrPwd.getText().toString().trim();
        if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd))) {//对用户名密码进行校验
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
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_sign_in:
                if (vetifyLogin(et_userName, et_uesrPwd)) {
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                } else {
                    AllUtils.toast(this, "user password error!!");
                }


                break;
            case R.id.tv_forgot_pwd://find pwd
                intent.setClass(this,FindPwdActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
