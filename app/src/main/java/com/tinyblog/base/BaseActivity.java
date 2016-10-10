package com.tinyblog.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tinyblog.interfaces.UIInterface;

/**
 * @author xiarui
 * @date 2016/10/10 9:18
 * @desc Activity 的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,UIInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到布局文件
        setContentView(getLayoutId());

        //初始化View
        initView();

        //初始化界面数据
        initData();

        //绑定监听器与适配器
        initEvens();

    }

    /**
     * 处理点击事件
     * @param v 点击的View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                processClick(v);
                break;
        }
    }

    /**
     * 显示一个Toast
     *
     * @param msg 吐司内容
     */
    protected void baseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个Toast
     *
     * @param msgId 吐司内容
     */
    protected void baseToast(int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }
}
