package com.tyebile.meereen.orm.core;

import com.tyebile.meereen.orm.core.param.TermType;

import java.util.Arrays;
import java.util.Collection;

public interface ConditionalFromBean<T extends ConditionalFromBean> extends LogicalOperation<T>, TermTypeConditionalFromBeanSupport {

    NestConditionalFromBean<T> nest();

    NestConditionalFromBean<T> nest(String column);

    NestConditionalFromBean<T> orNest();

    NestConditionalFromBean<T> orNest(String column);

    T and();

    T or();

    T and(String column, String termType);

    T or(String column, String termType);

    TermTypeConditionalSupport.Accepter<T, Object> getAccepter();

    default T where(String column) {
        return and(column, TermType.eq);
    }

    default T where() {
        return (T) this;
    }

    default T and(String column) {
        return and(column, TermType.eq);
    }

    default T or(String column) {
        return or(column, TermType.eq);
    }

    default T like(String column) {
        return accept(column, TermType.like);
    }

    default T like$(String column) {
        Object value = getValue(column);
        if (value == null)
            return like(column);
        return accept(column, TermType.like, String.valueOf(value).concat("%"));
    }

    default T $like(String column) {
        Object value = getValue(column);
        if (value == null)
            return like(column);
        return accept(column, TermType.like, "%".concat(String.valueOf(value)));
    }

    default T $like$(String column) {
        Object value = getValue(column);
        if (value == null)
            return like(column);
        return accept(column, TermType.like, "%".concat(String.valueOf(value)).concat("%"));
    }

    default T notLike(String column) {
        return accept(column, TermType.nlike);
    }

    default T gt(String column) {
        return accept(column, TermType.gt);
    }

    default T lt(String column) {
        return accept(column, TermType.lt);
    }

    default T gte(String column) {
        return accept(column, TermType.gte);
    }

    default T lte(String column) {
        return accept(column, TermType.lte);
    }

    default T in(String column) {
        return accept(column, TermType.in);
    }

    default T in(String column, Object... values) {
        return accept(column, TermType.in, values);
    }

    default T in(String column, Collection values) {
        return accept(column, TermType.in, values);
    }

    default T notIn(String column, Object... values) {
        return accept(column, TermType.nin, values);
    }

    default T notIn(String column, Collection values) {
        return accept(column, TermType.nin, values);
    }

    T sql(String sql, Object... params);

    default T notIn(String column) {
        return accept(column, TermType.nin);
    }

    default T isEmpty(String column) {
        return accept(column, TermType.empty, 1);
    }

    default T notEmpty(String column) {
        return accept(column, TermType.nempty, 1);
    }

    default T isNull(String column) {
        return accept(column, TermType.isnull, 1);
    }

    default T notNull(String column) {
        return accept(column, TermType.notnull, 1);
    }

    default T not(String column) {
        return accept(column, TermType.not);
    }

    default T between(String column, Object between, Object and) {
        return accept(column, TermType.btw, Arrays.asList(between, and));
    }

    default T notBetween(String column, Object between, Object and) {
        return accept(column, TermType.nbtw, Arrays.asList(between, and));
    }

    default T accept(String column, String termType) {
        return accept(column, termType, getValue(column));
    }

    default T accept(String column, String termType, Object value) {
        return getAccepter().accept(column, termType, value);
    }

}
