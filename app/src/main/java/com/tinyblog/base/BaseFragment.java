package com.tinyblog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tinyblog.interfaces.UIInterface;


/**
 * 基类Fragment
 */
public abstract class BaseFragment extends Fragment implements UIInterface {

    private View baseView;
    private Toast mBaseToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseView = View.inflate(getActivity(), getLayoutId(), null);
        //初始化View
        initView();

        //初始化界面数据
        initData();

        //绑定监听器与适配器
        initEvents();

        return baseView;
    }

    /**
     * 返回View引用
     *
     * @param viewId View的Id
     * @return 返回View引用
     */
    protected View findViewById(int viewId) {
        return baseView.findViewById(viewId);
    }

    /**
     * 显示Toast
     *
     * @param str Toast内容
     */
    public void showBaseToast(String str) {
        if (mBaseToast == null) {
            mBaseToast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        } else {
            mBaseToast.setText(str);
        }
        mBaseToast.show();
    }
}
