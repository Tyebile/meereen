package com.tyebile.meereen.orm.rdb.meta.builder;

import com.tyebile.meereen.orm.rdb.meta.RDBColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;

import java.sql.SQLException;
import java.util.Set;
import java.util.function.Consumer;

public interface TableBuilder {

    TableBuilder addColumn(Set<RDBColumnMetaData> columns);

    TableBuilder custom(Consumer<RDBTableMetaData> consumer);

    ColumnBuilder addColumn();

    ColumnBuilder addOrAlterColumn(String name);

    TableBuilder removeColumn(String name);

    TableBuilder comment(String comment);

    TableBuilder property(String propertyName, Object value);

    TableBuilder alias(String name);

    void commit() throws SQLException;
}
