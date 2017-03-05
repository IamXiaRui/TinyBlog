package com.tinyblog.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.adapter.PostCommentAdapter;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.CommentResponseBean;
import com.tinyblog.bean.PostCommentBean;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/1/14 19:19
 * @desc 文章评论页
 * @remark
 */
public class PostCommentActivity extends BaseActivity {
    private ListView mCommentLView;
    private Toolbar mCommentTBar;
    private EditText mCommentInputEText;
    private String mPostThreadId;  //文章评论Id
    private ImageView mCommentSendImage;
    //下拉刷新
    private SwipeRefreshLayout mCommentSRLayout;
    //停止刷新操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.REFRESH_SUCCESS) {
                if (mCommentSRLayout.isRefreshing()) {
                    mCommentSRLayout.setRefreshing(false);//设置不刷新
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_post_comment;
    }

    @Override
    public void initView() {
        mCommentTBar = (Toolbar) findViewById(R.id.tb_post_comment);
        setSupportActionBar(mCommentTBar);
        //隐藏默认标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //添加返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCommentSRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_post_comment);
        mCommentSRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mCommentLView = (ListView) findViewById(R.id.lv_post_comment);
        mCommentInputEText = (EditText) findViewById(R.id.et_comment_input);
        mCommentSendImage = (ImageView) findViewById(R.id.iv_comment_send);
    }

    @Override
    public void initData() {
        mCommentTBar.setTitle("评论");
        mCommentTBar.setSubtitle(getIntent().getStringExtra(Constants.POST_COMMNET_COUNT) + " 条评论");
        //开启自动刷新
        mCommentSRLayout.post(new Runnable() {
            @Override
            public void run() {
                mCommentSRLayout.setRefreshing(true);
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
                .url(Url.GET_POST_COMMENT + getIntent().getStringExtra(Constants.POST_DETAILS_ID))
                .build()
                .execute(new PostCommentCallBack());
    }

    private class PostCommentCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mCommentSRLayout.setRefreshing(false);  //设置不刷新
            if (!NetworkUtils.isConnected()) {
                showBaseToast("获取失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            PostCommentBean postCommentBean = new Gson().fromJson(response, PostCommentBean.class);
            if (postCommentBean.getStatus().equals("ok")) {
                mHandler.sendEmptyMessage(Constants.REFRESH_SUCCESS);
                mPostThreadId = postCommentBean.getPost().getCustom_fields().getDuoshuo_thread_id().get(0);
                //获得数据后更新UI
                List<PostCommentBean.PostBean.CommentsBean> commentsBeanList = postCommentBean.getPost().getComments();
                if (commentsBeanList.isEmpty()) {
                    showBaseToast("抱歉，暂无评论");
                }
                mCommentTBar.setSubtitle(commentsBeanList.size() + " 条评论");
                mCommentLView.setAdapter(new PostCommentAdapter(PostCommentActivity.this, commentsBeanList));
            } else {
                showBaseToast("数据异常，请重新刷新");
            }
        }
    }

    @Override
    public void initEvents() {
        //下拉刷新监听
        mCommentSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loaderNetWorkData();
                    }
                }).start();
            }
        });
        mCommentLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSoftKeyboard();     //显示软键盘
            }
        });

        //处理EditText输入法焦点问题
        mCommentLView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(); //隐藏软键盘
                return false;
            }
        });

        mCommentSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mCommentInputEText.getText().toString();
                if (msg.equals("") || msg == null) {
                    showBaseToast("请输入评论内容");
                } else {
                    hideSoftKeyboard(); //隐藏软键盘
                    mCommentInputEText.setText("");
                    mCommentInputEText.setHint("写下你的评论");
                    showBaseToast("正在发送...");
                    sendCommentWithNet(mPostThreadId, msg);
                }
            }
        });

    }

    private void sendCommentWithNet(String threadId, String msg) {
        OkHttpUtils
                .post()
                .url(Url.SEND_COMMENT)
                .addParams("short_name", "iamxiarui")
                .addParams("secret", "733356696f6b8ccb1c2d9afd147bd330")
                .addParams("thread_id", threadId)
                .addParams("message", msg)
                .addParams("author_name", "我")
                .build()
                .execute(new SendCommentCallBack());
    }

    private class SendCommentCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("评论失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            CommentResponseBean commentResponseBean = new Gson().fromJson(response, CommentResponseBean.class);
            if (commentResponseBean.getCode() == 0) {
                mCommentSRLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showBaseToast("评论成功！正在刷新...");
                        loaderNetWorkData();
                    }
                }, 8000);
            } else {
                showBaseToast("评论失败");
            }
        }
    }

    /**
     * 显示软键盘
     */
    private void showSoftKeyboard() {
        mCommentInputEText.setFocusable(true);
        mCommentInputEText.setFocusableInTouchMode(true);
        mCommentInputEText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        mCommentLView.setFocusable(true);
        mCommentLView.setFocusableInTouchMode(true);
        mCommentLView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCommentInputEText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cur_posts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_refresh:
                mCommentSRLayout.setRefreshing(true);
                loaderNetWorkData();
                return true;
        }
        return true;
    }
}
