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
import com.tinyblog.adapter.ZhihuListAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.ZhihuPostBean;
import com.tinyblog.db.CollectModel;
import com.tinyblog.sys.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/7 18:49
 * @description 收藏列表页面
 * @remark
 */

public class CollectPostListActivity extends BaseActivity {
    private Toolbar mCollectTBar;
    private ListView mCollectLView;
    private List<ZhihuPostBean> mPostBeanList = new ArrayList<>();
    private ZhihuListAdapter mCollectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect_post_list;
    }

    @Override
    public void initView() {
        mCollectTBar = (Toolbar) findViewById(R.id.tb_collect_list);
        setSupportActionBar(mCollectTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollectLView = (ListView) findViewById(R.id.lv_collect_list);
    }

    @Override
    public void initData() {
        mCollectTBar.setTitle("收藏");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次先清空旧数据
        mPostBeanList.clear();
        mCollectAdapter = new ZhihuListAdapter(this, queryPostListFromDB());
        mCollectLView.setAdapter(mCollectAdapter);
    }

    /**
     * 从数据库中查询数据
     *
     * @return 数据集合
     */
    private List<ZhihuPostBean> queryPostListFromDB() {
        List<CollectModel> collectList = new Select().from(CollectModel.class).queryList();
        ZhihuPostBean itemZhihuPostBean;
        for (int i = 0; i < collectList.size(); i++) {
            itemZhihuPostBean = new ZhihuPostBean();
            itemZhihuPostBean.setPostId(Integer.parseInt(collectList.get(i).postId));
            itemZhihuPostBean.setTitle(collectList.get(i).title);
            itemZhihuPostBean.setImageUrl(collectList.get(i).image);
            mPostBeanList.add(itemZhihuPostBean);
        }
        return mPostBeanList;
    }

    @Override
    public void initEvents() {
        mCollectLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ZhihuPostBean itemZhihuPostBean = (ZhihuPostBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(CollectPostListActivity.this, ZhihuPostDetailActivity.class)
                        .putExtra(Constants.ZHIHU_POST_ID, "" + itemZhihuPostBean.getPostId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collect_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_cancel:
                //清空数据库
                new Delete().from(CollectModel.class).execute();
                if (new Select().from(CollectModel.class).queryList().isEmpty()) {
                    showBaseToast("全部取消收藏成功");
                    mPostBeanList.clear();
                    mCollectAdapter.notifyDataSetChanged();
                }else{
                    showBaseToast("请求失败，请再试一次");
                }
                break;
        }
        return true;
    }
}
