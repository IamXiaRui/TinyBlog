package com.tinyblog.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tinyblog.R;
import com.tinyblog.adapter.GithubListAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.GithubBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/3/7 21:13
 * @description Github 每日 Trending
 * @remark
 */

public class GithubTrendingActivity extends BaseActivity {
    private ImageView mGithubBackImage;
    private Toolbar mGithubListTBar;
    private ListView mGithubLView;
    private MaterialDialog mTipsDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_github_posts;
    }

    @Override
    public void initView() {
        mGithubBackImage = (ImageView) findViewById(R.id.iv_github_back);

        mGithubListTBar = (Toolbar) findViewById(R.id.tb_github_list);
        setSupportActionBar(mGithubListTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGithubLView = (ListView) findViewById(R.id.lv_github_list);
    }

    @Override
    public void initData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        mGithubListTBar.setTitle(formatter.format(curDate));
        Glide.with(this).load(R.drawable.heng_5).into(mGithubBackImage);
        mTipsDialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content("此链接不稳定，请求时间较长，请耐心等待")
                .progress(true, 0)
                .show();
        loadDataFromNet();
    }

    private void loadDataFromNet() {
        OkHttpUtils
                .get()
                .url(Url.GITHUB_TRENDING+"java?since=daily")
                .build()
                .execute(new GithubCallBack());
    }

    private class GithubCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(GithubTrendingActivity.this, "刷新失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {
            if (response == null) {
                new MaterialDialog.Builder(GithubTrendingActivity.this)
                        .title("提示")
                        .content("由于此 API 限制链接次数，暂未收到反馈，请稍后再试")
                        .positiveText("重新请求")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                showBaseToast("正在重新请求...");
                                loadDataFromNet();
                            }
                        })
                        .negativeText("我知道了")
                        .canceledOnTouchOutside(true)
                        .show();
            } else {
                mTipsDialog.dismiss();
                showBaseToast("请求成功");
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                Gson gson = new Gson();
                List<GithubBean> githubBeanList = new ArrayList<>();
                for (JsonElement user : jsonArray) {
                    GithubBean githubBean = gson.fromJson(user, GithubBean.class);
                    githubBeanList.add(githubBean);
                }
                mGithubLView.setAdapter(new GithubListAdapter(GithubTrendingActivity.this, githubBeanList));
                setListViewHeightBasedOnChildren(mGithubLView);
            }
        }
    }

    @Override
    public void initEvents() {
        mGithubLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GithubBean itemGithubBean = (GithubBean) parent.getItemAtPosition(position);
                startActivity(new Intent(GithubTrendingActivity.this,WebActivity.class).putExtra(Constants.WEB_URL,itemGithubBean.getLink()));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        OkHttpUtils.getInstance().cancelTag(this);
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
