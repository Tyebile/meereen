package com.tyebile.meereen.orm.rdb.meta.converter;

import com.tyebile.meereen.orm.core.ValueConverter;
import com.tyebile.meereen.utils.ClassUtils;
import com.tyebile.meereen.utils.StringUtils;
import com.tyebile.meereen.utils.time.DateFormatter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

public class NumberValueConverter implements ValueConverter {

    private Function<Number, Object> converter;

    public NumberValueConverter(Function<Number, Object> converter) {
        this.converter = converter;
    }

    public NumberValueConverter(Class javaType) {
        if (javaType == int.class || javaType == Integer.class) {
            converter = Number::intValue;
        } else if (javaType == double.class || javaType == Double.class) {
            converter = Number::doubleValue;
        } else if (javaType == long.class || javaType == Long.class) {
            converter = Number::longValue;
        } else if (javaType == byte.class || javaType == Byte.class) {
            converter = Number::byteValue;
        } else if (javaType == boolean.class || javaType == Boolean.class) {
            converter = num -> num.byteValue() != 0;
        } else if (javaType == char.class || javaType == Character.class) {
            converter = num -> (char) num.intValue();
        } else if (ClassUtils.instanceOf(javaType, Date.class)) {
            converter = num -> {
                try {
                    Date date = (Date) javaType.newInstance();
                    date.setTime(num.longValue());
                    return date;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        } else {
            converter = num -> num;
        }
    }

    @Override
    public Object getData(Object value) {
        if (StringUtils.isNullOrEmpty(value)) return null;
        if (value instanceof Number) return value;
        if (value instanceof Date) {
            value = ((Date) value).getTime();
        } else if (!StringUtils.isNumber(value)) {
            Date date = DateFormatter.fromString(String.valueOf(value));
            if (null != date) value = date.getTime();
        }
        if (StringUtils.isNumber(value)) {
            return converter.apply(new BigDecimal(String.valueOf(value)));
        }
        if (Boolean.TRUE.equals(value)) {
            return 1;
        }
        if (Boolean.FALSE.equals(value)) {
            return 0;
        }
        throw new UnsupportedOperationException("值" + value + "无法转换为数字");
    }


    @Override
    public Object getValue(Object data) {
        if (data instanceof String) {
            if (StringUtils.isNumber(data)) {
                data = new BigDecimal(((String) data));
            }
        } else if (!StringUtils.isNumber(data)) {
            Date date = DateFormatter.fromString(String.valueOf(data));
            if (null != date) data = date.getTime();
        }
        if (data instanceof Number) {
            return converter.apply(((Number) data));
        }
        return data;
    }
}
