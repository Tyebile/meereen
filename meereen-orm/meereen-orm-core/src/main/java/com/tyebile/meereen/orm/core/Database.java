package com.tyebile.meereen.orm.core;


import com.tyebile.meereen.orm.core.meta.DatabaseMetaData;


/**
 * 数据库操作接口
 * Created by zhouhao on 16-6-4.
 */
public interface Database {

    /**
     * 获取数据库定义对象
     *
     * @return 数据库定义对象
     */
    <T extends DatabaseMetaData> T getMeta();

}
