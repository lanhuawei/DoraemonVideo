package com.lanhuawei.cn.doraemonvideo.common.http.callback;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan.L on 2018/6/29.
 * 基础接口
 * 添加
 */

public interface BaseImpl {

    boolean addDisposable(Disposable disposable);

    Context getContext();
}
