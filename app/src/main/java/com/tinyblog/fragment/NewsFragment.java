package com.tinyblog.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.activity.SearchActivity;
import com.tinyblog.adapter.NewsListAdapter;
import com.tinyblog.base.BaseFragment;
import com.tinyblog.bean.NewsListRootBean;
import com.tinyblog.sys.App;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.tinyblog.utils.BannerImageLoaderUtil;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2016/10/11 11:42
 * @desc 动态
 */
public class NewsFragment extends BaseFragment {

    //下拉刷新
    private SwipeRefreshLayout mNewsSRLayout;
    //最新动态列表
    private ListView mNewsLView;
    //顶部轮播 Bannner
    private Banner mHeaderBanner;
    //搜索按钮，新增文章按钮
    private ImageButton searchIButton, addIButton;
    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                if (mNewsSRLayout.isRefreshing()) {
                    mNewsSRLayout.setRefreshing(false);//设置不刷新
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        searchIButton = (ImageButton) findViewById(R.id.ib_news_search);
        addIButton = (ImageButton) findViewById(R.id.ib_news_add);
        
        mNewsSRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_news);
        mNewsSRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mNewsLView = (ListView) findViewById(R.id.lv_news);
        //添加动态 Banner 头布局
        mNewsLView.addHeaderView(View.inflate(getActivity(), R.layout.header_news_list, null));
        mHeaderBanner = (Banner) findViewById(R.id.ban_news_list_header);
        mHeaderBanner.setImages(App.testBannerImages).setImageLoader(new BannerImageLoaderUtil()).start();
    }

    @Override
    public void initData() {
        //TODO 去掉注释
        /*if (!NetworkUtils.isConnected()) {
            showBaseToast("请检查网络");
        }*/
        //加载网络数据
        loaderNetWorkData();
        //开启自动刷新
        mNewsSRLayout.post(new Runnable() {
            @Override
            public void run() {
                mNewsSRLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void loaderNetWorkData() {
        OkHttpUtils
                .get()
                .url(Url.GET_RECENT_POSTS)
                .build()
                .execute(new NewsListCallBack());
    }

    /**
     * 获取最近文章回调
     */
    public class NewsListCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            NewsListRootBean newsListRootBean = new Gson().fromJson(response, NewsListRootBean.class);
            if (newsListRootBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                List<NewsListRootBean.PostsBean> mNewsList = newsListRootBean.getPosts();
                //设置适配器
                mNewsLView.setAdapter(new NewsListAdapter(getContext(), mNewsList));
                showBaseToast("刷新成功");
            } else {
                showBaseToast("数据异常，请重新刷新");
            }
        }
    }

    @Override
    public void initEvents() {
        //下拉刷新监听
        mNewsSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        //列表点击监听
        mNewsLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBaseToast(String.valueOf(position));
            }
        });

        searchIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent().setClass(getContext(), SearchActivity.class));
            }
        });

        addIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseToast("新增");
            }
        });
    }


    /*========== Fragment 生命周期方法 ==========*/
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mHeaderBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mHeaderBanner.stopAutoPlay();
    }
}
