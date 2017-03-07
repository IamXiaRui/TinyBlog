package com.tinyblog.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author xiarui
 * @date 2017/3/7 17:44
 * @description App的数据库
 * @remark
 */

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
    //数据库名称
    public static final String NAME = "AppDataBase";
    //数据库版本号
    public static final int VERSION = 1;
}
