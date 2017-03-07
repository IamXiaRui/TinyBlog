package com.tinyblog.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author xiarui
 * @date 2017/3/7 17:56
 * @description 收藏数据表
 * @remark
 */
@Table(database = AppDataBase.class)
public class CollectModel extends BaseModel {
    //自增ID
    @PrimaryKey(autoincrement = true)
    public Long id;
    @Column
    public String postId;
    @Column
    public String title;
    @Column
    public String image;
}
