package com.tinyblog.activity;


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

    private int mPostsListAllPage;
    private int mCurPostsListPage = 1;
    private Toolbar mCurPostsListTBar;
    private RecyclerView mPostsListRView;
    private String mPostsListUrl;
    //列表中最后一个可见的Item
    private int lastVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    private CurPostsListAdapter mCurPostsListAdapter;
    private List<PostListBean.PostsBean> mPostsBeanList;
    //正在上拉加载标记
    private boolean LOADING_MORE_TAG = false;
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
        mCurPostsListTBar = (Toolbar) findViewById(R.id.tb_zhihu_list);
        setSupportActionBar(mCurPostsListTBar);
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
        mCurPostsListTBar.setTitle(getIntent().getStringExtra(Constants.CUR_POSTS_TITLE));
        mCurPostsListTBar.setSubtitle(getIntent().getStringExtra(Constants.CUR_POSTS_COUNT) + " 篇文章");
        //检查列表类型
        checkPostsListType();
        //开启自动刷新
        mCurPostsListSRLayout.post(new Runnable() {
            @Override
            public void run() {
                mCurPostsListSRLayout.setRefreshing(true);
                //加载网络数据
                loaderNetWorkData();
            }
        });
    }

    /**
     * 检查列表类型：相同分类列表 OR 相同标签列表
     */
    private void checkPostsListType() {
        String postsListType = getIntent().getStringExtra(Constants.CATEGORY_OR_TAG);
        if (postsListType.equals(Constants.IS_CATEGORY)) {
            mPostsListUrl = Url.GET_CATEGORY_POSTS + "?id=" + getIntent().getStringExtra(Constants.CUR_POSTS_ID);
        } else if (postsListType.equals(Constants.IS_TAG)) {
            mPostsListUrl = Url.GET_TAG_POSTS + "?id=" + getIntent().getStringExtra(Constants.CUR_POSTS_ID);
        } else {
            showBaseToast("请求无效，请检查参数");
        }
    }

    /**
     * 加载网络数据
     */
    private void loaderNetWorkData() {
        OkHttpUtils
                .get()
                .url(mPostsListUrl)
                .build()
                .execute(new CurPostsListCallBack());
    }

    private class CurPostsListCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mCurPostsListSRLayout.setRefreshing(false); //设置不刷新
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            PostListBean postListBean = new Gson().fromJson(response, PostListBean.class);
            mPostsListAllPage = postListBean.getPages();
            if (postListBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                mPostsBeanList = postListBean.getPosts();
                mCurPostsListAdapter = new CurPostsListAdapter(getApplicationContext(), mPostsBeanList);
                mPostsListRView.setAdapter(new ScaleInAnimatorAdapter(mCurPostsListAdapter, mPostsListRView));
                mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                mPostsListRView.setLayoutManager(mLinearLayoutManager);
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
        mPostsListRView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mCurPostsListAdapter.getItemCount() && !LOADING_MORE_TAG) {
                    mCurPostsListPage += 1; //下一页
                    //再次请求
                    if (mCurPostsListPage <= mPostsListAllPage) {
                        LOADING_MORE_TAG = true;
                        mCurPostsListAdapter.changeLoadStatus(CurPostsListAdapter.LOADING_MORE);
                        loadMoreDataFromNet(mCurPostsListPage);
                    } else if (mCurPostsListPage == mPostsListAllPage + 1) {
                        mCurPostsListAdapter.changeLoadStatus(CurPostsListAdapter.NO_NEW_DATA);
                    } else if (mCurPostsListPage > mPostsListAllPage + 1) {
                        showBaseToast("已经到底了，再刷新是徒劳的——爱因斯坦");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadMoreDataFromNet(int curPageNum) {
        OkHttpUtils
                .get()
                .url(mPostsListUrl + "&page=" + curPageNum)
                .build()
                .execute(new LoadMorePostsListCallBack());
    }

    private class LoadMorePostsListCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            PostListBean postListBean = new Gson().fromJson(response, PostListBean.class);
            mPostsListAllPage = postListBean.getPages();
            if (postListBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                mCurPostsListAdapter.addMoreItem(postListBean.getPosts());
                LOADING_MORE_TAG = false;
            }
        }
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
                initData();
                return true;
        }
        return true;
    }
}
