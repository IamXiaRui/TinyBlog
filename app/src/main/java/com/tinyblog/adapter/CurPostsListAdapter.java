package com.tinyblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tinyblog.R;
import com.tinyblog.bean.PostListBean;
import com.tinyblog.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/2/23 17:13
 * @desc 选中标签文章列表适配器
 * @remark 1.0
 */

public class CurPostsListAdapter extends RecyclerView.Adapter<CurPostsListAdapter.CurPostsListViewHolder> {

    private Context context;
    private List<PostListBean.PostsBean> postsBeanList = new ArrayList<>();

    public CurPostsListAdapter(Context context, List<PostListBean.PostsBean> postsBeanList) {
        this.context = context;
        this.postsBeanList = postsBeanList;
    }

    @Override
    public CurPostsListAdapter.CurPostsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurPostsListViewHolder(View.inflate(context, R.layout.item_cur_posts_list, null));
    }

    @Override
    public int getItemCount() {
        return postsBeanList.isEmpty() ? 0 : postsBeanList.size();
    }

    @Override
    public void onBindViewHolder(CurPostsListAdapter.CurPostsListViewHolder holder, int position) {
        PostListBean.PostsBean postBean = postsBeanList.get(position);
        //设置 Banner　图片
        if (postBean.getAttachments().size() > 0) {
            String itemBannerUrl = postBean.getAttachments().get(0).getUrl();
            Glide.with(context).load(itemBannerUrl).into(holder.itemPostImage);
        } else {
            Glide.with(context).load(R.drawable.pic_placeholder_image).into(holder.itemPostImage);
        }
        holder.itemPostTitleText.setText(postBean.getTitle());
        holder.itemPostExcerptText.setText(AppUtil.fromHtml(postBean.getExcerpt()));
        holder.itemPostTimeText.setText(postBean.getDate());
        //阅读
        holder.itemPostReadText.setText(postBean.getCustom_fields().getViews().get(0));
    }

    class CurPostsListViewHolder extends RecyclerView.ViewHolder {
        ImageView itemPostImage;
        TextView itemPostTitleText;
        TextView itemPostExcerptText;
        TextView itemPostTimeText;
        TextView itemPostReadText;

        public CurPostsListViewHolder(View itemView) {
            super(itemView);
            //初始化View
            initView(itemView);
            //点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击 " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "如果你长按此处，那将一无所获——拿破仑", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        /**
         * 初始化View
         * @param itemView itemView
         */
        private void initView(View itemView) {
            itemPostImage = (ImageView) itemView.findViewById(R.id.iv_cur_post_list_item);
            itemPostTitleText = (TextView) itemView.findViewById(R.id.tv_cur_post_list_item_title);
            itemPostExcerptText = (TextView) itemView.findViewById(R.id.tv_cur_post_list_item_excerpt);
            itemPostTimeText = (TextView) itemView.findViewById(R.id.tv_cur_post_list_item_time);
            itemPostReadText = (TextView) itemView.findViewById(R.id.tv_cur_post_list_item_read);
        }
    }
}
