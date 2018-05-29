package com.tyebile.meereen.orm.rdb.meta;

import com.tyebile.meereen.orm.core.meta.AbstractDatabaseMetaData;
import com.tyebile.meereen.orm.core.meta.DatabaseMetaData;
import com.tyebile.meereen.orm.rdb.meta.parser.TableMetaParser;
import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.dialect.Dialect;

public abstract class RDBDatabaseMetaData extends AbstractDatabaseMetaData implements DatabaseMetaData {
    private TableMetaParser parser;

    public abstract Dialect getDialect();

    public abstract void init();

    public abstract <T> SqlRender<T> getRenderer(SqlRender.TYPE type);

    public abstract String getName();

    public void setParser(TableMetaParser parser) {
        this.parser = parser;
    }

    public TableMetaParser getParser() {
        return parser;
    }

    @Override
    public RDBTableMetaData getTableMetaData(String name) {
        return super.getTableMetaData(name);
    }

    public RDBTableMetaData removeTable(String name) {
        return tableMetaDataStorage.removeTableMeta(name);
    }

    public RDBTableMetaData putTable(RDBTableMetaData tableMetaData) {
        tableMetaData.setDatabaseMetaData(this);
        return tableMetaDataStorage.putTableMetaData(tableMetaData);
    }

    public void shutdown() {
        tableMetaDataStorage.clear();
    }

}
