package com.tinyblog.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.TextView;

import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.zzhoujay.markdown.MarkDown;

/**
 * 个人简历页面
 */
public class PersonResumeActivity extends BaseActivity {
    private Toolbar mResumeTBar;
    private TextView mResumeText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resume;
    }

    @Override
    public void initView() {
        mResumeTBar = (Toolbar) findViewById(R.id.tb_person_resume);
        setSupportActionBar(mResumeTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mResumeText = (TextView) findViewById(R.id.tv_person_resume);
    }

    @Override
    public void initData() {
        mResumeTBar.setTitle("个人简历");
        mResumeText.post(new Runnable() {
            @Override
            public void run() {
                Spanned spanned = MarkDown.fromMarkdown(getResources().openRawResource(R.raw.person_resume), new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable = new ColorDrawable(Color.LTGRAY);
                        drawable.setBounds(0, 0, 400, 400);
                        return drawable;
                    }
                }, mResumeText);
                mResumeText.setText(spanned);
            }
        });
    }


    @Override
    public void initEvents() {

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
