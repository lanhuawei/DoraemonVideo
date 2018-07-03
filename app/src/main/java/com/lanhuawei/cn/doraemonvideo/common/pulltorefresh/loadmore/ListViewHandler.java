package com.lanhuawei.cn.doraemonvideo.common.pulltorefresh.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Ivan.L on 2018/7/3.
 *
 */

public class ListViewHandler implements LoadMoreHandler {
    private ListView mListView;
    private View mFooter;

    @Override
    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        final ListView listView = (ListView) contentView;
        mListView = listView;
        boolean hasInit = false;
        if (loadMoreView != null) {
            final Context context = listView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, listView, false);
                    mFooter = view;
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    listView.addFooterView(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
        }
        return hasInit;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        ListView listView = (ListView) contentView;
        listView.setOnScrollListener(new ListViewOnScrollListener(onScrollBottomListener));
        listView.setOnItemSelectedListener(new ListViewOnItemSelectedListener(onScrollBottomListener));
    }

    @Override
    public void removeFooter() {
        if (mListView.getFooterViewsCount() > 0 && null != mFooter) {
            mListView.removeFooterView(mFooter);
        }
    }

    @Override
    public void addFooter() {
        if (mListView.getFooterViewsCount() <= 0 && null != mFooter) {
            mListView.addFooterView(mFooter);
        }
    }

    /**
     * 针对于电视 选择到了底部项的时候自动加载更多数据
     */
    private class ListViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
            if (listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
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
    private static class ListViewOnScrollListener implements AbsListView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
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
