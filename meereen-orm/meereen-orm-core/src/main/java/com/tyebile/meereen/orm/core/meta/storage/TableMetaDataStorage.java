package com.tyebile.meereen.orm.core.meta.storage;

import com.tyebile.meereen.orm.core.meta.TableMetaData;

import java.util.Set;

public interface TableMetaDataStorage {

    <T extends TableMetaData> Set<T> getAllTableMetaData();

    <T extends TableMetaData> T getTableMetaData(String nameOrAlias);

    <T extends TableMetaData> T removeTableMeta(String nameOrAlias);

    <T extends TableMetaData> T putTableMetaData(T table);

    void clear();

}
