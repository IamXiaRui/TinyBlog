package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tinyblog.R;
import com.tinyblog.bean.GithubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/7 21:23
 * @description
 * @remark
 */

public class GithubListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GithubBean> mGithubBeanList = new ArrayList<>();

    public GithubListAdapter(Context mContext, List<GithubBean> mGithubBeanList) {
        this.mContext = mContext;
        this.mGithubBeanList = mGithubBeanList;
    }

    @Override
    public int getCount() {
        return mGithubBeanList.isEmpty() ? 0 : mGithubBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGithubBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_github_list, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        Glide.with(mContext).load(mGithubBeanList.get(position).getAvatar()).into(viewHolder.itemGithubImage);
        viewHolder.itemNameText.setText(mGithubBeanList.get(position).getOwner() + "/" + mGithubBeanList.get(position).getRepo());
        viewHolder.itemStartText.setText(mGithubBeanList.get(position).getStars());
        viewHolder.itemDescText.setText(mGithubBeanList.get(position).getDesc());
        return convertView;
    }

    private static class ViewHolder {
        ImageView itemGithubImage;
        TextView itemNameText;
        TextView itemStartText;
        TextView itemDescText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemGithubImage = (ImageView) convertView.findViewById(R.id.iv_item_github);
            itemNameText = (TextView) convertView.findViewById(R.id.tv_item_github_name);
            itemStartText = (TextView) convertView.findViewById(R.id.tv_item_github_star);
            itemDescText = (TextView) convertView.findViewById(R.id.tv_item_github_desc);
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
