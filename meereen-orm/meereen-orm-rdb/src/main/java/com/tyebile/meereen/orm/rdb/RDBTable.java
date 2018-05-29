package com.tyebile.meereen.orm.rdb;

import com.tyebile.meereen.orm.core.Table;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;

public interface RDBTable<T> extends Table<T> {
    RDBTableMetaData getMeta();

    RDBQuery<T> createQuery();

}
