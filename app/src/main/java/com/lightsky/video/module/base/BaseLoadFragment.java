package com.lightsky.video.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/25.
 * fragment预加载,页面可见才开始加载数据。不一开始占用太多内存
 */
public abstract class BaseLoadFragment extends Fragment {
    private BaseActivity context;
    protected Unbinder unbinder;
    protected abstract int layoutResId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewReallyCreated(View view);

    private View rootView;
    // 当前Fragment 是否可见
    protected boolean isVisible = false;
    // 是否加载过数据
    protected boolean isLoadData = false;
    protected boolean isViewInit = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
    }

}
