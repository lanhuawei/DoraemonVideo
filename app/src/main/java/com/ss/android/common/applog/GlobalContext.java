package com.ss.android.common.applog;

import android.content.Context;

/**
 * Created by Ivan.L on 2018/7/5.
 * 火山和抖音使用的
 */

public class GlobalContext {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
