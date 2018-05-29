package com.tyebile.meereen.orm.core.meta;


import com.tyebile.meereen.orm.core.DefaultValue;
import com.tyebile.meereen.orm.core.OptionConverter;
import com.tyebile.meereen.orm.core.PropertyWrapper;
import com.tyebile.meereen.orm.core.ValueConverter;

import java.io.Serializable;
import java.util.Set;

public interface ColumnMetaData extends Serializable, Cloneable {
    String getName();

    String getAlias();

    String getComment();

    Class getJavaType();

    <T extends TableMetaData> T getTableMetaData();

    ValueConverter getValueConverter();

    OptionConverter getOptionConverter();

    DefaultValue getDefaultValue();

    Set<String> getValidator();

    PropertyWrapper getProperty(String property);

    PropertyWrapper getProperty(String property, Object defaultValue);

    PropertyWrapper setProperty(String property, Object value);

    <T extends ColumnMetaData> T clone();
}
