package com.tyebile.meereen.orm.rdb.render.dialect;

import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.support.oracle.OracleMetaAlterRender;
import com.tyebile.meereen.orm.rdb.render.support.oracle.OracleMetaCreateRender;

public class OracleRDBDatabaseMetaData extends AbstractRDBDatabaseMetaData {
    private static final String DEFAULT_NAME = "oracle";

    private String name;

    public OracleRDBDatabaseMetaData() {
        super(Dialect.ORACLE);
        name = DEFAULT_NAME;
        init();
    }

    @Override
    public void init() {
        super.init();
        renderMap.put(SqlRender.TYPE.META_CREATE, new OracleMetaCreateRender());
        renderMap.put(SqlRender.TYPE.META_ALTER, new OracleMetaAlterRender(this));
    }

    @Override
    public String getName() {
        return name;
    }
}
