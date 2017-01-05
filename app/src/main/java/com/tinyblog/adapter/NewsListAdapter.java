package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tinyblog.R;
import com.tinyblog.bean.NewsListBean;

import java.util.ArrayList;

/**
 * @author xiarui
 * @date 2017/1/4 20:50
 * @desc 动态页列表适配器
 * @remark 1.0
 */

public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NewsListBean> mNewsList = new ArrayList<>();

    public NewsListAdapter(Context mContext, ArrayList<NewsListBean> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_news_list, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        Glide.with(mContext).load(mNewsList.get(position).getImageUrl()).into(viewHolder.itemBannerImage);
        viewHolder.itemTitleText.setText(mNewsList.get(position).getTitle());
        viewHolder.itemBriefText.setText(mNewsList.get(position).getBrief());
        viewHolder.itemReadNumText.setText(mNewsList.get(position).getReadNum());
        viewHolder.itemLikeNumText.setText(mNewsList.get(position).getLikeNum());
        viewHolder.itemCommentNumText.setText(mNewsList.get(position).getCommentNum());

        return convertView;
    }

    /**
     * @author xiarui
     * @date 2017/1/5 11:04
     * @desc 动态页 列表 ViewHolder 类
     * @remark 1.0
     */
    private static class ViewHolder {
        ImageView itemBannerImage;
        TextView itemTitleText;
        TextView itemBriefText;
        TextView itemReadNumText;
        TextView itemLikeNumText;
        TextView itemCommentNumText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemBannerImage = (ImageView) convertView.findViewById(R.id.iv_news_list_item_banner);
            itemTitleText = (TextView) convertView.findViewById(R.id.tv_news_list_item_title);
            itemBriefText = (TextView) convertView.findViewById(R.id.tv_news_list_item_brief);
            itemReadNumText = (TextView) convertView.findViewById(R.id.tv_news_list_icon_read);
            itemLikeNumText = (TextView) convertView.findViewById(R.id.tv_news_list_icon_like);
            itemCommentNumText = (TextView) convertView.findViewById(R.id.tv_news_list_icon_comment);
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
