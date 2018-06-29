package com.lanhuawei.cn.doraemonvideo.base;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Ivan.L on 2018/6/29.
 *
 */

public class BasePresenter<VIEW> {
    private WeakReference<VIEW> mViews;

    protected void attachView(VIEW view) {
        mViews = new WeakReference<VIEW>(view);
    }

    protected VIEW getView() {
        return isViewAttached() ? mViews.get() : null;
    }

    private boolean isViewAttached() {
        return null != mViews && null != mViews.get();
    }

    protected void detachView() {
        if (null != mViews) {
            mViews.clear();
            mViews = null;
        }
    }
}
