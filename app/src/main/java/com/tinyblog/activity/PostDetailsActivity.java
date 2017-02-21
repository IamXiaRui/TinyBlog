package com.tinyblog.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.PostDetailsBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zzhoujay.richtext.RichText;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/1/14 19:19
 * @desc 文章详情页
 * @remark
 */

public class PostDetailsActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mPostTitleText;
    private TextView mPostTimeText;
    private ProgressBar mPostPBar;
    private TextView mPostContentText;

    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                mPostPBar.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_post_details;
    }

    @Override
    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tb_post_details);
        setSupportActionBar(mToolbar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostTitleText = (TextView) findViewById(R.id.tv_post_details_title);
        mPostTimeText = (TextView) findViewById(R.id.tv_post_details_time);
        mPostPBar = (ProgressBar) findViewById(R.id.pb_post_details);
        mPostContentText = (TextView) findViewById(R.id.tv_post_details_content);
    }

    @Override
    public void initData() {
        OkHttpUtils
                .get()
                .url(Url.GET_POST_DETAILS + getIntent().getStringExtra(Constants.POST_DETAILS_ID))
                .build()
                .execute(new PostDetailsCallBack());
    }

    /**
     * 获取当前文章详情
     */
    public class PostDetailsCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("获取失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            PostDetailsBean postDetailsBean = new Gson().fromJson(response, PostDetailsBean.class);
            if (postDetailsBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                PostDetailsBean.PostBean postBean = postDetailsBean.getPost();
                mPostTitleText.setText(postBean.getTitle());
                mPostTimeText.setText(postBean.getDate());
                // 设置为Html
                //TODO: 代码块无法分行 GIF无法播放
                RichText.from(postBean.getContent()).into(mPostContentText);
            } else {
                showBaseToast("数据异常，请重新刷新");
            }
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
                mPostPBar.setVisibility(View.VISIBLE);
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
