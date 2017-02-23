package com.tinyblog.activity;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.utils.NetworkUtils;
import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/2/23 10:02
 * @desc 当前选中的分类的文章列表
 * @remark 1.0
 */

public class CurPostsListActivity extends BaseActivity {

    private Toolbar mCurPostsListTBar;
    //下拉刷新
    private SwipeRefreshLayout mCurPostsListSRLayout;
    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                if (mCurPostsListSRLayout.isRefreshing()) {
                    mCurPostsListSRLayout.setRefreshing(false);//设置不刷新
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_cur_posts_list;
    }

    @Override
    public void initView() {
        mCurPostsListTBar = (Toolbar) findViewById(R.id.tb_cur_posts_list);
        setSupportActionBar(mCurPostsListTBar);
        mCurPostsListTBar.setTitleTextColor(Color.WHITE);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurPostsListSRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_cur_posts_list);
        mCurPostsListSRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initData() {
        mCurPostsListTBar.setTitle(getIntent().getStringExtra(Constants.CUR_CATEGORY_TITLE));
        showBaseToast(getIntent().getStringExtra(Constants.CUR_CATEGORY_ID));

        //加载网络数据
        loaderNetWorkData();
        //开启自动刷新
        mCurPostsListSRLayout.post(new Runnable() {
            @Override
            public void run() {
                mCurPostsListSRLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void loaderNetWorkData() {
        OkHttpUtils
                .get()
                .url(Url.GET_CATEGORY_POSTS)
                .build()
                .execute(new CurCategoryListListCallBack());
    }

    private class CurCategoryListListCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {

        }
    }

    @Override
    public void initEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_refresh:
                initData();
                return true;
            case R.id.menu_item_share:
                showBaseToast("分享");
                return true;
            case R.id.menu_item_comment:
                showBaseToast("评论");
                return true;
        }
        return true;
    }
}
