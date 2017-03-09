package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tinyblog.R;
import com.tinyblog.bean.DraftBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/7 12:47
 * @description 知乎文章列表适配器
 * @remark
 */

public class DraftAdapter extends BaseAdapter {

    private Context mContext;
    private List<DraftBean> mDraftBeanList = new ArrayList<>();

    public DraftAdapter(Context mContext, List<DraftBean> mDraftBeanList) {
        this.mContext = mContext;
        this.mDraftBeanList = mDraftBeanList;
    }

    @Override
    public int getCount() {
        return mDraftBeanList.isEmpty() ? 0 : mDraftBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDraftBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_draft_list, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        viewHolder.itemTitleText.setText(mDraftBeanList.get(position).getTitle());
        viewHolder.itemContentText.setText(mDraftBeanList.get(position).getContent());
        viewHolder.itemTimeText.setText(mDraftBeanList.get(position).getTime());
        return convertView;
    }

    private static class ViewHolder {
        TextView itemTitleText;
        TextView itemContentText;
        TextView itemTimeText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemTitleText = (TextView) convertView.findViewById(R.id.tv_item_draft_title);
            itemContentText = (TextView) convertView.findViewById(R.id.tv_item_draft_content);
            itemTimeText = (TextView) convertView.findViewById(R.id.tv_item_draft_time);
        }

        // 得到一个ViewHolder
        static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
