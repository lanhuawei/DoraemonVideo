package com.lightsky.video;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lightsky.video.common.Util.CommonUtil;
import com.lightsky.video.common.Util.KindsOfUtil;
import com.lightsky.video.common.Util.SpUtil;
import com.lightsky.video.common.Util.hookpms.ServiceManagerWraper;
import com.lightsky.video.module.base.MainTabActivity;
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
//        ServiceManagerWraper.hookPMS(this.getApplicationContext());
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        SpUtil.init(this);

        //缓存起来防止每次网络请求都去拿
        PACKAGE_NAME = CommonUtil.getProcessName();
        VERSION_NAME = KindsOfUtil.getHasDotVersion(MyApplication.getInstance());
//        CHANNEL_ID = CommonUtil.getMetaData(MyApplication.getInstance(), "BaiduMobAd_CHANNEL");
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

        LogUtils.getLogConfig()
                .configAllowLog(BuildConfig.DEBUG)
                .configTagPrefix(this.getPackageName())
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                .configLevel(LogLevel.TYPE_VERBOSE);

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
//        initCrashHandler();

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

    /**
     * 实例化全局异常捕获类
     */
    private void initCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    /**
     * 创建服务用于捕获崩溃异常
     */
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            //发生崩溃异常时,重启应用
            restartApp();
        }
    };

    /**
     * 重启App
     */
    public void restartApp() {
        Intent intent = new Intent(this, MainTabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
