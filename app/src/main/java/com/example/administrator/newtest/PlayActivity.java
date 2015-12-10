package com.example.administrator.newtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PlayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);
    }
}
