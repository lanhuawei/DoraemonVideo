package com.lightsky.video.module.view.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.lightsky.video.R;
import com.lightsky.video.module.base.MainTabActivity;

/**
 * Created by Ivan.L on 2018/6/29.
 * 启动页
 */

public class SplashActivity extends Activity {
    private Handler handler = new Handler();
    private Context context;
    private MyThread myThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
    }

    /**
     * 设置数据
     */
    private void initData() {
        context = SplashActivity.this;
        myThread = new MyThread();
        handler.postDelayed(myThread, 2000);
    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(context, MainTabActivity.class));
            finish();
        }
    }



}
