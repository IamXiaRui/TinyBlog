package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lid.lib.LabelImageView;
import com.tinyblog.R;
import com.tinyblog.bean.NewsListRootBean;
import com.tinyblog.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/1/4 20:50
 * @desc 动态页列表适配器
 * @remark 1.0
 */

public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsListRootBean.PostsBean> mNewsList = new ArrayList<>();

    public NewsListAdapter(Context mContext, List<NewsListRootBean.PostsBean> mNewsList) {
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
        //最新文章
        NewsListRootBean.PostsBean postsBean = mNewsList.get(position);
        //设置 Banner　图片
        if (postsBean.getAttachments().size() > 0) {
            String itemBannerUrl = postsBean.getAttachments().get(0).getUrl();
            Glide.with(mContext).load(itemBannerUrl).into(viewHolder.itemBannerImage);
        } else {
            Glide.with(mContext).load(R.drawable.pic_placeholder_image).into(viewHolder.itemBannerImage);
        }
        viewHolder.itemBannerImage.setLabelText(postsBean.getCategories().get(0).getTitle());
        viewHolder.itemTitleText.setText(postsBean.getTitle());
        viewHolder.itemExcerptText.setText(AppUtil.fromHtml(postsBean.getExcerpt()).toString());
        //阅读数 喜欢数 评论数
        viewHolder.itemReadNumText.setText(postsBean.getCustom_fields().getViews().get(0));
        if (postsBean.getCustom_fields().getKratos_love() != null) {
            viewHolder.itemLikeNumText.setText(postsBean.getCustom_fields().getKratos_love().get(0));
        }
        if (postsBean.getCustom_fields().getSpecs_zan() != null) {
            viewHolder.itemLikeNumText.setText(postsBean.getCustom_fields().getSpecs_zan().get(0));
        }
        viewHolder.itemCommentNumText.setText(String.valueOf(postsBean.getComment_count()));

        return convertView;
    }

    /**
     * @author xiarui
     * @date 2017/1/5 11:04
     * @desc 动态页 列表 ViewHolder 类
     * @remark 1.0
     */
    private static class ViewHolder {
        LabelImageView itemBannerImage;
        TextView itemTitleText;
        TextView itemExcerptText;
        TextView itemReadNumText;
        TextView itemLikeNumText;
        TextView itemCommentNumText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemBannerImage = (LabelImageView) convertView.findViewById(R.id.liv_news_list_item_banner);
            itemTitleText = (TextView) convertView.findViewById(R.id.tv_news_list_item_title);
            itemExcerptText = (TextView) convertView.findViewById(R.id.tv_news_list_item_excerpt);
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
