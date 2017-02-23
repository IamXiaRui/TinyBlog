package com.tinyblog.activity;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.adapter.CurPostsListAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.PostListBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.tinyblog.utils.RVItemDecorationUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;
import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/2/23 10:02
 * @desc 当前选中的分类的文章列表
 * @remark 1.0
 */

public class CurPostsListActivity extends BaseActivity {

    private Toolbar mCurPostsListTBar;
    private RecyclerView mPostsListRView;
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

        mPostsListRView = (RecyclerView) findViewById(R.id.rv_cur_posts_list);
        mPostsListRView.addItemDecoration(new RVItemDecorationUtil(24));     //设置 item 间距
    }

    @Override
    public void initData() {
        mCurPostsListTBar.setTitle(getIntent().getStringExtra(Constants.CUR_CATEGORY_TITLE));
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
                .url(Url.GET_CATEGORY_POSTS + "?id=" + getIntent().getStringExtra(Constants.CUR_CATEGORY_ID) + "&page=1")
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
            PostListBean postListBean = new Gson().fromJson(response, PostListBean.class);
            if (postListBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                List<PostListBean.PostsBean> postsBeanList = postListBean.getPosts();
                CurPostsListAdapter adapter = new CurPostsListAdapter(getApplicationContext(), postsBeanList);
                mPostsListRView.setAdapter(new ScaleInAnimatorAdapter(adapter,mPostsListRView));
                mPostsListRView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        }
    }

    @Override
    public void initEvents() {
        //下拉刷新监听
        mCurPostsListSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loaderNetWorkData();
                    }
                }).start();
            }
        });
        //列表滚动事件监听
        mPostsListRView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mCurPostsListSRLayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
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
