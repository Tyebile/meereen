package com.tyebile.meereen.orm.rdb.meta.converter;


import com.tyebile.meereen.orm.core.ValueConverter;

/**
 * Created by zhouhao on 16-6-4.
 */
public class DefaultValueConverter implements ValueConverter {
    @Override
    public Object getData(Object value) {
        return value;
    }

    @Override
    public Object getValue(Object data) {
        return data;
    }
}
