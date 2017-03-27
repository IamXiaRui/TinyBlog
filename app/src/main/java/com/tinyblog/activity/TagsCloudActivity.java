package com.tinyblog.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.moxun.tagcloudlib.view.TagCloudView;
import com.tinyblog.R;
import com.tinyblog.adapter.TagsCloudAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.PostsTagsBean;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/1/14 22:13
 * @desc 搜索页面
 * @remark
 */

public class TagsCloudActivity extends BaseActivity {
    private Toolbar mTagsCloudTBar;
    private TagCloudView mTagsCloudView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tags;
    }

    @Override
    public void initView() {
        mTagsCloudTBar = (Toolbar) findViewById(R.id.tb_tags_cloud);
        setSupportActionBar(mTagsCloudTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTagsCloudView = (TagCloudView) findViewById(R.id.tcv_tags_cloud);
    }

    @Override
    public void initData() {
        mTagsCloudTBar.setTitle("标签云");
        loaderNetWorkData();
    }

    private void loaderNetWorkData() {
        OkHttpUtils
                .get()
                .url(Url.GET_TAG_INDEX)
                .build()
                .execute(new PostsTagsCallBack());
    }

    private class PostsTagsCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            PostsTagsBean postsTagsBean = new Gson().fromJson(response, PostsTagsBean.class);
            if (postsTagsBean.getStatus().equals("ok")) {
                mTagsCloudView.setAdapter(new TagsCloudAdapter(postsTagsBean.getTags()));
            }
        }
    }

    @Override
    public void initEvents() {

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

    @Override
    public void onStop() {
        super.onStop();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
