package com.tinyblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;
import com.tinyblog.activity.CurPostsListActivity;
import com.tinyblog.bean.PostsTagsBean;
import com.tinyblog.sys.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/6 14:02
 * @description 标签云适配器
 * @remark
 */

public class TagsCloudAdapter extends TagsAdapter {

    private List<PostsTagsBean.TagsBean> mTagsBeanList = new ArrayList<>();

    public TagsCloudAdapter(List<PostsTagsBean.TagsBean> mTagsBeanList) {
        this.mTagsBeanList = mTagsBeanList;
    }

    @Override
    public int getCount() {
        return mTagsBeanList.isEmpty() ? 0 : mTagsBeanList.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup viewGroup) {
        final TextView tv = new TextView(context);
        PostsTagsBean.TagsBean tagsBean = mTagsBeanList.get(position);
        tv.setText(tagsBean.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .putExtra(Constants.CUR_POSTS_TITLE, mTagsBeanList.get(position).getTitle())
                        .putExtra(Constants.CUR_POSTS_ID, "" + mTagsBeanList.get(position).getId())
                        .putExtra(Constants.CATEGORY_OR_TAG, Constants.IS_TAG)
                        .putExtra(Constants.CUR_POSTS_COUNT, "" + mTagsBeanList.get(position).getPost_count())
                        .setClass(context, CurPostsListActivity.class);
                context.startActivity(intent);
            }
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return mTagsBeanList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        ((TextView) view).setTextColor(themeColor);
    }
}
