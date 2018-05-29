package com.tyebile.meereen.orm.rdb.render;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public interface Sql {
    String getSql();

    static Sql build(String sql) {
        return () -> sql;
    }
}
