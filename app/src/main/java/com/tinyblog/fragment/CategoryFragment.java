package com.tinyblog.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.google.gson.Gson;
import com.squareup.picasso.RequestCreator;
import com.tinyblog.R;
import com.tinyblog.activity.CurPostsListActivity;
import com.tinyblog.activity.TagsCloudActivity;
import com.tinyblog.base.BaseFragment;
import com.tinyblog.bean.CategoryBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import okhttp3.Call;

/**
 * @author xiarui
 * @date 2016/10/11 11:42
 * @desc 分类页
 */
public class CategoryFragment extends BaseFragment {

    private TextView mToolbarText;
    private ImageButton mTagsIButton;
    private MaterialListView mCategoryLView;
    private List<CategoryBean.CategoriesBean> categoriesList;
    //图片集合
    private int[] cardBackPicList = new int[]{R.drawable.heng_1, R.drawable.heng_2, R.drawable.heng_3, R.drawable.heng_4,
            R.drawable.heng_5, R.drawable.heng_6, R.drawable.heng_7, R.drawable.heng_8,
            R.drawable.heng_9, R.drawable.heng_10};
    //下拉刷新
    private SwipeRefreshLayout mCategorySRLayout;
    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                if (mCategorySRLayout.isRefreshing()) {
                    mCategorySRLayout.setRefreshing(false);//设置不刷新
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void initView() {
        mToolbarText = (TextView) findViewById(R.id.tv_find_header);
        mToolbarText.setText("分类");
        mTagsIButton = (ImageButton) findViewById(R.id.ib_find_ask);
        mTagsIButton.setImageResource(R.drawable.svg_tag);
        mCategoryLView = (MaterialListView) findViewById(R.id.mlv_category_list);
        mCategoryLView.setItemAnimator(new OvershootInLeftAnimator());
        mCategoryLView.getItemAnimator().setAddDuration(400);
        mCategoryLView.getItemAnimator().setRemoveDuration(400);
        mCategorySRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_category);
        mCategorySRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initData() {
        //开启自动刷新
        mCategorySRLayout.post(new Runnable() {
            @Override
            public void run() {
                mCategorySRLayout.setRefreshing(true);
                //加载网络数据
                loaderNetWorkData();
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void loaderNetWorkData() {
        OkHttpUtils
                .get()
                .url(Url.GET_CATEGORY_INDEX)
                .build()
                .execute(new CategoryCallBack());
    }

    private class CategoryCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mCategorySRLayout.setRefreshing(false);//设置不刷新
            if (!NetworkUtils.isConnected()) {
                showBaseToast("获取失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            CategoryBean categoryBean = new Gson().fromJson(response, CategoryBean.class);
            if (categoryBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                categoriesList = categoryBean.getCategories();
                List<Card> cardList = new ArrayList<>();
                for (int i = 0; i < categoriesList.size(); i++) {
                    cardList.add(getCurrentCard(i));
                }
                mCategoryLView.getAdapter().addAll(cardList);
            }
        }

        private Card getCurrentCard(final int i) {
            final CardProvider provider = new Card.Builder(getContext())
                    .setTag("BIG_IMAGE_BUTTONS_CARD")
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_image_with_buttons_card)
                    .setTitle(categoriesList.get(i).getTitle())
                    .setTitleColor(Color.WHITE)
                    .setTitleGravity(Gravity.CENTER)
                    .setDescription(categoriesList.get(i).getDescription())
                    .setDrawable(cardBackPicList[new Random().nextInt(10)])
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull final RequestCreator requestCreator) {
                            requestCreator.resize(720, 216)
                                    .centerCrop();
                        }
                    })
                    .addAction(R.id.left_text_button, new TextViewAction(getContext())
                            .setText("" + categoriesList.get(i).getPost_count() + " 篇文章")
                            .setTextResourceColor(R.color.black_button));
            return provider.endConfig().build();
        }
    }


    @Override
    public void initEvents() {
        //下拉刷新监听
        mCategorySRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mCategoryLView.getAdapter().clearAll();
                        loaderNetWorkData();
                    }
                }).start();
            }
        });
        mTagsIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TagsCloudActivity.class));
            }
        });
        mCategoryLView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(@NonNull Card card, int position) {
                CategoryBean.CategoriesBean itemCategoryBean = categoriesList.get(position);
                Intent intent = new Intent()
                        .putExtra(Constants.CUR_POSTS_TITLE, itemCategoryBean.getTitle())
                        .putExtra(Constants.CUR_POSTS_ID, "" + itemCategoryBean.getId())
                        .putExtra(Constants.CUR_POSTS_COUNT,""+""+itemCategoryBean.getPost_count())
                        .putExtra(Constants.CATEGORY_OR_TAG,Constants.IS_CATEGORY)
                        .setClass(getContext(), CurPostsListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
                showBaseToast("长按是没有用的——鲁迅");
            }
        });
    }

}
