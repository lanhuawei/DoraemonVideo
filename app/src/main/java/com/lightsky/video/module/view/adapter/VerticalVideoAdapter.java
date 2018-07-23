package com.lightsky.video.module.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Ivan.L on 2018/7/23.
 * 可以上下滑动，左右滑动页面适配器
 */

public class VerticalVideoAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;

    public VerticalVideoAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
