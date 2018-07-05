package com.ss.android.common.applog;

/**
 * Created by Ivan.L on 2018/7/4.
 * 抖音，火山so对应的native类
 */

public class UserInfo {
    public static native void getPackage(String str);

    public static native String getUserInfo(int i, String str, String[] strArr);

    public static native int initUser(String str);

    public static native void setAppId(int i);
}
