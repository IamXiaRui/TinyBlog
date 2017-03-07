package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tinyblog.R;
import com.tinyblog.bean.ZhihuPostBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/7 12:47
 * @description 知乎文章列表适配器
 * @remark
 */

public class ZhihuListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ZhihuPostBean> mZhihuBeanList = new ArrayList<>();

    public ZhihuListAdapter(Context mContext, List<ZhihuPostBean> mZhihuBeanList) {
        this.mContext = mContext;
        this.mZhihuBeanList = mZhihuBeanList;
    }

    @Override
    public int getCount() {
        return mZhihuBeanList.isEmpty() ? 0 : mZhihuBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mZhihuBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_zhihu_list, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        Glide.with(mContext).load(mZhihuBeanList.get(position).getImageUrl()).into(viewHolder.itemPostImage);
        viewHolder.itemPostTitleText.setText(mZhihuBeanList.get(position).getTitle());
        return convertView;
    }

    private static class ViewHolder {
        ImageView itemPostImage;
        TextView itemPostTitleText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemPostImage = (ImageView) convertView.findViewById(R.id.iv_item_zhihu);
            itemPostTitleText = (TextView) convertView.findViewById(R.id.tv_item_zhihu);
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
