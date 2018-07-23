package com.lightsky.video.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mobstat.StatService;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.http.callback.BaseImpl;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Ivan.L on 2018/6/29.
 * 基础activity
 */

public abstract class BaseActivity<PRESENTER extends BasePresenter> extends AppCompatActivity implements BaseImpl {
    private CompositeDisposable compositeDisposable;
    protected PRESENTER mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == compositeDisposable) {
            compositeDisposable = new CompositeDisposable();
        }
        if (null == mPresenter) {
            mPresenter = createPresenter();
        }
        setContentView(layoutResId());
        StatusBarCompat.translucentStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
//        ToastUtil.showToast("" + getClass().getSimpleName());
    }

    protected PRESENTER createPresenter() {
        return null;
    }

    protected abstract int layoutResId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public boolean addDisposable(Disposable disposable) {
        if (null != compositeDisposable) {
            compositeDisposable.add(disposable);
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != compositeDisposable) {
            compositeDisposable.clear();
        }
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        if (null != unbinder) {
            unbinder.unbind();//解除绑定，官方文档只对fragment做了解绑
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPageStart(this, getClass().getName());
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPageEnd(this, getClass().getName());
        StatService.onPause(this);
    }
}
