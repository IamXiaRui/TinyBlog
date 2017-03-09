package com.tinyblog.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author xiarui
 * @date 2017/3/9 17:26
 * @description 新建文章数据表
 * @remark
 */
@Table(database = AppDataBase.class)
public class NewPostModel extends BaseModel {
    //自增ID
    @PrimaryKey(autoincrement = true)
    public Long id;
    @Column
    public String title;
    @Column
    public String content;
    @Column
    public String time;
}
