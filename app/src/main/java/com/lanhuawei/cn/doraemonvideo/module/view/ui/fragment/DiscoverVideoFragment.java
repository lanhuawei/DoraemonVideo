package com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.lanhuawei.cn.doraemonvideo.R;
import com.lanhuawei.cn.doraemonvideo.common.customview.LoadFrameLayout;
import com.lanhuawei.cn.doraemonvideo.module.base.BaseFragment;
import com.lanhuawei.cn.doraemonvideo.module.bean.EventEntity;
import com.lightsky.video.datamanager.category.CategoryQueryNotify;
import com.lightsky.video.sdk.CategoryInfoBase;
import com.lightsky.video.sdk.VideoTabFragement;
import com.lightsky.video.sdk.VideoTypesLoader;
import com.lightsky.video.widget.PagerSlidingTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/2.
 * 发现的视频Fragment
 */

public class DiscoverVideoFragment extends BaseFragment implements CategoryQueryNotify {
    Unbinder unbinder;
    @BindView(R.id.load_frameLayout) LoadFrameLayout loadFrameLayout;
    private VideoTypesLoader videoTypesLoader;
    private Map<String, Integer> mTabs = new HashMap<>();
    private List<CategoryInfoBase> mTabInfos = new ArrayList<>();
    private List<Integer> tabFilter = new ArrayList<>();
    private VideoTabFragement videoTabFragement;
    private Handler handler = new Handler();
    private ImageView tab_search;
    private List<EventEntity> mEvenList = new ArrayList<>();
    private PagerSlidingTab pagerSlidingTab;
    private boolean isInit = false;
    private int currentPos;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_discover_video;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCategoryQueryFinish(boolean b, List<CategoryInfoBase> list) {

    }

    @Override
    protected void onViewReallyCreated(View view) {
//        super.onViewReallyCreated(view);
        unbinder = ButterKnife.bind(this, view);
        StatService.onEvent(getActivity(), "discover", "发现");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
