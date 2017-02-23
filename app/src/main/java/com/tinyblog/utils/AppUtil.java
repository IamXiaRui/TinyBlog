package com.tinyblog.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * @author xiarui
 * @date 2017/2/23 19:08
 * @desc 单独抽取的工具类
 * @remark 1.0
 */

public class AppUtil {

    /**
     * 将 Html 文本转成普通文本
     * @param html Html文本
     * @return 普通文本
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
