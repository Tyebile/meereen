package com.tyebile.meereen.orm.rdb.render;

import java.util.LinkedList;

/**
 * Created by 浩 on 2015-11-07 0007.
 */
public class SqlAppender extends LinkedList<String> {
    public SqlAppender() {
    }

    public SqlAppender(String sql) {
        add(sql);
    }

    public SqlAppender add(Object... str) {
        for (Object s : str) {
            this.add(String.valueOf(s));
        }
        return this;
    }

    public SqlAppender addEdSpc(Object... str) {
        for (Object s : str) {
            this.add(String.valueOf(s));
        }
        this.add(" ");
        return this;
    }

    /**
     * 接入sql语句，并自动加入空格
     *
     * @param str
     * @return
     */
    public SqlAppender addSpc(Object... str) {
        for (Object s : str) {
            this.add(s);
            this.add(" ");
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.forEach(builder::append);
        return builder.toString();
    }
}
