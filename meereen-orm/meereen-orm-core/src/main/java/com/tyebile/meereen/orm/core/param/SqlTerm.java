package com.tyebile.meereen.orm.core.param;

/**
 * 直接拼接sql的方式
 *
 * @author zhouhao
 * @since 3.0
 */
public class SqlTerm extends Term {

    private String sql;

    private Object param;

    public SqlTerm() {
    }

    public SqlTerm(String sql) {
        this(sql, null);
    }

    public SqlTerm(String sql, Object param) {
        this.sql = sql;
        this.param = param;
        setColumn(sql);
        if (param == null) {
            param = sql;
        }
        setValue(param);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        setColumn(sql);
        this.sql = sql;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        setValue(param);
        this.param = param;
    }

    @Override
    public SqlTerm clone() {
        SqlTerm term = new SqlTerm();
        term.setColumn(getColumn());
        term.setValue(getValue());
        term.setTermType(getTermType());
        term.setType(getType());
        term.setSql(getSql());
        term.setParam(getParam());
        getTerms().forEach(t -> term.addTerm(t.clone()));
        return term;
    }
}
