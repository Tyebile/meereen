package com.tyebile.meereen.orm.rdb.render.support.mysql;

import com.tyebile.meereen.orm.rdb.meta.RDBDatabaseMetaData;
import com.tyebile.meereen.orm.rdb.render.support.simple.AbstractMetaAlterRender;


public class MysqlMetaAlterRender extends AbstractMetaAlterRender {

    public MysqlMetaAlterRender(RDBDatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }

}
