package com.lightsky.video.base;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.baidu.mobstat.StatService;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.NetworkMainUtil;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.bean.EventEntity;
import com.lightsky.video.VideoHelper;
import com.lightsky.video.datamanager.category.CategoryQueryNotify;
import com.lightsky.video.sdk.CategoryInfoBase;
import com.lightsky.video.sdk.VideoOption;
import com.lightsky.video.sdk.VideoSwitcher;
import com.lightsky.video.sdk.VideoTabFragement;
import com.lightsky.video.sdk.VideoTypesLoader;
import com.lightsky.video.widget.PagerSlidingTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/10.
 * 发现的视频Fragment
 * 这个在这个包名下是因为videoTabFragement.mRoot  mViewPager 等是protected，只能在同一个包名下访问，
 */

public class DiscoveryVideoFragment extends BaseFragment implements CategoryQueryNotify {

    private VideoTypesLoader videoTypesLoader;
    private Map<String, Integer> mTabs = new HashMap<>();
    private List<CategoryInfoBase> mTabInfos = new ArrayList<>();
    List<Integer> tabFilter = new ArrayList<>();
    private VideoTabFragement videoTabFragement;
    private Handler handler = new Handler();
    ImageView tab_search;
    List<EventEntity> mEventList = new ArrayList<>();
    PagerSlidingTab pagerSlidingTab;
    private boolean isInit = false;
    int currentPos;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_discovery_video;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkMainUtil.isNetworkActive(getActivity())) {
//            loadFrameLayout.showErrorView();
            return;
        }
        initVideoType();
