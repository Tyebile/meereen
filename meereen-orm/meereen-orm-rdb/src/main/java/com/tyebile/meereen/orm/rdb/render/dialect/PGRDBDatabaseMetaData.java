package com.tyebile.meereen.orm.rdb.render.dialect;

import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.support.oracle.OracleMetaAlterRender;
import com.tyebile.meereen.orm.rdb.render.support.oracle.OracleMetaCreateRender;

/**
 * @author zhouhao
 * @since 3.0
 */
public class PGRDBDatabaseMetaData extends AbstractRDBDatabaseMetaData {
    private static final String DEFAULT_NAME = "postgres";

    private String name;

    public PGRDBDatabaseMetaData() {
        super(Dialect.POSTGRES);
        name = DEFAULT_NAME;
        init();
    }

    @Override
    public void init() {
        super.init();
        putRenderer(SqlRender.TYPE.META_CREATE, new OracleMetaCreateRender());
        putRenderer(SqlRender.TYPE.META_ALTER, new OracleMetaAlterRender(this));
    }

    @Override
    public String getName() {
        return name;
    }
}
