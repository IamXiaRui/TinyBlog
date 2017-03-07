package com.tinyblog.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.adapter.ZhihuListAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.ZhihuPostBean;
import com.tinyblog.bean.ZhihuRootBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/3/7 11:55
 * @description 发现栏目页面
 * @remark
 */

public class FindPostsListActivity extends BaseActivity {
    private ImageView mPostBackImage;
    private Toolbar mFindListTBar;
    private ListView mPostsListView;
    private List<ZhihuPostBean> mZhihuBeanList = new ArrayList<>();
    private ZhihuPostBean itemZhihuPostBean;
    private RotateAnimation mFlipAnimation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_posts;
    }

    @Override
    public void initView() {
        mPostBackImage = (ImageView) findViewById(R.id.iv_zhihu_back);

        mFindListTBar = (Toolbar) findViewById(R.id.tb_zhihu_list);
        setSupportActionBar(mFindListTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostsListView = (ListView) findViewById(R.id.lv_zhihu_list);

        mFlipAnimation = new RotateAnimation(0, 45, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(0);
        mFlipAnimation.setFillAfter(true);
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.drawable.heng_1).into(mPostBackImage);
        loadDataFromNet();
    }

    /**
     * 加载网络数据
     */
    private void loadDataFromNet() {
        OkHttpUtils
                .get()
                .url(Url.ZHIHU_RECENT)
                .build()
                .execute(new ZhihuRecentCallBack());
    }


    private class ZhihuRecentCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(FindPostsListActivity.this, "刷新失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {
            Toast.makeText(FindPostsListActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
            ZhihuRootBean zhihuRootBean = new Gson().fromJson(response, ZhihuRootBean.class);
            mFindListTBar.setTitle(zhihuRootBean.getDate().substring(0, 4)
                    + "-" + zhihuRootBean.getDate().substring(4, 6)
                    + "-" + zhihuRootBean.getDate().substring(6, 8));
            List<ZhihuPostBean> zhihuPostBean = handleNetData(zhihuRootBean);
            mPostsListView.setAdapter(new ZhihuListAdapter(FindPostsListActivity.this, zhihuPostBean));
            setListViewHeightBasedOnChildren(mPostsListView);
        }
    }

    /**
     * 处理网络数据
     */
    private List<ZhihuPostBean> handleNetData(ZhihuRootBean zhihuRootBean) {
        boolean isSameTag = false;
        for (int i = 0; i < zhihuRootBean.getStories().size(); i++) {
            itemZhihuPostBean = new ZhihuPostBean();
            ZhihuRootBean.StoriesBean storiesBean = zhihuRootBean.getStories().get(i);
            itemZhihuPostBean.setImageUrl(storiesBean.getImages().get(0));
            itemZhihuPostBean.setPostId(storiesBean.getId());
            itemZhihuPostBean.setTitle(storiesBean.getTitle());
            //去除重复的
            for (int j = 0; j < mZhihuBeanList.size(); j++) {
                if (itemZhihuPostBean.getPostId() == mZhihuBeanList.get(j).getPostId()) {
                    isSameTag = true;
                }
            }
            if (!isSameTag) {
                mZhihuBeanList.add(itemZhihuPostBean);
            }
        }
        for (int i = 0; i < zhihuRootBean.getTop_stories().size(); i++) {
            itemZhihuPostBean = new ZhihuPostBean();
            ZhihuRootBean.TopStoriesBean topStoriesBean = zhihuRootBean.getTop_stories().get(i);
            itemZhihuPostBean.setImageUrl(topStoriesBean.getImage());
            itemZhihuPostBean.setPostId(topStoriesBean.getId());
            itemZhihuPostBean.setTitle(topStoriesBean.getTitle());
            //去除重复的
            for (int j = 0; j < mZhihuBeanList.size(); j++) {
                if (itemZhihuPostBean.getPostId() == mZhihuBeanList.get(j).getPostId()) {
                    isSameTag = true;
                }
            }
            if (!isSameTag) {
                mZhihuBeanList.add(itemZhihuPostBean);
            }
        }
        return mZhihuBeanList;
    }

    @Override
    public void initEvents() {
        mPostsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ZhihuPostBean itemZhihuPostBean = (ZhihuPostBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(FindPostsListActivity.this, ZhihuPostDetailActivity.class)
                        .putExtra(Constants.ZHIHU_POST_ID, "" + itemZhihuPostBean.getPostId());
                startActivity(intent);
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

    /**
     * 处理 ScrollView 嵌套 ListView 只显示一条的方法
     *
     * @param listView 需要处理的ListView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