//        videoTypesLoader = new VideoTypesLoader();
//        videoTypesLoader.Init(this);
//        initSdk();

    }

    private void initVideoType() {
        videoTypesLoader = new VideoTypesLoader();
        videoTypesLoader.Init(this);
        initSdk();
    }


    /**
     * 初始化SDK一些参数信息
     */
    private void initSdk() {
        VideoSwitcher setting = new VideoSwitcher();
        setting.Debugmodel = false;
        setting.UseNbPlayer = true;
        setting.UseFileLog = false;
        setting.UseLogCatLog = false;
        setting.UseShareLayout = false;
        VideoOption option = new VideoOption();

//        VideoHelper.get().Init(getActivity(), setting, option);
//        videoTypesLoader.loadData();

        InitVideoHelper(setting, option);
    }

    private void InitVideoHelper(VideoSwitcher setting, VideoOption opt) {
        VideoHelper.get().Init(getActivity(), setting, opt);
        videoTypesLoader.loadData();
    }

    @Override
    protected void initView() {
//        retry = loadFrameLayout.findViewById(R.id.tv_retry);
    }

    @Override
    protected void initData() {
//        retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NetworkMainUtil.isNetworkActive(getActivity())) {
//                    if (!NoDoubleClickUtil.isDoubleClick()) {
//                        loadFrameLayout.showContentView();
//                        initVideoType();
//                    }
//                } else {
//                    loadFrameLayout.showErrorView();
//                }
//
//            }
//        });


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int result = StatusBarFontHelper.setStatusBarMode(getActivity(), true);
        StatusBarFontHelper.setLightMode(getActivity(), result);
    }

    /**
     * 查询频道列表
     * 娱乐 -> 1
     * 生活 -> 18
     * 汽车 -> 23
     * 音乐 -> 13
     * 体育 -> 12
     * 搞笑 -> 2
     * 科技 -> 7
     * 军事 -> 19
     * 影视 -> 8
     * 社会 -> 3
     * 推荐 -> 0
     *
     * * 查询频道列表
     * 娱乐 -> 1
     * 生活 -> 18
     * 汽车 -> 23
     * 音乐 -> 13
     * 体育 -> 12
     * 搞笑 -> 2
     * 科技 -> 7
     * 军事 -> 19
     * 影视 -> 8
     * 社会 -> 3
     * 推荐 -> 0
     * 萌宠 -> 111
     *
     * 健康 -> 110
     * 美食 -> 113
     * 时尚 -> 112
     * 音乐 -> 13
     * 搞笑 -> 2
     * 影视 -> 8
     * 推荐 -> 0
     * 娱乐 -> 1
     * 生活 -> 18
     * 汽车 -> 23
     * 游戏 -> 11
     * 体育 -> 12
     * 科技 -> 7
     * 军事 -> 19
     * 社会 -> 3
     * 广场舞 -> 127
     *
     * 类别查询完成
     */
    @Override
    public void onCategoryQueryFinish(boolean b, List<CategoryInfoBase> list) {
        LogUtils.e("onCategoryQueryFinish");
        LogUtils.e(isInit);
        mTabs.clear();
        mTabInfos.clear();
        for (CategoryInfoBase item : list) {
            mTabs.put(item.name, item.mId);
            mTabInfos.add(item);
        }

        if (isInit) {
            return;
        }

        tabFilter.add(mTabs.get("音乐"));
        tabFilter.add(mTabs.get("社会"));
        tabFilter.add(mTabs.get("影视"));
        tabFilter.add(mTabs.get("生活"));
        tabFilter.add(mTabs.get("科技"));
        tabFilter.add(mTabs.get("游戏"));
        tabFilter.add(mTabs.get("体育"));
        tabFilter.add(mTabs.get("军事"));
        tabFilter.add(mTabs.get("汽车"));
        LogUtils.e(tabFilter);

        VideoHelper.get().SetVideoTabFilter(tabFilter);
        videoTabFragement = new VideoTabFragement();
        if (isAdded()) {
            if (!NetworkMainUtil.isNetworkActive(getActivity())) {
//                loadFrameLayout.showErrorView();
                return;
            }
            showVideoFragment(videoTabFragement);
            generateEvent();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    pagerSlidingTab = videoTabFragement.mRoot.findViewById(R.id.tabs);
                    pagerSlidingTab.setSelectedTextColor(Color.parseColor("#4A8CD2"));
                    pagerSlidingTab.setTextColor(Color.parseColor("#5D646E"));
                    onStatistics();
                }
            });
        }

        isInit = true;

    }

    /**
     * 展示Fragment
     *
     * @param fragment
     */
    private void showVideoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_video_frame_layout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 百度统计实例
     */
    private void generateEvent() {
        EventEntity entity1 = new EventEntity("music", "音乐");
        EventEntity entity2 = new EventEntity("social", "社会");
        EventEntity entity3 = new EventEntity("film", "影视");
        EventEntity entity4 = new EventEntity("life", "生活");
        EventEntity entity5 = new EventEntity("technology", "科技");
        EventEntity entity6 = new EventEntity("game", "游戏");
        EventEntity entity7 = new EventEntity("sports", "体育");
        EventEntity entity8 = new EventEntity("military", "军事");
        EventEntity entity9 = new EventEntity("car", "汽车");

        mEventList.add(entity1);
        mEventList.add(entity2);
        mEventList.add(entity3);
        mEventList.add(entity4);
        mEventList.add(entity5);
        mEventList.add(entity6);
        mEventList.add(entity7);
        mEventList.add(entity8);
        mEventList.add(entity9);
    }

    /**
     * 滑动页面的百度统计
     * 和搜索点击
     */
    private void onStatistics() {
        videoTabFragement.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                try {
                    StatService.onEvent(getActivity(), mEventList.get(position).getEventId(), mEventList.get(position).getEventName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tab_search = videoTabFragement.mRoot.findViewById(R.id.tab_search);
        tab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(getActivity(), "search", "搜索");
                videoTabFragement.onClick(view);
            }
        });
    }


    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
//        StatService.onEvent(getActivity(), "discover", "发现");
        StatService.onEvent(getActivity(), "music", "音乐");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VideoHelper.get().unInit();
        isInit = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (videoTabFragement != null && videoTabFragement.mViewPager != null) {
                currentPos = videoTabFragement.mViewPager.getCurrentItem();
                if (currentPos == 0) {
                    videoTabFragement.mViewPager.setCurrentItem(1);
                } else {
                    videoTabFragement.mViewPager.setCurrentItem(0);
                }
            }
        } else {
            if (videoTabFragement != null && videoTabFragement.mViewPager != null) {
                videoTabFragement.mViewPager.setCurrentItem(currentPos, false);
            }
        }
    }
}
