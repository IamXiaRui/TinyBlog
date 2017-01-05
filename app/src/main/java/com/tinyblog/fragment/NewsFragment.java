package com.tinyblog.fragment;

import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.tinyblog.R;
import com.tinyblog.adapter.NewsListAdapter;
import com.tinyblog.base.BaseFragment;
import com.tinyblog.bean.NewsListBean;
import com.tinyblog.sys.App;
import com.tinyblog.utils.BannerImageLoaderUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;

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

    private ArrayList<NewsListBean> mTestList = new ArrayList<>();
    ;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
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
        //加载本地数据
        loaderLocalData(mTestList);
        //设置适配器
        mNewsLView.setAdapter(new NewsListAdapter(getContext(), mTestList));
    }

    /**
     * 加载模拟本地数据
     * TODO:需要删除
     *
     * @param mTestList 测试集合
     */
    private void loaderLocalData(ArrayList<NewsListBean> mTestList) {

        NewsListBean mTestBean1 = new NewsListBean();
        mTestBean1.setImageUrl("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        mTestBean1.setTitle("这是测试标题1");
        mTestBean1.setBrief("这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1这是测试简要1");
        mTestBean1.setReadNum("123");
        mTestBean1.setLikeNum("23");
        mTestBean1.setCommentNum("234");
        mTestList.add(mTestBean1);

        NewsListBean mTestBean2 = new NewsListBean();
        mTestBean2.setImageUrl("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        mTestBean2.setTitle("这是测试标题2这是测试标题2这是测试标题2");
        mTestBean2.setBrief("这是测试简要2这是测试简要2这是测试简要1这是测试简要2");
        mTestBean2.setReadNum("12");
        mTestBean2.setLikeNum("3445");
        mTestBean2.setCommentNum("23");
        mTestList.add(mTestBean2);

        NewsListBean mTestBean3 = new NewsListBean();
        mTestBean3.setImageUrl("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        mTestBean3.setTitle("这是测试标题3");
        mTestBean3.setBrief("这是测试简要3这是测试简要3这是测试简要3这是测试简要3这是测试简要3这是测试简要3");
        mTestBean3.setReadNum("23");
        mTestBean3.setLikeNum("324");
        mTestBean3.setCommentNum("78");
        mTestList.add(mTestBean3);

        NewsListBean mTestBean4 = new NewsListBean();
        mTestBean4.setImageUrl("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        mTestBean4.setTitle("这是测试标题4这是测试标题4这是测试标题4这是测试标题4");
        mTestBean4.setBrief("这是测试简要4");
        mTestBean4.setReadNum("576");
        mTestBean4.setLikeNum("45");
        mTestBean4.setCommentNum("789");
        mTestList.add(mTestBean4);

        NewsListBean mTestBean5 = new NewsListBean();
        mTestBean5.setImageUrl("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        mTestBean5.setTitle("这是测试标题5这是测试标题5这是测试标题5这是测试标题5这是测试标题5");
        mTestBean5.setBrief("这是测试简要5这是测试简要5这是测试简要5这是测试简要5这是测试简要5这是测试简要5");
        mTestBean5.setReadNum("57");
        mTestBean5.setLikeNum("425");
        mTestBean5.setCommentNum("89");
        mTestList.add(mTestBean5);

    }

    @Override
    public void initEvents() {

        mNewsSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(4200);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //停止刷新操作
                                mNewsSRLayout.setRefreshing(false);
                                showBaseToast("刷新完成！");
                            }
                        });
                    }
                }).start();
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
