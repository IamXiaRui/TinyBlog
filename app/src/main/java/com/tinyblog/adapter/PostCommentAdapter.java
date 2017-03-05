package com.tinyblog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tinyblog.R;
import com.tinyblog.bean.PostCommentBean;
import com.tinyblog.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/3 12:49
 * @description 评论列表适配器
 * @remark
 */

public class PostCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<PostCommentBean.PostBean.CommentsBean> mCommentsBeanList = new ArrayList<>();
    private static int PARENT_TYPE = 0;  //父级评论
    private static int CHILD_TYPE = 1;  //子级评论
    //头像集合
    private int[] headerIconList = new int[]{R.drawable.svg_header_icon, R.drawable.svg_header_icon2, R.drawable.svg_header_icon3, R.drawable.svg_header_icon4,
            R.drawable.svg_header_icon5, R.drawable.svg_header_icon6, R.drawable.svg_header_icon7, R.drawable.svg_header_icon8};

    public PostCommentAdapter(Context mContext, List<PostCommentBean.PostBean.CommentsBean> mCommentsBeanList) {
        this.mContext = mContext;
        this.mCommentsBeanList = mCommentsBeanList;
    }

    @Override
    public int getCount() {
        return mCommentsBeanList.isEmpty() ? 0 : mCommentsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && mCommentsBeanList.get(position).getParent() == mCommentsBeanList.get(position - 1).getId()) {
            return CHILD_TYPE;
        } else {
            return PARENT_TYPE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostCommentBean.PostBean.CommentsBean commentsBean = mCommentsBeanList.get(position);
        String replyName = commentsBean.getName();
        replyName = checkNameString(replyName);
        if (getItemViewType(position) == PARENT_TYPE) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_comment_parent, null);
            }
            // 得到一个ViewHolder
            ParentViewHolder parentHolder = ParentViewHolder.getViewHolder(convertView);
            parentHolder.parentNameText.setText(replyName);
            if(replyName.equals("我")){
                parentHolder.parentIconImage.setImageResource(R.drawable.icon_header_me);
            }else{
                parentHolder.parentIconImage.setImageResource(headerIconList[position % 8]);
            }
            parentHolder.parentTimeText.setText(commentsBean.getDate());
            parentHolder.parentContentText.setText(AppUtil.fromHtml(commentsBean.getContent()));
        } else if (getItemViewType(position) == CHILD_TYPE) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_comment_child, null);
            }
            // 得到一个ViewHolder
            ChildViewHolder childHolder = ChildViewHolder.getViewHolder(convertView);
            childHolder.childNameText.setText(replyName);
            if(replyName.equals("我")){
                childHolder.childIconImage.setImageResource(R.drawable.icon_header_me);
            }else{
                childHolder.childIconImage.setImageResource(headerIconList[position % 8]);
            }
            childHolder.childTimeText.setText(commentsBean.getDate());
            String responseName = mCommentsBeanList.get(position - 1).getName();
            responseName = checkNameString(responseName);
            childHolder.childContentText.setText(Html.fromHtml("<font color='#42A5F5'>@" + responseName + " : </font>"));
            childHolder.childContentText.append(AppUtil.fromHtml(commentsBean.getContent()));
        }
        return convertView;
    }

    /**
     * 检查是不是自己回复
     *
     * @param nameStr 名字
     * @return 处理后的名字
     */
    @NonNull
    private String checkNameString(String nameStr) {
        if (nameStr.equals("半夏的大大卷") || nameStr.equals("iamxiarui")) {
            nameStr = "我";
        }
        return nameStr;
    }

    /**
     * @author xiarui
     * @date 2017/1/5 11:04
     * @desc 父级评论 ViewHolder 类
     * @remark 1.0
     */
    private static class ParentViewHolder {
        ImageView parentIconImage;
        TextView parentNameText;
        TextView parentTimeText;
        TextView parentContentText;

        // 构造函数中就初始化View
        ParentViewHolder(View convertView) {
            parentIconImage = (ImageView) convertView.findViewById(R.id.iv_comment_parent_header);
            parentNameText = (TextView) convertView.findViewById(R.id.tv_comment_parent_name);
            parentTimeText = (TextView) convertView.findViewById(R.id.tv_comment_parent_time);
            parentContentText = (TextView) convertView.findViewById(R.id.tv_comment_parent_content);
        }

        // 得到一个ViewHolder
        static ParentViewHolder getViewHolder(View convertView) {
            ParentViewHolder viewHolder = (ParentViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ParentViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }

    /**
     * @author xiarui
     * @date 2017/1/5 11:04
     * @desc 子级评论 ViewHolder 类
     * @remark 1.0
     */
    private static class ChildViewHolder {
        ImageView childIconImage;
        TextView childNameText;
        TextView childTimeText;
        TextView childContentText;

        // 构造函数中就初始化View
        ChildViewHolder(View convertView) {
            childIconImage = (ImageView) convertView.findViewById(R.id.iv_comment_child_header);
            childNameText = (TextView) convertView.findViewById(R.id.tv_comment_child_name);
            childTimeText = (TextView) convertView.findViewById(R.id.tv_comment_child_time);
            childContentText = (TextView) convertView.findViewById(R.id.tv_comment_child_content);
        }

        // 得到一个ViewHolder
        static ChildViewHolder getViewHolder(View convertView) {
            ChildViewHolder viewHolder = (ChildViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ChildViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
