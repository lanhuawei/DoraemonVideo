package com.lanhuawei.cn.doraemonvideo.common.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;

import com.baidu.mobstat.StatService;

import java.util.HashMap;

/**
 * Created by Ivan.L on 2018/7/2.
 * 底部导航栏
 */

public class TabManager implements TabHost.OnTabChangeListener {
    private final FragmentActivity fragmentActivity;
    private final TabHost tabHost;
    private final int mContainerId;
    private final HashMap<String, TabInfo> tabInfoHashMap = new HashMap<>();
    TabInfo mlastTab;

    public TabManager(FragmentActivity fragmentActivity, TabHost tabHost, int mContainerId) {
        this.fragmentActivity = fragmentActivity;
        this.tabHost = tabHost;
        this.mContainerId = mContainerId;
        tabHost.setOnTabChangedListener(this);
    }


    final class TabInfo {
        private final String tag;
        private final Class<?> aClass;
        private final Bundle args;
        private Fragment fragment;

        public TabInfo(String tag, Class<?> aClass, Bundle args) {
            this.tag = tag;
            this.aClass = aClass;
            this.args = args;
        }
    }

    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context context;

        public DummyTabFactory(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String s) {
            View view = new View(context);
            view.setMinimumHeight(0);
            view.setMinimumWidth(0);
            return view;
        }
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
        tabSpec.setContent(new DummyTabFactory(fragmentActivity));
        String tag = tabSpec.getTag();
        TabInfo tabInfo = new TabInfo(tag, clss, args);
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state. If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(tabInfo.fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        tabInfoHashMap.put(tag, tabInfo);
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String s) {
        int pos = tabHost.getCurrentTab(); // To get tab position
        if (pos == 0) {
            StatService.onEvent(fragmentActivity, "aweme", "小视频");
        } else if (pos == 1) {
            StatService.onEvent(fragmentActivity, "recommon_tab", "底部推荐");
        } else if (pos == 2) {
            StatService.onEvent(fragmentActivity, "discover", "发现");
        }

        TabInfo newTab = tabInfoHashMap.get(s);
        if (mlastTab != newTab) {
            FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
            if (mlastTab != null) {
                if (mlastTab.fragment != null) {
                    ft.hide(mlastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(fragmentActivity, newTab.aClass.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.show(newTab.fragment);
                }
            }

            mlastTab = newTab;
            ft.commitAllowingStateLoss();
            fragmentActivity.getSupportFragmentManager().executePendingTransactions();
        }
    }

}
