package com.lanhuawei.cn.doraemonvideo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lanhuawei.cn.doraemonvideo.common.Util.CommonUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.KindsOfUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.SpUtil;
import com.ss.android.common.applog.GlobalContext;
import com.ss.android.common.applog.UserInfo;

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

    public static String IMEI;
    public static String PACKAGE_NAME;
    public static String VERSION_NAME;
    public static String CHANNEL_ID;
    public static String ANDROID_ID;
    public static String SERIAL_NO;


    @Override
    public void onCreate() {
        super.onCreate();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();


        SpUtil.init(this);

        //缓存起来防止每次网络请求都去拿
        PACKAGE_NAME = CommonUtil.getProcessName();
        VERSION_NAME = KindsOfUtil.getHasDotVersion(MyApplication.getInstance());
        CHANNEL_ID = CommonUtil.getMetaData(MyApplication.getInstance(), "BaiduMobAd_CHANNEL");
        IMEI = KindsOfUtil.getDeviceIMEI(MyApplication.getInstance());
        ANDROID_ID = KindsOfUtil.getDeviceAndroidId(MyApplication.getInstance());
        SERIAL_NO = KindsOfUtil.getSerialNo();

//        Fresco初始化
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)   // 对图片进行自动缩放
                .setResizeAndRotateEnabledForNetwork(true)    // 对图片进行自动缩放
                .setBitmapsConfig(Bitmap.Config.RGB_565) //  //图片设置RGB_565，减小内存开销  fresco默认情况下是RGB_8888
                //other settings
                .build();
        Fresco.initialize(this, config);

        StatService.setDebugOn(BuildConfig.DEBUG);
        GlobalContext.setContext(getApplicationContext()); //Hook 抖音//要在UserInfo配置之前
//        native
//        出现 java.lang.UnsatisfiedLinkError: Native method not found ，其中是一个是so没有load
        try {
            System.loadLibrary("userinfo");//抖音&火山
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserInfo.setAppId(2);
        UserInfo.initUser("a3668f0afac72ca3f6c1697d29e0e1bb1fef4ab0285319b95ac39fa42c38d05f");

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //内存低的时候，清理Glide的缓存
        Glide.get(this).clearMemory();
    }
}
