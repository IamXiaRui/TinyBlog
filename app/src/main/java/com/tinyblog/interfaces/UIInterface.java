package com.tinyblog.interfaces;

import android.view.View;

/**
 * UI接口
 */
public interface UIInterface {

    /**
     * 得到布局文件
     *
     * @return 布局文件Id
     */
    int getLayoutId();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 初始化界面数据
     */
    void initData();

    /**
     * 绑定监听器与适配器
     */
    void initEvens();

    /**
     * 点击事件
     *
     * @param v 点击的View
     */
    void processClick(View v);

}
