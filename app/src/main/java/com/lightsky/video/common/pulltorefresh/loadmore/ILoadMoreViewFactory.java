package com.lightsky.video.common.pulltorefresh.loadmore;

import android.view.View;

/**
 * Created by Ivan.L on 2018/7/3.
 * 加载更多布局切换接口
 */

public interface ILoadMoreViewFactory {
    ILoadMoreView madeLoadMoreView();

    /**
     * ListView底部加载更多的布局切换
     *
     * @author Chanven
     */
    interface ILoadMoreView {

        /**
         * 初始化
         *
         * @param footViewHolder
         * @param onClickLoadMoreListener 加载更多的点击事件，需要点击调用加载更多的按钮都可以设置这个监听
         */
        void init(FootViewAdder footViewHolder, View.OnClickListener onClickLoadMoreListener);

        /**
         * 显示普通布局
         */
        void showNormal();

        /**
         * 显示已经加载完成，没有更多数据的布局
         */
        void showNomore();

        /**
         * 显示正在加载中的布局
         */
        void showLoading();

        /**
         * 显示加载失败的布局
         *
         * @param e
         */
        void showFail(Exception e);

        void setFooterVisibility(boolean isVisible);

    }

    interface FootViewAdder {

        View addFootView(View view);

        View addFootView(int layoutId);

    }
}
