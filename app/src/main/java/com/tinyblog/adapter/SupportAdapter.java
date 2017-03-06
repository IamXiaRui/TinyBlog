package com.tinyblog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tinyblog.R;
import com.tinyblog.bean.SupportBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/6 22:56
 * @description
 * @remark
 */

public class SupportAdapter extends BaseAdapter {
    private Context mContext;
    private List<SupportBean.LibsBean> mLibsBeanList = new ArrayList<>();

    public SupportAdapter(Context mContext, List<SupportBean.LibsBean> libsBeanList) {
        this.mContext = mContext;
        this.mLibsBeanList = libsBeanList;
    }

    @Override
    public int getCount() {
        return mLibsBeanList.isEmpty() ? 0 : mLibsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLibsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_support, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        viewHolder.itemNameText.setText(mLibsBeanList.get(position).getName());
        viewHolder.itemAuthorText.setText(mLibsBeanList.get(position).getAuthor());
        viewHolder.itemDescText.setText(mLibsBeanList.get(position).getDesc());
        return convertView;
    }

    private static class ViewHolder {
        TextView itemNameText;
        TextView itemAuthorText;
        TextView itemDescText;

        // 构造函数中就初始化View
        ViewHolder(View convertView) {
            itemNameText = (TextView) convertView.findViewById(R.id.tv_item_lib_name);
            itemAuthorText = (TextView) convertView.findViewById(R.id.tv_item_lib_author);
            itemDescText = (TextView) convertView.findViewById(R.id.tv_item_lib_desc);
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
