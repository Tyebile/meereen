package com.tyebile.meereen.orm.core;


import com.tyebile.meereen.orm.core.meta.TableMetaData;

public interface ObjectWrapperFactory {
    <T> ObjectWrapper<T> createObjectWrapper(TableMetaData metaData);
}
