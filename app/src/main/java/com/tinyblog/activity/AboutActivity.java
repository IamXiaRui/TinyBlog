package com.tinyblog.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.sys.Constants;

/**
 * 关于页面
 */
public class AboutActivity extends BaseActivity {
    private Toolbar mAboutTBar;
    private TextView mWebText;
    private TextView mAppWebText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mAboutTBar = (Toolbar) findViewById(R.id.tb_about);
        setSupportActionBar(mAboutTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebText = (TextView) findViewById(R.id.tv_about_myweb);
        mAppWebText = (TextView) findViewById(R.id.tv_about_appweb);
    }

    @Override
    public void initData() {
        mAboutTBar.setTitle("关于软件");
        mWebText.setText(Html.fromHtml("<a href=\\\"http://www.iamxiarui.com/\\\">个人主页</a>"));
        mAppWebText.setText(Html.fromHtml("<a href=\\\"https://github.com/IamXiaRui/TinyBlog\\\">项目地址</a>"));
    }

    @Override
    public void initEvents() {
        mWebText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this,WebActivity.class).putExtra(Constants.WEB_URL,"http://www.iamxiarui.com/"));
            }
        });
        mAppWebText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this,WebActivity.class).putExtra(Constants.WEB_URL,"https://github.com/IamXiaRui/TinyBlog"));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
