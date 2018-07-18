package com.lightsky.video.common.pulltorefresh.loadmore;

import android.view.View;

/**
 * Created by Ivan.L on 2018/7/3.
 *
 */

public interface LoadMoreHandler {
    /**
     * @param contentView
     * @param loadMoreView
     * @param onClickLoadMoreListener
     * @return 是否有 initJPush ILoadMoreView
     */
    boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener
            onClickLoadMoreListener);

    void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

    void removeFooter();
    void addFooter();
}
