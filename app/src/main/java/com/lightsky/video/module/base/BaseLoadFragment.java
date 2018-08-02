package com.lightsky.video.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isViewInit = true;
        // 防止一开始加载的时候未 调用 preLoadData 方法， 因为setUserVisibleHint 比 onActivityCreated 触发 前
        if (getUserVisibleHint()) {
            loadData(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (null == rootView) {
            rootView = inflater.inflate(layoutResId(), null);
            onViewReallyCreated(rootView);
            initView();
            initData();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        loadData(false);
    }

    /**
     * 可见或者不可见
     * @param forceLoad
     */
    public void loadData(boolean forceLoad) {
        if (isViewInit && isVisible && (!isLoadData || forceLoad)) {
            lazyLoad();
            isLoadData = true;
        } else {
            fragmentInvisible();
//            isLoadData = false;
        }
    }

    /**
     * 延迟加载
     * 子类必须重写此方法,这个方法只会调用一次,相当于activity的onCreate
     * 数据没有加载时候
     */
    protected abstract void lazyLoad();

    /**
     * Fragment不可见时候调用
     * 可调用可不调用
     */
    protected void fragmentInvisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
