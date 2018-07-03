package com.lanhuawei.cn.doraemonvideo.module.base;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.lanhuawei.cn.doraemonvideo.R;
import com.lanhuawei.cn.doraemonvideo.common.Util.LogUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.statusbar.StatusBarFontHelper;
import com.lanhuawei.cn.doraemonvideo.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lanhuawei.cn.doraemonvideo.common.Util.ToastUtil;
import com.lanhuawei.cn.doraemonvideo.common.manager.TabManager;
import com.lanhuawei.cn.doraemonvideo.common.videoplayer.player.VideoViewManager;
import com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment.DiscoverVideoFragment;
import com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment.DouYinVideoFragment;
import com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment.HotVideoFragment;
import com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment.MineCenterFragment;
import com.lightsky.video.VideoHelper;

/**
 * Created by Ivan.L on 2018/6/29.
 * PS:activity_main.xml 跟360快视频SDK冲突
 */

public class MainTabActivity extends BaseActivity {
    private TabHost mTabHost;
    private TabManager tabManager;
    private LayoutInflater layoutInflater;
    private Context context;
    private static final String TAG = "--->MainTabActivity";

    private final String TAB1 = "MAIN";
    private final String TAB2 = "VIDEO";
    private final String TAB3 = "FOLLOWING";
    private final String TAB4 = "MINE";

    private long mExitTime;

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

        tabManager.addTab(
                mTabHost.newTabSpec(TAB1).setIndicator(createTabIndicatorView(R.layout.tab_main)), DouYinVideoFragment.class, null);
        tabManager.addTab(
                mTabHost.newTabSpec(TAB2).setIndicator(createTabIndicatorView(R.layout.tab_hot)), HotVideoFragment.class, null);
        tabManager.addTab(
                mTabHost.newTabSpec(TAB3).setIndicator(createTabIndicatorView(R.layout.tab_discover)), DiscoverVideoFragment.class, null);
        tabManager.addTab(
                mTabHost.newTabSpec(TAB4).setIndicator(createTabIndicatorView(R.layout.tab_mine)), MineCenterFragment.class, null);


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
