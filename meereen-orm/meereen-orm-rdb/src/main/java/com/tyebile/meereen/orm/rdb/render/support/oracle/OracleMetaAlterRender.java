package com.tyebile.meereen.orm.rdb.render.support.oracle;

import com.tyebile.meereen.orm.rdb.meta.RDBColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.RDBDatabaseMetaData;
import com.tyebile.meereen.orm.rdb.render.SqlAppender;
import com.tyebile.meereen.orm.rdb.render.support.simple.AbstractMetaAlterRender;

import java.util.ArrayList;
import java.util.List;


/**
 * oracle数据库表结构修改sql渲染器
 *
 * @author zhouhao
 * @see 1.0
 */
public class OracleMetaAlterRender extends AbstractMetaAlterRender {

    public OracleMetaAlterRender(RDBDatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }

    @Override
    protected List<SqlAppender> buildAdd(RDBColumnMetaData column) {
        SqlAppender alter = new SqlAppender();
        SqlAppender comments = new SqlAppender();
        List<SqlAppender> all = new ArrayList<>();
        alter.add("ALTER TABLE ",
                column.getTableMetaData().getName(),
                " ADD ",
                column.getName(),
                " ");
        if (column.getColumnDefinition() != null) {
            alter.add(column.getColumnDefinition());
        } else {
            alter.add(column.getDataType());
            if (column.isNotNull() || column.isPrimaryKey()) {
                alter.add(" NOT NULL");
            }
            if (column.getComment() != null) {
                comments.add(String.format("COMMENT ON COLUMN %s.\"%s\" is '%s'", column.getTableMetaData().getName(), column.getName().toUpperCase(), column.getComment()));
            }
        }
        all.add(alter);
        all.add(comments);
        return all;
    }

