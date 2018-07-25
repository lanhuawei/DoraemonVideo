package com.lightsky.video.module.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.lightsky.video.module.base.BaseLoadFragment;
import com.lightsky.video.module.view.ui.fragment.HotVideoItemFragment;

/**
 * Created by Ivan.L on 2018/7/25.
 * 热门视频Fragment适配器
 */
public class HotVideoPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private SparseArray<BaseLoadFragment> fragmentSparseArray = new SparseArray<>();
    public HotVideoPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(HotVideoItemFragment.POSITION, position);
        args.putString(HotVideoItemFragment.TITLE, titles[position]);
        fragmentSparseArray.put(position, HotVideoItemFragment.newInstance(args));
        return fragmentSparseArray.get(position);
    }
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
