package com.tinyblog.sys;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.blankj.utilcode.utils.Utils;
import com.tinyblog.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author xiarui
 * @date 2017/1/4 19:41
 * @desc Application 初始化类
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

        /*===== 配置 OkHttpUtils =====*/
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        /*===== 初始化 Utils 工具类 =====*/
        Utils.init(app);
    }

    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
