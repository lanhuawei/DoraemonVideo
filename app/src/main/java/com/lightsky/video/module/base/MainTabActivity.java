package com.lightsky.video.module.base;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.lightsky.video.R;
import com.lightsky.video.base.DiscoveryVideoFragment;
import com.lightsky.video.base.FollowingVideoFragment;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.NoDoubleClickUtil;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.manager.TabManager;
import com.lightsky.video.common.videoplayer.player.VideoViewManager;
import com.lightsky.video.module.model.event.DiscoveryClickToRefreshEvent;
import com.lightsky.video.module.model.event.DoubleClickToRefreshEvent;
import com.lightsky.video.module.view.ui.fragment.DouYinVideoFragment;
import com.lightsky.video.module.view.ui.fragment.HotVideoFragment;
import com.lightsky.video.module.view.ui.fragment.MineCenterFragment;
import com.lightsky.video.VideoHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Ivan.L on 2018/6/29.
 * PS:activity_main.xml 跟360快视频SDK冲突
 */

public class MainTabActivity extends BaseActivity{
    private TabHost mTabHost;
    private TabManager tabManager;
    private LayoutInflater layoutInflater;
    private Context context;
    private static final String TAG = "--->MainTabActivity";

    private final String TAB1 = "DOUYIN";
    private final String TAB2 = "DISCOVER";
    private final String TAB3 = "HOT";
    private final String TAB4 = "MINE";

    private long mExitTime;
    private Handler handler = new Handler();
    private MyThread myThread;
    private ImageView iv_icon_tab_douyin;
    private TextView tv_tab_name_douyin;
    private boolean isTabOneRefresh = false;
    private boolean isTabTwoRefresh = false;

    @Override
    protected int layoutResId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        context = this;
        layoutInflater = LayoutInflater.from(context);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(null);//去除分割线
        tabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        myThread = new MyThread();


        tabManager.addTab(
                mTabHost.newTabSpec(TAB1).setIndicator(createTabIndicatorView(R.layout.tab_main)), DouYinVideoFragment.class, null);
        tabManager.addTab(
                mTabHost.newTabSpec(TAB2).setIndicator(createTabIndicatorView(R.layout.tab_discover)), DiscoveryVideoFragment.class, null);

        tabManager.addTab(
                mTabHost.newTabSpec(TAB3).setIndicator(createTabIndicatorView(R.layout.tab_hot)), HotVideoFragment.class, null);

//        tabManager.addTab(
//                mTabHost.newTabSpec(TAB3).setIndicator(createTabIndicatorView(R.layout.tab_discover)), FollowingVideoFragment.class, null);
        tabManager.addTab(
                mTabHost.newTabSpec(TAB4).setIndicator(createTabIndicatorView(R.layout.tab_mine)), MineCenterFragment.class, null);

        View tabOne = mTabHost.getTabWidget().getChildTabViewAt(0);
        iv_icon_tab_douyin = (ImageView) tabOne.findViewById(R.id.iv_icon_tab);
        tv_tab_name_douyin = (TextView) tabOne.findViewById(R.id.tv_tab_name);
        tabClick();
        setRefresh();

    }

    /**
     * tab点击
     */
    private void tabClick() {
        mTabHost.getTabWidget().getChildTabViewAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(0);
                if (isTabOneRefresh) {
                    EventBus.getDefault().post(new DoubleClickToRefreshEvent(true));
                    iv_icon_tab_douyin.setImageDrawable(getResources().getDrawable(R.drawable.bg_tab_small_video));
                    isTabOneRefresh = false;
//                    setRefresh();
                } else {
                    if (NoDoubleClickUtil.isDoubleClickOne()) {
                        EventBus.getDefault().post(new DoubleClickToRefreshEvent(true));
                    }
                    handler.removeCallbacks(myThread);
                    isTabOneRefresh = false;
//                    setRefresh();
                }
                setRefresh();
                tv_tab_name_douyin.setText("短视频");
                tv_tab_name_douyin.setTextColor(Color.parseColor("#1296db"));
            }
        });

        mTabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(1);
                handler.removeCallbacks(myThread);
                iv_icon_tab_douyin.setImageDrawable(getResources().getDrawable(R.drawable.bg_tab_small_video));
                tv_tab_name_douyin.setText("短视频");
                tv_tab_name_douyin.setTextColor(Color.parseColor("#707070"));
                isTabOneRefresh = false;
                if (NoDoubleClickUtil.isDoubleClickTwo()) {
                    EventBus.getDefault().post(new DiscoveryClickToRefreshEvent(true, view));
                }

            }
        });

        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(2);
                handler.removeCallbacks(myThread);
                iv_icon_tab_douyin.setImageDrawable(getResources().getDrawable(R.drawable.bg_tab_small_video));
                tv_tab_name_douyin.setText("短视频");
                tv_tab_name_douyin.setTextColor(R.drawable.bg_tab_text_color);
                tv_tab_name_douyin.setTextColor(Color.parseColor("#707070"));
                isTabOneRefresh = false;
            }
        });
    }

    private void setRefresh() {
        if (tabManager.getCurrentTab() == 0 && !isTabOneRefresh) {
            handler.postDelayed(myThread, 1000 * 60 * 5);
//            handler.postDelayed(myThread, 5000 );
        }
    }


    /**
     * 动态更新
     */
    private class MyThread implements Runnable {
        @Override
        public void run() {
            isTabOneRefresh = true;
            iv_icon_tab_douyin.setImageDrawable(getResources().getDrawable(R.drawable.bg_tab_small_video_refresh));
            tv_tab_name_douyin.setText("刷新");
            tv_tab_name_douyin.setTextColor(Color.parseColor("#1296db"));
        }
    }


    private View createTabIndicatorView(int layoutResource) {
        return layoutInflater.inflate(layoutResource, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTabHost.getCurrentTab() == 0 || mTabHost.getCurrentTab() == 2) {
            return;
        }

        if (VideoViewManager.instance().getCurrentVideoPlayer() != null) {
            VideoViewManager.instance().getCurrentVideoPlayer().resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (VideoViewManager.instance().getCurrentVideoPlayer() != null) {
            VideoViewManager.instance().getCurrentVideoPlayer().pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        boolean isCanBack = VideoHelper.get().isCanBack(this);
        if (!isCanBack) {
            return;
        }
        if (!VideoViewManager.instance().onBackPressed()) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtil.e(TAG, "横屏");
        } else {
            StatusBarCompat.setStatusBarColor(this, 0xfffffff);
            StatusBarFontHelper.setStatusBarMode(this, true);
        }
        super.onConfigurationChanged(newConfig);
    }

}
