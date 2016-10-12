package com.tinyblog.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tinyblog.activity.login.LoginActivity;

/**
 * @author xiarui
 * @date 2016/10/10 9:34
 * @desc 启动闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不设置布局文件 唯一作用就是跳转到主页面
        this.startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}