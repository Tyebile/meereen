package com.tyebile.meereen.orm.rdb.render.support.oracle;

import com.tyebile.meereen.orm.rdb.executor.BindSQL;
import com.tyebile.meereen.orm.rdb.executor.SQL;
import com.tyebile.meereen.orm.rdb.meta.RDBColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;
import com.tyebile.meereen.orm.rdb.render.SqlAppender;
import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.support.simple.SimpleSQL;
import com.tyebile.meereen.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OracleMetaCreateRender implements SqlRender<Object> {
    @Override
    public SQL render(RDBTableMetaData metaData, Object param) {
        SqlAppender createBody = new SqlAppender();
        List<String> comments = new ArrayList<>();
        Set<RDBColumnMetaData> columns = metaData.getColumns();
        if (columns.isEmpty()) throw new UnsupportedOperationException("未指定任何字段");
        createBody.add("\nCREATE TABLE ", metaData.getName(), "(");
        columns.forEach(column -> {
            createBody.add("\n\t\"", column.getName().toUpperCase(), "\" ");

            if (column.getColumnDefinition() != null) {
                createBody.add(column.getColumnDefinition());

            } else {
                createBody.add(column.getDataType());
                if (column.isNotNull()
                        || column.isPrimaryKey()) {
                    createBody.add(" NOT NULL ");
                }
                if (column.isPrimaryKey())
                    createBody.add("PRIMARY KEY ");
                //注释
                if (!StringUtils.isNullOrEmpty(column.getComment())) {
                    comments.add(String.format("COMMENT ON COLUMN %s.\"%s\" IS '%s'", metaData.getName(), (column.getName().toUpperCase()), column.getComment()));
                }
            }
            createBody.add(",");
        });
        comments.add(String.format("COMMENT ON TABLE %s IS '%s'", metaData.getName(), metaData.getComment()));
        createBody.removeLast();
        createBody.add("\n)");
        SimpleSQL simpleSQL = new SimpleSQL(createBody.toString(), param);
        List<BindSQL> bindSQLs = comments.stream().map(s -> {
            BindSQL sql = new BindSQL();
            sql.setSql(new SimpleSQL(s, param));
            return sql;
        }).collect(Collectors.toList());
        simpleSQL.setBindSQLs(bindSQLs);
        return simpleSQL;
    }
}
