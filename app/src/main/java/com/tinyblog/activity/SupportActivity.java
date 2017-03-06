package com.tinyblog.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.adapter.SupportAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.SupportBean;

import java.io.IOException;
import java.io.InputStream;

/**
 * 开源支持页面
 */
public class SupportActivity extends BaseActivity {
    private Toolbar mSupportTBar;
    private ListView mSupportLView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_support;
    }

    @Override
    public void initView() {
        mSupportTBar = (Toolbar) findViewById(R.id.tb_support);
        setSupportActionBar(mSupportTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSupportLView = (ListView) findViewById(R.id.lv_support);
    }

    @Override
    public void initData() {
        mSupportTBar.setTitle("开源支持");
        SupportBean supportBean = new Gson().fromJson(readLocalJson(), SupportBean.class);
        mSupportLView.setAdapter(new SupportAdapter(this, supportBean.getLibs()));
    }

    private String readLocalJson() {
        String content = "";
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(R.raw.libs);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            content = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    @Override
    public void initEvents() {
        mSupportLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SupportBean.LibsBean itemLibBean = (SupportBean.LibsBean) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(itemLibBean.getUrl());
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}
