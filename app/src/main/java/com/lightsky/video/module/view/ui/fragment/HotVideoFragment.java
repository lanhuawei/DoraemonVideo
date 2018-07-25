package com.lightsky.video.module.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.Util.httputil.DouyinUtil;
import com.lightsky.video.common.Util.httputil.HuoShanUtil;
import com.lightsky.video.common.customview.LoadFrameLayout;
import com.lightsky.video.common.customview.tablayout.SlidingTabLayout;
import com.lightsky.video.common.okhttp.manager.OkHttpClientManager;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.entity.databean.DouYinMainVideoListDataBean;
import com.lightsky.video.module.entity.databean.HuoShanVideoListDataBean;
import com.lightsky.video.module.view.adapter.HotVideoPagerAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by Ivan.L on 2018/7/2.
 * 热门Fragment
 */

public class HotVideoFragment extends BaseFragment {
    private static final String TAG = "--->HotVideoFragment";
    @BindView(R.id.stl_hot)
    SlidingTabLayout stlHot;
    @BindView(R.id.vp_hot_video)
    ViewPager vpHotVideo;
    @BindView(R.id.load_frameLayout)
    LoadFrameLayout loadFrameLayout;
    Unbinder unbinder;
    private String[] titles = {"精选", "新鲜", "搞笑", "娱乐"};
    private Map<String, Integer> mTabs = new HashMap<>();
    private TextView retry;
    private long max_cursor = 0;
    private boolean isLoadMore = false;
    private boolean douYinDisable = false;//是否是抖音。火山显示不同效果
    private HotVideoPagerAdapter hotVideoPagerAdapter;
    @Override
    protected int layoutResId() {
        return R.layout.fragment_hot_video;
    }

    @Override
    protected void initView() {
        retry = loadFrameLayout.findViewById(R.id.tv_retry);
    }


    @Override
    protected void initData() {

    }





    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

}
