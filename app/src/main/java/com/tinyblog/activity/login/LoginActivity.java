package com.tinyblog.activity.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tinyblog.R;
import com.tinyblog.activity.main.MainActivity;
import com.tinyblog.base.BaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author xiarui
 * @date 2016/10/10 15:51
 * @desc 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mLoginButton;
    private ImageButton mTipsIButton;
    private RelativeLayout mLogoRLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mLoginButton = (Button) findViewById(R.id.btn_login_login);
        mTipsIButton = (ImageButton) findViewById(R.id.ib_login_tips);
        mLogoRLayout = (RelativeLayout) findViewById(R.id.rl_login_logo);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvents() {
        mLoginButton.setOnClickListener(this);
        mTipsIButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                startActivity(new Intent(this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, mLogoRLayout, "login").toBundle());
                break;
            case R.id.ib_login_tips:
                showTipsDialog();
                break;
            default:
                break;
        }
    }

    private void showTipsDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("小提示")
                .setContentText("这里的注册并非提供本博客的写入权限，只提供读取和评论权限。")
                .setConfirmText("我知道了")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }
}
