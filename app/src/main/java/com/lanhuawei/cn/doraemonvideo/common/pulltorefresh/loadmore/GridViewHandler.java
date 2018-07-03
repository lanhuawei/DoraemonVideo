package com.lanhuawei.cn.doraemonvideo.common.pulltorefresh.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by Ivan.L on 2018/7/3.
 *
 */

public class GridViewHandler implements LoadMoreHandler {
    private GridViewWithHeaderAndFooter mGridView;
    private View mFooter;

    @Override
    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        mGridView = (GridViewWithHeaderAndFooter) contentView;
        ListAdapter adapter = mGridView.getAdapter();
        boolean hasInit = false;
        if (loadMoreView != null) {
            final Context context = mGridView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, mGridView, false);
                    mFooter = view;
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    mGridView.addFooterView(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
            if (null != adapter) {
                mGridView.setAdapter(adapter);
            }
        }
        return hasInit;
    }

    @Override
    public void addFooter() {
        if (mGridView.getFooterViewCount() <= 0 && null != mFooter) {
            mGridView.addFooterView(mFooter);
        }
    }

    @Override
    public void removeFooter() {
        if (mGridView.getFooterViewCount() > 0 && null != mFooter) {
            mGridView.removeFooterView(mFooter);
        }
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) contentView;
        gridView.setOnScrollListener(new GridViewOnScrollListener(onScrollBottomListener));
        gridView.setOnItemSelectedListener(new GridViewOnItemSelectedListener(onScrollBottomListener));
    }

    /**
     * 针对于电视 选择到了底部项的时候自动加载更多数据
     */
    private class GridViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (adapterView.getLastVisiblePosition() + 1 == adapterView.getCount()) {// 如果滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    ;

    /**
     * 滚动到底部自动加载更多数据
     */
    private static class GridViewOnScrollListener implements AbsListView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() + 1 == view.getCount()) {// 如果滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