    @Override
    protected List<SqlAppender> buildAlter(RDBColumnMetaData column) {
        SqlAppender alter = new SqlAppender();
        SqlAppender comments = new SqlAppender();
        List<SqlAppender> all = new ArrayList<>();
        alter.add("ALTER TABLE ",
                column.getTableMetaData().getName(),
                " MODIFY ",
                column.getName(),
                " ");
        if (column.getColumnDefinition() != null) {
            alter.add(column.getColumnDefinition());
        } else {
            alter.add(column.getDataType());
            if (column.isNotNull() || column.isPrimaryKey()) {
                alter.add(" NOT NULL");
            }
            if (column.getComment() != null) {
                comments.add(String.format("COMMENT ON COLUMN %s.\"%s\" is '%s'", column.getTableMetaData().getName(), column.getName().toUpperCase(), column.getComment()));
            }
        }
        all.add(alter);
        all.add(comments);
        return all;
    }

//    @Override
//    public SQL render(RDBTableMetaData metaData, Boolean executeRemove) {
//        RDBTableMetaData old = databaseMetaData.getTableMetaData(metaData.getName());
//        if (old == null) throw new UnsupportedOperationException("旧表不存在!");
//        List<RDBColumnMetaData> changedField = new ArrayList<>();
//        List<RDBColumnMetaData> addedField = new ArrayList<>();
//        List<RDBColumnMetaData> deletedField = new ArrayList<>();
//
//        if (executeRemove)
//            old.getColumns().forEach(oldField -> {
//                RDBColumnMetaData newMeta = metaData.findColumn(oldField.getName());
//                if (newMeta == null) {
//                    newMeta = metaData.getColumns().stream()
//                            .filter(columnMetaData -> oldField.getName().equals(columnMetaData.getProperty("old-name").getValue()))
//                            .findFirst().orElse(null);
//                }
//                if (newMeta == null || !newMeta.getName().equals(oldField.getName())) {
//                    //删除的字段
//                    deletedField.add(oldField);
//                }
//            });
//        metaData.getColumns().forEach(newField -> {
//            String oldName = newField.getProperty("old-name").getValue();
//            if (oldName == null) oldName = newField.getName();
//            RDBColumnMetaData oldField = old.findColumn(oldName);
//            if (oldField == null) {
//                //增加的字段
//                addedField.add(newField);
//            } else {
//                if (!newField.getName().equals(oldField.getName()) ||
//                        !newField.getDataType().equals(oldField.getDataType())
//                        || !newField.getComment().equals(oldField.getComment())
//                        || oldField.isNotNull() != newField.isNotNull()) {
//                    changedField.add(newField);
//                }
//            }
//        });
//        LinkedList<BindSQL> bind = new LinkedList<>();
//        List<String> comments = new ArrayList<>();
//        String newTableComment = metaData.getComment();
//        String oldTableComment = old.getComment();
//        if (newTableComment == null) newTableComment = "";
//        if (oldTableComment == null) oldTableComment = "";
//        if (!newTableComment.equals(oldTableComment)) {
//            comments.add(String.format("COMMENT ON TABLE %s IS '%s'", metaData.getName(), metaData.getComment()));
//        }
//        if (addedField.isEmpty() && changedField.isEmpty() && deletedField.isEmpty() && comments.isEmpty()) {
//            return new EmptySQL();
//        }
//        //删除列
//        deletedField.forEach(column -> {
//            String dropSql = String.format("ALTER TABLE %s DROP COLUMN \"%s\"", metaData.getName(), column.getName().toUpperCase());
//            SimpleSQL simpleSQL = new SimpleSQL(dropSql, column);
//            BindSQL bindSQL = new BindSQL();
//            bindSQL.setSql(simpleSQL);
//            bindSQL.setToField(column.getName());
//            bind.add(bindSQL);
//        });
//        //增加列
//        addedField.forEach(column -> {
//            SqlAppender append = new SqlAppender();
//            append.add("ALTER TABLE ", metaData.getName(), " ADD \"", column.getName().toUpperCase(), "\" ", column.getDataType());
//            if (column.isNotNull()) {
//                append.add(" NOT NULL");
//            }
//            if (StringUtils.isNullOrEmpty(column.getComment())) {
//                comments.add(String.format("COMMENT ON COLUMN %s.\"%s\" is '%s'", metaData.getName(), column.getName().toUpperCase(), column.getAlias()));
//            } else {
//                comments.add(String.format("COMMENT ON COLUMN %s.\"%s\" is '%s'", metaData.getName(), column.getName().toUpperCase(), column.getComment()));
//            }
//            SimpleSQL simpleSQL = new SimpleSQL(append.toString(), column);
//            BindSQL bindSQL = new BindSQL();
//            bindSQL.setSql(simpleSQL);
//            bindSQL.setToField(column.getName());
//            bind.add(bindSQL);
//        });
//        //修改列
//        changedField.forEach(column -> {
//            String oldName = column.getProperty("old-name").getValue();
//            if (oldName == null) oldName = column.getName();
//            RDBColumnMetaData oldColumn = old.findColumn(oldName);
//            if (!oldName.equals(column.getName())) {
//                SqlAppender renameSql = new SqlAppender();
//                renameSql.add("ALTER TABLE ", metaData.getName(), " RENAME COLUMN \"", oldName.toUpperCase(), "\" TO \"", column.getName().toUpperCase(), "\"");
//                BindSQL bindSQL = new BindSQL();
//                bindSQL.setSql(new SimpleSQL(renameSql.toString()));
//                bind.add(bindSQL);
//                metaData.renameColumn(oldName, column.getName());
//            }
//            if (!oldColumn.getDataType().equals(column.getDataType())
//                    || oldColumn.isNotNull() != column.isNotNull()) {
//                SqlAppender append = new SqlAppender();
//                append.add("ALTER TABLE ", metaData.getName(), " MODIFY \"", column.getName().toUpperCase(), "\" ", column.getDataType());
//                if (oldColumn.isNotNull() != column.isNotNull()) {
//                    if (column.isNotNull()) {
//                        append.add(" NOT NULL");
//                    } else {
//                        append.add(" NULL");
//                    }
//                }
//                SimpleSQL simpleSQL = new SimpleSQL(append.toString(), column);
//                BindSQL bindSQL = new BindSQL();
//                bindSQL.setSql(simpleSQL);
//                bindSQL.setToField(column.getName());
//                bind.add(bindSQL);
//            }
//            String nc = column.getComment();
//            String oc = oldColumn.getComment();
//            if (nc == null) nc = "";
//            if (oc == null) oc = "";
//            if (nc.equals(oc)) return;
//
//            if (!StringUtils.isNullOrEmpty(nc)) {
//                comments.add(String.format("comment on column %s.\"%s\" is '%s'", metaData.getName(), column.getName().toUpperCase(), nc));
//            }
//        });
//
//        LinkedList<BindSQL> commentSql = new LinkedList<>(comments.stream().map(s -> {
//            BindSQL binSql = new BindSQL();
//            binSql.setSql(new SimpleSQL(s, s));
//            return binSql;
//        }).collect(Collectors.toList()));
//
//        SQL sql = null;
//        if (bind.isEmpty()) {
//            return new EmptySQL();
//        }
//        bind.addAll(commentSql);
//        if (!bind.isEmpty()) {
//            sql = bind.get(0).getSql();
//            bind.removeFirst();
//        }
//        if (sql != null && !bind.isEmpty())
//            ((SimpleSQL) sql).setBindSQLs(bind);
//        return sql;
//    }
}
