package com.tyebile.meereen.orm.rdb.render.dialect;

import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.support.mysql.MysqlDeleteSqlRender;
import com.tyebile.meereen.orm.rdb.render.support.mysql.MysqlMetaAlterRender;
import com.tyebile.meereen.orm.rdb.render.support.mysql.MysqlMetaCreateRender;

public class MysqlRDBDatabaseMetaData extends AbstractRDBDatabaseMetaData {
    private static final String DEFAULT_NAME = "mysql";

    private String name;

    private String engine = "InnoDB";

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public MysqlRDBDatabaseMetaData() {
        this("InnoDB");
    }

    public MysqlRDBDatabaseMetaData(String engine) {
        super(Dialect.MYSQL);
        name = DEFAULT_NAME;
        this.engine = engine;
        init();
    }

    @Override
    public void init() {
        super.init();
        renderMap.put(SqlRender.TYPE.META_CREATE, new MysqlMetaCreateRender(getEngine()));
        renderMap.put(SqlRender.TYPE.DELETE, new MysqlDeleteSqlRender(getDialect()));
        renderMap.put(SqlRender.TYPE.META_ALTER, new MysqlMetaAlterRender(this));
    }


    @Override
    public String getName() {
        return name;
    }


}
