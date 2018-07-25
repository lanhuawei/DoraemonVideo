package com.lightsky.video.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;

import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/2.
 * fagment Base
 */

public abstract class BaseFragment<PRESENTER extends BasePresenter> extends Fragment {
    private static final String TAG = "BaseFragment";

    public BaseActivity context;
    protected Unbinder unbinder;
    protected abstract int layoutResId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewReallyCreated(View view);

    private View rootView;

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
    public void onPause() {
        super.onPause();
        // 页面埋点
        StatService.onPageStart(getActivity(), getActivity().getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // 页面埋点
        StatService.onPageEnd(getActivity(), getActivity().getClass().getName());
    }


    @Override
    public void onDestroyView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }

        super.onDestroyView();
    }
}
