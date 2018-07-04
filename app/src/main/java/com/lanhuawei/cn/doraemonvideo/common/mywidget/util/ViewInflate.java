package com.lanhuawei.cn.doraemonvideo.common.mywidget.util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ivan.L on 2018/7/3.
 *
 */

public final class ViewInflate {
    private static final String TAG = "ViewInflate";

    private ViewInflate() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static <VIEW extends View> VIEW fromView(View view) {
        try {
            return (VIEW) view;
        } catch (Exception e) {
            Log.e(TAG, "fromView: " + String.valueOf(e.getMessage()));
            return null;
        }
    }

    public static <VIEW extends View> VIEW inflateLayout(Context context, @LayoutRes int layoutRes, @Nullable ViewGroup root, boolean attachToRoot) {
        try {
            View view = LayoutInflater.from(context).inflate(layoutRes, root, attachToRoot);
            return fromView(view);
        } catch (Exception e) {
            Log.e(TAG, "inflateLayout: " + String.valueOf(e.getMessage()));
            return null;
        }
    }

    public static <VIEW extends View> VIEW inflateLayout(Context context, @LayoutRes int layoutRes, @Nullable ViewGroup root) {
        try {
            View view = LayoutInflater.from(context).inflate(layoutRes, root);
            return fromView(view);
        } catch (Exception e) {
            Log.e(TAG, "inflateLayout: " + String.valueOf(e.getMessage()));
            return null;
        }
    }

    public static <VIEW extends View> VIEW inflateLayout(Context context, @LayoutRes int layoutRes) {
        try {
            View view = LayoutInflater.from(context).inflate(layoutRes, null);
            return fromView(view);
        } catch (Exception e) {
            Log.e(TAG, "inflateLayout: " + String.valueOf(e.getMessage()));
            return null;
        }
    }

    public static <VIEW extends View> VIEW findView(View view, @IdRes int id) {
        try {
            View child = view.findViewById(id);
            return fromView(child);
        } catch (Exception e) {
            Log.e(TAG, "findView: " + String.valueOf(e.getMessage()));
            return null;
        }
    }
}
