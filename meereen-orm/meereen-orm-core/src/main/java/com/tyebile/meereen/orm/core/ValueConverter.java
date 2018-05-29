package com.tyebile.meereen.orm.core;

public interface ValueConverter {
    Object getData(Object value);

    Object getValue(Object data);
}
