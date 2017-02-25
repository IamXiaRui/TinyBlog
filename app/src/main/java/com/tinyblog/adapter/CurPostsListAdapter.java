package com.tinyblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class CurPostsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PostListBean.PostsBean> postsBeanList = new ArrayList<>();
    private LayoutInflater mInflater;
    private static final int TYPE_ITEM = 0;  //普通Item
    private static final int TYPE_FOOTER = 1;  //底部上拉加载
    private static int DEFAULT_STATUS = 1;      //默认情况
    public static final int LOADING_MORE = 1; //正在加载
    public static final int NO_NEW_DATA = 2;  //无新数据

    public CurPostsListAdapter(Context context, List<PostListBean.PostsBean> postsBeanList) {
        this.context = context;
        this.postsBeanList = postsBeanList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(View.inflate(context, R.layout.item_cur_posts_list, null));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(mInflater.inflate(R.layout.footer_cur_posts_list, parent, false));
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return postsBeanList.isEmpty() ? 0 : postsBeanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footer
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            PostListBean.PostsBean postBean = postsBeanList.get(position);
            //设置 Banner　图片
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (postBean.getAttachments().size() > 0) {
                String itemBannerUrl = postBean.getAttachments().get(0).getUrl();
                Glide.with(context).load(itemBannerUrl).into(itemViewHolder.itemPostImage);
            } else {
                Glide.with(context).load(R.drawable.pic_placeholder_image).into(itemViewHolder.itemPostImage);
            }
            itemViewHolder.itemPostTitleText.setText(postBean.getTitle());
            itemViewHolder.itemPostExcerptText.setText(AppUtil.fromHtml(postBean.getExcerpt()));
            itemViewHolder.itemPostTimeText.setText(postBean.getDate());
            //阅读
            itemViewHolder.itemPostReadText.setText(postBean.getCustom_fields().getViews().get(0));
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            changeLoadStatus(footerViewHolder, DEFAULT_STATUS);
        }
    }

    //添加所有数据
    public void addMoreItem(List<PostListBean.PostsBean> newDatas) {
        postsBeanList.addAll(newDatas);
        notifyDataSetChanged();
    }

    //改变状态
    public void changeLoadStatus(int loadStatus) {
        DEFAULT_STATUS = loadStatus;
        notifyDataSetChanged();
    }

    //改变状态
    public void changeLoadStatus(FooterViewHolder footerViewHolder, int loadStatus) {
        if (loadStatus == LOADING_MORE) {
            footerViewHolder.footerPBar.setVisibility(View.VISIBLE);
            footerViewHolder.footerText.setVisibility(View.INVISIBLE);
        } else if (loadStatus == NO_NEW_DATA) {
            footerViewHolder.footerPBar.setVisibility(View.INVISIBLE);
            footerViewHolder.footerText.setVisibility(View.VISIBLE);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemPostImage;
        TextView itemPostTitleText;
        TextView itemPostExcerptText;
        TextView itemPostTimeText;
        TextView itemPostReadText;

        public ItemViewHolder(View itemView) {
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

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar footerPBar;
        TextView footerText;

        public FooterViewHolder(View itemView) {
            super(itemView);
            footerPBar = (ProgressBar) itemView.findViewById(R.id.pb_footer_cur_posts_list);
            footerText = (TextView) itemView.findViewById(R.id.tv_footer_cur_posts_list);
        }
    }

}
