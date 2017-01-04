package com.tinyblog.fragment;

import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tinyblog.R;
import com.tinyblog.base.BaseFragment;

/**
 * @author xiarui
 * @date 2016/10/11 11:42
 * @desc 动态
 */
public class NewsFragment extends BaseFragment {

    //下拉刷新
    private SwipeRefreshLayout mNewsSRLayout;
    private ListView mHomeLView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        mNewsSRLayout = (SwipeRefreshLayout) findViewById(R.id.srl_news);
        mNewsSRLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mHomeLView = (ListView) findViewById(R.id.lv_news);
    }

    @Override
    public void initData() {
        mHomeLView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"1", "2", "3", "1", "2", "3", "1", "2", "3",
                "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3",
                "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3"}));
    }

    @Override
    public void initEvents() {

        mNewsSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(4200);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //停止刷新操作
                                mNewsSRLayout.setRefreshing(false);
                                showBaseToast("刷新完成！");
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
