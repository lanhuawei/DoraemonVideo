package com.lanhuawei.cn.doraemonvideo.common.pulltorefresh.loadmore;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanhuawei.cn.doraemonvideo.R;

/**
 * Created by Ivan.L on 2018/7/3.
 * default load more view
 */

public class DefaultLoadMoreViewFooter implements ILoadMoreViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected View footerView;
        protected TextView footerTv;
        protected ProgressBar footerBar;

        protected View.OnClickListener onClickRefreshListener;

        @Override
        public void init(FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener) {
            footerView = footViewHolder.addFootView(R.layout.default_loadmore_footer);
            footerTv = (TextView) footerView.findViewById(R.id.loadmore_default_footer_tv);
            footerBar = (ProgressBar) footerView.findViewById(R.id.loadmore_default_footer_progressbar);
            this.onClickRefreshListener = onClickRefreshListener;
//            showNormal();
            footerTv.setVisibility(View.INVISIBLE);

        }

        @Override
        public void showNormal() {
            footerTv.setVisibility(View.VISIBLE);
            footerTv.setText("点击加载更多");
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footerTv.setVisibility(View.VISIBLE);
            footerTv.setText("正在加载中...");
            footerBar.setVisibility(View.VISIBLE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            footerTv.setVisibility(View.VISIBLE);
            footerTv.setText("加载失败，点击重新");
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            footerTv.setVisibility(View.VISIBLE);
            footerTv.setText("暂无更多数据");
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void setFooterVisibility(boolean isVisible) {
            footerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}
