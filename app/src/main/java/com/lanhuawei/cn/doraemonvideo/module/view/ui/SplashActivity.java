package com.lanhuawei.cn.doraemonvideo.module.view.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lanhuawei.cn.doraemonvideo.R;

/**
 * Created by Ivan.L on 2018/6/29.
 * 启动页
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {

    }


}
