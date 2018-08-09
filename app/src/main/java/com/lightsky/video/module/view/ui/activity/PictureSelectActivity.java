package com.lightsky.video.module.view.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.customview.WithWordsCircleProgressBar;
import com.lightsky.video.module.base.BaseActivity;
import com.lightsky.video.module.view.adapter.PhotoListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ivan.L on 2018/8/8.
 * 图片选择
 */
public class PictureSelectActivity extends BaseActivity {
    private static final String TAG = "---->PictureSelectActivity";
    private static final int PHOTO_REQUEST_TAKE_PHOTO = 11; //拍照
    @BindView(R.id.fl_back)
    FrameLayout flBack;
    @BindView(R.id.tv_title_bar_title)
    TextView tvTitleBarTitle;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    @BindView(R.id.wwcpb_progress)
    WithWordsCircleProgressBar wwcpbProgress;
    @BindView(R.id.fl_circle_progress)
    FrameLayout flCircleProgress;
    private Context context;
    private List<String> mPhotoPathList = new ArrayList<>(); //存放本地图片路径的集合
    private File FILEPATH_FILE = Environment.getExternalStorageDirectory(); //保存图片的路径
    private String imageName = ""; //图片名字
    private String mPicPath; //想要上传的图片路径
    private PhotoListAdapter photoListAdapter;


    @Override
    protected int layoutResId() {
        return R.layout.activity_picture_select;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        context = PictureSelectActivity.this;

    }

    /**
     * 返回点击
     */
    @OnClick(R.id.fl_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
    }
}
