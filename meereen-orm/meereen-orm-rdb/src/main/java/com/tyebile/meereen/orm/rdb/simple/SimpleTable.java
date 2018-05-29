package com.tyebile.meereen.orm.rdb.simple;

import com.tyebile.meereen.orm.core.Delete;
import com.tyebile.meereen.orm.core.Insert;
import com.tyebile.meereen.orm.core.ObjectWrapper;
import com.tyebile.meereen.orm.core.Update;
import com.tyebile.meereen.orm.rdb.RDBQuery;
import com.tyebile.meereen.orm.rdb.RDBTable;
import com.tyebile.meereen.orm.rdb.executor.SqlExecutor;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;

/**
 * Created by zhouhao on 16-6-4.
 */
class SimpleTable<T> implements RDBTable<T> {
    private RDBTableMetaData metaData;

    private SqlExecutor sqlExecutor;

    private ObjectWrapper objectWrapper;

    private SimpleDatabase database;

    public SimpleTable(RDBTableMetaData metaData, SimpleDatabase database, SqlExecutor sqlExecutor, ObjectWrapper objectWrapper) {
        this.metaData = metaData;
        this.sqlExecutor = sqlExecutor;
        this.objectWrapper = objectWrapper;
        this.database = database;
    }

    @Override
    public RDBTableMetaData getMeta() {
        return metaData;
    }

    @Override
    public RDBQuery createQuery() {
        return new SimpleQuery<>(this, sqlExecutor, objectWrapper);
    }

    @Override
    public Update createUpdate() {
        return new SimpleUpdate<>(this, sqlExecutor);
    }

    @Override
    public Delete createDelete() {
        return new SimpleDelete(this, sqlExecutor);
    }

    @Override
    public Insert<T> createInsert() {
        return new SimpleInsert<>(this, sqlExecutor);
    }

    public SimpleDatabase getDatabase() {
        return database;
    }
}
