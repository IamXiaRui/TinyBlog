package com.tinyblog.ui.main;

import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.tinyblog.R;
import com.tinyblog.base.BaseActivity;

/**
 * @author xiarui
 * @date 2016/10/10 13:32
 * @desc 主页面
 */
public class MainActivity extends BaseActivity {

    private BottomNavigationBar mainBottomBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainBottomBar = (BottomNavigationBar) findViewById(R.id.bnb_main);
        mainBottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        mainBottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
    }

    @Override
    public void initData() {
        mainBottomBar.addItem(new BottomNavigationItem(R.drawable.icon_home, "主页").setActiveColorResource(R.color.md_deep_orange_400))
                .addItem(new BottomNavigationItem(R.drawable.icon_category, "分类").setActiveColorResource(R.color.md_deep_orange_400))
                .addItem(new BottomNavigationItem(R.drawable.icon_search, "查找").setActiveColorResource(R.color.md_deep_orange_400))
                .addItem(new BottomNavigationItem(R.drawable.icon_me, "我的").setActiveColorResource(R.color.md_deep_orange_400))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    @Override
    public void initEvens() {
        mainBottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                baseToast("当前点击的是第 " + (position + 1) + " 个标签");
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    public void processClick(View v) {

    }
}
