package com.tinyblog.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tinyblog.R;
import com.tinyblog.activity.GithubTrendingActivity;
import com.tinyblog.activity.ZhihuPostsListActivity;
import com.tinyblog.base.BaseFragment;

/**
 * @author xiarui
 * @date 2016/10/11 11:42
 * @desc 发现页面
 */
public class FindFragment extends BaseFragment {
    private ImageButton mRightRefreshIButton;
    private TextView mMidTitleText;
    private ImageView mZhiHuImage, mGithubImage, mOtherImage;
    private CardView mZhiHuCView, mGithubCView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        mRightRefreshIButton = (ImageButton) findViewById(R.id.ib_find_ask);
        mMidTitleText = (TextView) findViewById(R.id.tv_find_header);
        mZhiHuImage = (ImageView) findViewById(R.id.iv_find_zhihu);
        mGithubImage = (ImageView) findViewById(R.id.iv_find_github);
        mOtherImage = (ImageView) findViewById(R.id.iv_find_other);
        mZhiHuCView = (CardView) findViewById(R.id.cv_find_zhihu);
        mGithubCView = (CardView) findViewById(R.id.cv_find_github);

    }

    @Override
    public void initData() {
        mMidTitleText.setText("发现");
        Glide.with(getContext()).load(R.drawable.heng_1).into(mZhiHuImage);
        Glide.with(getContext()).load(R.drawable.heng_5).into(mGithubImage);
        Glide.with(getContext()).load(R.drawable.heng_3).into(mOtherImage);
    }


    @Override
    public void initEvents() {
        mZhiHuCView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ZhihuPostsListActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), mZhiHuCView, "zhihu").toBundle());
            }
        });
        mGithubCView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GithubTrendingActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), mGithubCView, "github").toBundle());
            }
        });
        mRightRefreshIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title("提示")
                        .content("本栏目为知名媒体、博客、网站等精彩内容综合")
                        .positiveText("了解")
                        .canceledOnTouchOutside(true)
                        .show();
            }
        });
    }
}
