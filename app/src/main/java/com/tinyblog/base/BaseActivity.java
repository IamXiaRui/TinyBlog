package com.tinyblog.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tinyblog.interfaces.UIInterface;

/**
 * @author xiarui
 * @date 2016/10/10 9:18
 * @desc Activity 的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements UIInterface {

    private Toast mBaseToast;

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
        initEvents();

    }

    /**
     * 显示Toast
     *
     * @param str Toast内容
     */
    public void showBaseToast(String str) {
        if (mBaseToast == null) {
            mBaseToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        } else {
            mBaseToast.setText(str);
        }
        mBaseToast.show();
    }
}
