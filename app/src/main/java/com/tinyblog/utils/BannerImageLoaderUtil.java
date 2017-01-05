package com.tinyblog.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author xiarui
 * @date 2017/1/4 19:29
 * @desc 动态页 Banner 图片加载器
 * @remark 1.0
 */

public class BannerImageLoaderUtil extends ImageLoader {

    /**
     * 显示图片
     *
     * @param context   上下文对象
     * @param path      图片路径
     * @param imageView 图片
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }
}
