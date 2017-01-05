package com.tinyblog.sys;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.tinyblog.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiarui
 * @date 2017/1/4 19:41
 * @description
 * @remark
 */

public class App extends Application {
    public static List<?> testBannerImages = new ArrayList<>();
    public static List<String> testBannerTitles = new ArrayList<>();
    public static int H;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        H = getScreenH(this);
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        testBannerImages = new ArrayList(list);
        List list1 = Arrays.asList(tips);
        testBannerTitles = new ArrayList(list1);
    }

    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
