package com.lanhuawei.cn.doraemonvideo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Ivan.L on 2018/6/29.
 *
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    /**
     * 主线程ID
     */
    public static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    public static Thread mMainThread;
    /**
     * 主线程Handler
     */
    public static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    public static Looper mMainLooper;

    /**
     * 获取主线程ID
     */
    public static int getmMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getmMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getmMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getmMainLooper() {
        return mMainLooper;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public MyApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
    }
}
