package com.tinyblog.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.sys.Constants;

/**
 * @author xiarui
 * @date 2017/3/8 14:14
 * @description 内部WebView 页面
 * @remark
 */

public class WebActivity extends BaseActivity {
    private Toolbar mWebTBar;
    private WebView mWebWView;
    private String mCurUrl;
    //下拉刷新
    private SwipeRefreshLayout mWebSRLayout;
    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                if (mWebSRLayout.isRefreshing()) {
                    mWebSRLayout.setRefreshing(false);//设置不刷新
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        mWebTBar = (Toolbar) findViewById(R.id.tb_web);
        setSupportActionBar(mWebTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebWView = (WebView) findViewById(R.id.wv_web);
        mWebWView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebWView.getSettings().setLoadsImagesAutomatically(true);
        mWebWView.getSettings().setJavaScriptEnabled(true);
        mWebWView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebWView.getSettings().setDomStorageEnabled(true);

        mWebSRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_web);
        mWebSRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initData() {
        mWebTBar.setTitle("内部浏览器");
        mCurUrl = getIntent().getStringExtra(Constants.WEB_URL);
        mWebSRLayout.post(new Runnable() {
            @Override
            public void run() {
                mWebSRLayout.setRefreshing(true);
                //加载网络数据
                mWebWView.loadUrl(mCurUrl);
            }
        });
    }

    @Override
    public void initEvents() {
        //下拉刷新监听
        mWebSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mWebWView.loadUrl(mCurUrl);
                    }
                }).start();
            }
        });
        mWebWView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
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
                break;
            case R.id.menu_item_browser:
                gotoBrowser(mCurUrl);
                break;
        }
        return true;
    }

    /**
     * 跳转到浏览器界面
     *
     * @param urlStr 网址
     */
    private void gotoBrowser(String urlStr) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(urlStr);
        intent.setData(content_url);
        startActivity(intent);
    }
}
