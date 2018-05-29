package com.tyebile.meereen.orm.rdb.render.support.h2;

import com.tyebile.meereen.orm.rdb.meta.RDBDatabaseMetaData;
import com.tyebile.meereen.orm.rdb.render.support.simple.AbstractMetaAlterRender;

public class H2MetaAlterRender extends AbstractMetaAlterRender {
    public H2MetaAlterRender(RDBDatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
}
