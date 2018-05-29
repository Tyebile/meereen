package com.tyebile.meereen.orm.core;

import com.tyebile.meereen.orm.core.meta.TableMetaData;

public interface Table<T> {
    <M extends TableMetaData> M getMeta();

    <Q extends Query<T>> Q createQuery();

    <U extends Update<T>> U createUpdate();

    <I extends Insert<T>> I createInsert();

    <D extends Delete> D createDelete();
}
