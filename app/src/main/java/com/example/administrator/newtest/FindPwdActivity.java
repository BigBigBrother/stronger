package com.example.administrator.newtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Utils.AllUtils;
import Utils.Constant;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by huangan on 15/12/24.
 */
public class FindPwdActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "FindPwdActivity" ;


    private EditText mPhone,mNumber;
    private Button mVertify,mCommit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        SMSSDK.initSDK(this, Constant.MOB_SMS_SDK_APP_KEY, Constant.MOB_SMS_SDK_APP_SECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调
        findViews();
    }


    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Log.e(TAG,"event:"+event+"    result:"+result+"    data:"+data.toString());
            switch (event) {
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //AllUtils.toast(FindPwdActivity.this,"验证成功");
                        Log.e(TAG,"验证成功");
                        //toast("验证成功");
                        Intent intent=new Intent(FindPwdActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //AllUtils.toast(FindPwdActivity.this,"验证失败");
                        Log.e(TAG,"验证失败");
                    }
                    break;
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //AllUtils.toast(FindPwdActivity.this,"获取验证码成功");
                        Log.e(TAG,"获取验证码成功");
                        //默认的智能验证是开启的,我已经在后台关闭
                    } else {
                        //AllUtils.toast(FindPwdActivity.this,"获取验证码失败");
                        Log.e(TAG,"获取验证码失败");
                    }
                    break;
            }
        }
    };


    private void findViews() {
        mPhone= (EditText) findViewById(R.id.find_pwd_et_phone);
        mVertify= (Button) findViewById(R.id.find_pwd_bt_vertify);
        mVertify.setOnClickListener(this);
        mNumber= (EditText) findViewById(R.id.find_pwd_et_number);
        mCommit= (Button) findViewById(R.id.find_pwd_bt_commit);
        mCommit.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.find_pwd_bt_vertify:
                SMSSDK.getVerificationCode("86", mPhone.getText().toString().trim());//获取验证码  对应回调里的  EVENT_GET_VERIFICATION_CODE
                break;
            case R.id.find_pwd_bt_commit:
                SMSSDK.submitVerificationCode("86", mPhone.getText().toString().trim(), mNumber  //校验验证码  对应回调里的 RESULT_COMPLETE
                        .getText().toString());
                break;
        }
    }
}
