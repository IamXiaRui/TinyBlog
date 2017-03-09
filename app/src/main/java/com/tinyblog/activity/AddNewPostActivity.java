package com.tinyblog.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.TextViewToolbar;
import com.github.mr5.icarus.Toolbar;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.FontScaleButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Html;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.popover.FontScalePopoverImpl;
import com.github.mr5.icarus.popover.HtmlPopoverImpl;
import com.github.mr5.icarus.popover.LinkPopoverImpl;
import com.google.gson.Gson;
import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;
import com.tinyblog.bean.IssuePostBean;
import com.tinyblog.db.NewPostModel;
import com.tinyblog.sys.Constants;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2017/1/14 22:15
 * @desc 新建文章页
 * @remark
 */

public class AddNewPostActivity extends BaseActivity {
    private android.support.v7.widget.Toolbar mAddTBar;
    private WebView mAddWView;
    private Icarus mAddIcarus;
    private EditText mAddTitleEText;
    private String mCategoryStr, mTagStr;
    private TextViewToolbar mAddTVTBar = new TextViewToolbar();
    private MaterialDialog issuePostDialog;
    private String mPostContentText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_post;
    }

    @Override
    public void initView() {
        mAddTBar = (android.support.v7.widget.Toolbar) findViewById(R.id.tb_add_post);
        setSupportActionBar(mAddTBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddTBar.setTitle("新建");

        mAddWView = (WebView) findViewById(R.id.wv_add_content);
        mAddTitleEText = (EditText) findViewById(R.id.et_add_title);
    }

    @Override
    public void initData() {
        Options options = new Options();
        options.setPlaceholder("输入文章内容...");
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height", "data-non-image"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));
        mAddIcarus = new Icarus(mAddTVTBar, options, mAddWView);
        prepareToolbar(mAddTVTBar, mAddIcarus);
        mAddIcarus.loadCSS("file:///android_asset/editor.css");
        mAddIcarus.loadJs("file:///android_asset/test.js");
        mAddIcarus.render();

        if ((getIntent().getStringExtra(Constants.IS_FROM_DRAFT)).equals("true")) {
            mAddTitleEText.setText(getIntent().getStringExtra(Constants.DRAFT_TITLE));
            mAddIcarus.setContent(getIntent().getStringExtra(Constants.DRAFT_CONTENT));
        }
    }

    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_HR, R.id.button_hr);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_INDENT, R.id.button_indent);
        generalButtons.put(Button.NAME_OUTDENT, R.id.button_outdent);
        generalButtons.put(Button.NAME_CODE, R.id.button_math);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }
        TextView linkButtonTextView = (TextView) findViewById(R.id.button_link);
        linkButtonTextView.setTypeface(iconfont);
        TextViewButton linkButton = new TextViewButton(linkButtonTextView, icarus);
        linkButton.setName(Button.NAME_LINK);
        linkButton.setPopover(new LinkPopoverImpl(linkButtonTextView, icarus));
        toolbar.addButton(linkButton);

        TextView imageButtonTextView = (TextView) findViewById(R.id.button_image);
        imageButtonTextView.setTypeface(iconfont);
        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
        imageButton.setName(Button.NAME_HTML);
        imageButton.setPopover(new HtmlPopoverImpl(imageButtonTextView, icarus));
        toolbar.addButton(imageButton);

        TextView htmlButtonTextView = (TextView) findViewById(R.id.button_html5);
        htmlButtonTextView.setTypeface(iconfont);
        TextViewButton htmlButton = new TextViewButton(htmlButtonTextView, icarus);
        htmlButton.setName(Button.NAME_HTML);
        htmlButton.setPopover(new HtmlPopoverImpl(htmlButtonTextView, icarus));
        toolbar.addButton(htmlButton);

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);
        return toolbar;
    }

    @Override
    public void initEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_save:
                confirmSavePost();
                return true;
            case R.id.menu_item_issue:
                if (mAddTitleEText.getText().toString() == null || mAddTitleEText.getText().toString().equals("")) {
                    showBaseToast("标题不能为空");
                } else {
                    //第一步 选择分类
                    inputCurPostCategory();
                }
                return true;
        }
        return true;
    }

    /**
     * 确定保存文章
     */
    private void confirmSavePost() {
        mAddIcarus.getContent(new Callback() {
            @Override
            public void run(String s) {
                Html html = new Gson().fromJson(s, Html.class);
                mPostContentText = html.getContent();
            }
        });
        new MaterialDialog.Builder(AddNewPostActivity.this)
                .title("提示")
                .content("是否保存当前文章")
                .positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        //存入数据库
                        NewPostModel newPostModel = new NewPostModel();
                        newPostModel.title = mAddTitleEText.getText().toString();
                        newPostModel.content = mPostContentText;
                        newPostModel.time = getCurrentTime();
                        newPostModel.save();
                        showSaveSuccess();
                    }
                })
                .negativeText("取消")
                .show();
    }

    /**
     * 保存成功
     */
    private void showSaveSuccess() {
        new MaterialDialog.Builder(AddNewPostActivity.this)
                .title("提示")
                .content("保存成功！是否查看草稿")
                .positiveText("查看")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        //跳转到草稿箱
                        startActivity(new Intent(AddNewPostActivity.this, DraftActivity.class));
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        finish();
                    }
                })
                .show();
    }

    /**
     * 第一步 选择分类
     */
    private void inputCurPostCategory() {
        new MaterialDialog.Builder(AddNewPostActivity.this)
                .title("选择文章分类")
                .content("目前只支持选择已有文章分类")
                .items(R.array.categories)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        mCategoryStr = charSequence.toString();
                        return true;
                    }
                })
                .positiveText("确定选择")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        //第二步 填写标签
                        inputCurPostTag();
                    }
                })
                .negativeText("取消")
                .show();
    }

    /**
     * 第二步 填写标签
     */
    private void inputCurPostTag() {
        new MaterialDialog.Builder(AddNewPostActivity.this)
                .title("填写标签")
                .content("多个标签之间需用英文“ , ”隔开")
                .input("请输入标签", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        mTagStr = charSequence.toString();
                        //最后是否确认发布
                        isOrNotIssuePost();
                    }
                }).show();
    }

    /**
     * 是否确认发布
     */
    private void isOrNotIssuePost() {
        new MaterialDialog.Builder(AddNewPostActivity.this)
                .title("发表前确认")
                .content("当前文章分类：" + mCategoryStr + "，当前文章标签：" + mTagStr)
                .positiveText("确认发表")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        //发布文章网络请求
                        issuePostToNet(mAddTitleEText.getText().toString(), mCategoryStr, mTagStr);
                        issuePostDialog = new MaterialDialog.Builder(AddNewPostActivity.this)
                                .title("正在发表")
                                .content("请等待...")
                                .progress(true, 0)
                                .progressIndeterminateStyle(true)
                                .show();
                    }
                })
                .negativeText("取消发布")
                .show();
    }

    /**
     * 发布文章
     *
     * @param titleStr    文章标题
     * @param categoryStr 文章分类
     * @param tagStr      文章标签
     */
    public void issuePostToNet(String titleStr, String categoryStr, String tagStr) {
        OkHttpUtils
                .get()
                .url(Url.CREATE_POST_ISSUE + "&title=" + titleStr + "&categories=" + handleCategoryStr(categoryStr) + "&tags=" + tagStr)
                .build()
                .execute(new IssuePostCallBack());
    }

    private class IssuePostCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            issuePostDialog.dismiss();
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            } else {
                showBaseToast("抱歉，暂未对您开放写权限");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            final IssuePostBean issuePostBean = new Gson().fromJson(response, IssuePostBean.class);
            if (issuePostBean.getStatus().equals("ok")) {
                issuePostDialog.dismiss();
                new MaterialDialog.Builder(AddNewPostActivity.this)
                        .title("发表成功！")
                        .content("是否立即查看新文章")
                        .positiveText("查看")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                //跳转新文章页面
                                startActivity(new Intent(AddNewPostActivity.this, PostDetailsActivity.class).putExtra(Constants.POST_DETAILS_ID, String.valueOf(issuePostBean.getPost().getId())));
                            }
                        })
                        .negativeText("取消")
                        .show();
            } else {
                issuePostDialog.dismiss();
                showBaseToast("发表失败");
            }
        }
    }

    /**
     * 处理一下分类
     *
     * @param categoryStr 分类字符串
     * @return 分类
     */
    private String handleCategoryStr(String categoryStr) {
        if (categoryStr.equals("Android坑")) {
            categoryStr = "android";
        } else if (categoryStr.equals("Java坑")) {
            categoryStr = "java";
        } else if (categoryStr.equals("可圈可点")) {
            categoryStr = "%e5%8f%af%e5%9c%88%e5%8f%af%e7%82%b9";
        } else if (categoryStr.equals("随笔")) {
            categoryStr = "%e9%9a%8f%e7%ac%94";
        } else {
            showBaseToast("Url异常");
        }
        return categoryStr;
    }

    public void onDestroy() {
        mAddWView.destroy();
        super.onDestroy();
    }

    /**
     * 获得当前系统时间
     *
     * @return 系统时间
     */
    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

}
