package com.tyebile.meereen.orm.rdb;

import com.tyebile.meereen.orm.core.Query;
import com.tyebile.meereen.orm.core.TriggerSkipSupport;
import com.tyebile.meereen.orm.core.param.QueryParam;

import java.sql.SQLException;
import java.util.List;

public interface RDBQuery<T> extends Query<T>, TriggerSkipSupport<Query<T>> {
    RDBQuery<T> setParam(QueryParam param);

    RDBQuery<T> select(String... fields);

    RDBQuery<T> selectExcludes(String... fields);

    RDBQuery<T> orderByAsc(String column);

    RDBQuery<T> orderByDesc(String column);

    RDBQuery<T> noPaging();

    RDBQuery<T> forUpdate();

    List<T> list() throws SQLException;

    List<T> list(int pageIndex, int pageSize) throws SQLException;

    T single() throws SQLException;

    int total() throws SQLException;

}
