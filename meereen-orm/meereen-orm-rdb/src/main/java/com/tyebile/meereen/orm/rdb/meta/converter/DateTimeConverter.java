package com.tyebile.meereen.orm.rdb.meta.converter;

import com.tyebile.meereen.orm.core.ValueConverter;
import com.tyebile.meereen.utils.DateTimeUtils;
import com.tyebile.meereen.utils.time.DateFormatter;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 日期转换器
 */
public class DateTimeConverter implements ValueConverter {

    private String format;

    private Class toType;

    public DateTimeConverter(String format, Class toType) {
        this.format = format;
        this.toType = toType;
    }

    @Override
    public Object getData(Object value) {
        if (value instanceof Date) return value;
        if (value instanceof String) {
            if (((String) value).contains(",")) {
                return Arrays.stream(((String) value).split(","))
                        .map(DateFormatter::fromString)
                        .collect(Collectors.toList());
            }
            return DateFormatter.fromString(((String) value));
        }
        return value;
    }

    @Override
    public Object getValue(Object data) {
        if (data instanceof Number) {
            data = new Date(((Number) data).longValue());
        }
        if (data instanceof Date) {
            if (toType == Date.class) return data;
            if (toType == String.class) {
                return DateTimeUtils.format(((Date) data), format);
            }
        }
        if (data instanceof String) {
            if (toType == Date.class) {
                if (((String) data).contains(",")) {
                    return Arrays.stream(((String) data).split(","))
                            .map(DateFormatter::fromString)
                            .collect(Collectors.toList());
                }
                data = DateFormatter.fromString(((String) data));
                if (data == null) data = DateTimeUtils.formatDateString(((String) data), format);
            }
        }
        return data;
    }
}
