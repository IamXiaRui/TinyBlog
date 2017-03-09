package com.tinyblog.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tinyblog.R;
import com.tinyblog.adapter.DraftAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.DraftBean;
import com.tinyblog.db.NewPostModel;
import com.tinyblog.sys.Constants;
import com.tinyblog.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/9 18:01
 * @description 草稿箱
 * @remark
 */

public class DraftActivity extends BaseActivity {
    private Toolbar mDraftTBar;
    private ListView mDraftLView;
    private List<DraftBean> mDraftBeanList = new ArrayList<>();
    private DraftAdapter mDraftAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_draft;
    }

    @Override
    public void initView() {
        mDraftTBar = (Toolbar) findViewById(R.id.tb_draft);
        setSupportActionBar(mDraftTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDraftLView = (ListView) findViewById(R.id.lv_draft);
    }

    @Override
    public void initData() {
        mDraftTBar.setTitle("草稿箱");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次先清空旧数据
        mDraftBeanList.clear();
        mDraftAdapter = new DraftAdapter(this, queryPostListFromDB());
        mDraftLView.setAdapter(mDraftAdapter);
    }

    /**
     * 从数据库中查询数据
     *
     * @return 数据集合
     */
    private List<DraftBean> queryPostListFromDB() {
        List<NewPostModel> newPostModels = new Select().from(NewPostModel.class).queryList();
        DraftBean itemDraftBean;
        for (int i = 0; i < newPostModels.size(); i++) {
            itemDraftBean = new DraftBean();
            itemDraftBean.setTitle(newPostModels.get(i).title);
            itemDraftBean.setContent(AppUtil.fromHtml(newPostModels.get(i).content).toString());
            itemDraftBean.setTime(newPostModels.get(i).time);
            mDraftBeanList.add(itemDraftBean);
        }
        return mDraftBeanList;
    }

    @Override
    public void initEvents() {
        mDraftLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DraftBean itemDraftBean = (DraftBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(DraftActivity.this, AddNewPostActivity.class)
                        .putExtra(Constants.DRAFT_TITLE, itemDraftBean.getTitle())
                        .putExtra(Constants.DRAFT_CONTENT, itemDraftBean.getContent())
                        .putExtra(Constants.IS_FROM_DRAFT, "true");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_draft_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_draft_cancel:
                //清空数据库
                new Delete().from(NewPostModel.class).execute();
                if (new Select().from(NewPostModel.class).queryList().isEmpty()) {
                    showBaseToast("清空草稿箱成功");
                    mDraftBeanList.clear();
                    mDraftAdapter.notifyDataSetChanged();
                } else {
                    showBaseToast("请求失败，请再试一次");
                }
                break;
        }
        return true;
    }
}
