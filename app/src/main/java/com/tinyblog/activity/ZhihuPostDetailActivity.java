package com.tinyblog.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.ZhihuPostDetailBean;
import com.tinyblog.db.CollectModel;
import com.tinyblog.db.CollectModel_Table;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/3/7 14:22
 * @description 知乎文章详情页面
 * @remark
 */

public class ZhihuPostDetailActivity extends BaseActivity {
    private Toolbar mZhihuDetailTBar;
    private ImageView mZhihuBackImage;
    private TextView mZhihuDetailText;
    private FloatingActionButton mLoveFAButton;
    private ZhihuPostDetailBean mZhihuPostDetailBean;
    private String mCurPostId;
    //是否被收藏标志位
    private boolean IS_COLLECT;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_post_detail;
    }

    @Override
    public void initView() {
        mZhihuDetailTBar = (Toolbar) findViewById(R.id.tb_zhihu_detail_list);
        setSupportActionBar(mZhihuDetailTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mZhihuBackImage = (ImageView) findViewById(R.id.iv_zhihu_detail_back);
        mZhihuDetailText = (TextView) findViewById(R.id.tv_zhihu_detail);

        mLoveFAButton = (FloatingActionButton) findViewById(R.id.fab_zhihu_detail_love);
    }

    @Override
    public void initData() {
        mCurPostId = getIntent().getStringExtra(Constants.ZHIHU_POST_ID);
        //判断当前文章是否被收藏
        IS_COLLECT = judgeCurPostIsCollect(mCurPostId);
        //获取网络数据
        loadDataFromNet(mCurPostId);
    }

    /**
     * 判断文章是否被收藏
     *
     * @param curPostId 文章 Id
     */
    private boolean judgeCurPostIsCollect(String curPostId) {
        //查表判断收藏没有
        List<CollectModel> collectModels = new Select().from(CollectModel.class).where(CollectModel_Table.postId.is(curPostId)).queryList();
        if (!collectModels.isEmpty()) {
            mLoveFAButton.setImageResource(R.drawable.svg_love2);
            return true;
        } else {
            mLoveFAButton.setImageResource(R.drawable.svg_love1);
            return false;
        }
    }

    /**
     * 联网获取数据
     *
     * @param curPostId 文章 Id
     */
    private void loadDataFromNet(String curPostId) {
        OkHttpUtils.get()
                .url(Url.ZHIHU_POST_DETAIL + curPostId)
                .build()
                .execute(new ZhihuPostDetailCallBack());
    }

    private class ZhihuPostDetailCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(ZhihuPostDetailActivity.this, "刷新失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {
            mZhihuPostDetailBean = new Gson().fromJson(response, ZhihuPostDetailBean.class);
            mZhihuDetailTBar.setTitle(mZhihuPostDetailBean.getTitle());
            Glide.with(ZhihuPostDetailActivity.this).load(mZhihuPostDetailBean.getImage()).into(mZhihuBackImage);
            RichText.from(mZhihuPostDetailBean.getBody()).into(mZhihuDetailText);
        }
    }

    @Override
    public void initEvents() {
        mLoveFAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果已经收藏了
                if (IS_COLLECT) {
                    new MaterialDialog.Builder(ZhihuPostDetailActivity.this)
                            .title("提示")
                            .content("是否取消收藏此篇文章")
                            .positiveText("取消收藏")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                    //从数据库删除
                                    new Delete().from(CollectModel.class).where(CollectModel_Table.postId.is(mCurPostId)).execute();
                                    if (!judgeCurPostIsCollect(mCurPostId)) {
                                        showBaseToast("取消收藏成功");
                                        IS_COLLECT = !IS_COLLECT;
                                        mLoveFAButton.setImageResource(R.drawable.svg_love1);
                                    } else {
                                        showBaseToast("取消收藏失败");
                                    }
                                }
                            })
                            .canceledOnTouchOutside(true)
                            .show();
                } else if (!IS_COLLECT) { //如果没有被收藏
                    new MaterialDialog.Builder(ZhihuPostDetailActivity.this)
                            .title("提示")
                            .content("是否收藏此篇文章")
                            .positiveText("收藏")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                    //存入数据库
                                    CollectModel collectModel = new CollectModel();
                                    collectModel.postId = mCurPostId;
                                    collectModel.image = mZhihuPostDetailBean.getImage();
                                    collectModel.title = mZhihuPostDetailBean.getTitle();
                                    collectModel.save();
                                    if (judgeCurPostIsCollect(mCurPostId)) {
                                        showBaseToast("收藏成功");
                                        IS_COLLECT = !IS_COLLECT;
                                        mLoveFAButton.setImageResource(R.drawable.svg_love2);
                                    } else {
                                        showBaseToast("收藏失败");
                                    }
                                }
                            })
                            .canceledOnTouchOutside(true)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cur_posts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_refresh:
                Toast.makeText(this, "正在刷新...", Toast.LENGTH_SHORT).show();
                initData();
                break;
        }
        return true;
    }

}
