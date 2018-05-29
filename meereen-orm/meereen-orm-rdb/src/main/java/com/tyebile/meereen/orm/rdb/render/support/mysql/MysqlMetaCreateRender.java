package com.tyebile.meereen.orm.rdb.render.support.mysql;

import com.tyebile.meereen.orm.rdb.executor.SQL;
import com.tyebile.meereen.orm.rdb.meta.RDBColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;
import com.tyebile.meereen.orm.rdb.render.SqlAppender;
import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.support.simple.SimpleSQL;
import com.tyebile.meereen.utils.StringUtils;

import java.util.Set;

public class MysqlMetaCreateRender implements SqlRender {

    private String engine = "InnoDB";

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public MysqlMetaCreateRender() {
    }

    public MysqlMetaCreateRender(String engine) {
        this.engine = engine;
    }

    @Override
    public SQL render(RDBTableMetaData metaData, Object param) {
        SqlAppender appender = new SqlAppender();
        Set<RDBColumnMetaData> columns = metaData.getColumns();
        if (columns.isEmpty()) {
            throw new UnsupportedOperationException("未指定任何字段");
        }
        appender.add("\nCREATE TABLE ", metaData.getName(), "(");
        columns.forEach(column -> {
            appender.add("\n\t`", column.getName(), "` ");
            if (column.getColumnDefinition() != null) {
                appender.add(column.getColumnDefinition());
            } else {
                appender.add(column.getDataType());
                if (column.isNotNull()) {
                    appender.add(" not null");
                }
                if (column.isPrimaryKey()) {
                    appender.add(" primary key");
                }
                //注释
                if (!StringUtils.isNullOrEmpty(column.getComment())) {
                    appender.add(String.format(" comment '%s'", column.getComment()));
                }
            }
            appender.add(",");
        });
        appender.removeLast();
        appender.add("\n)ENGINE = " + getEngine() + " CHARACTER SET utf8 ");
        if (metaData.getComment() != null) {
            appender.add("COMMENT=", "'", metaData.getComment(), "'");
        }
        return new SimpleSQL(appender.toString(), param);
    }
}
