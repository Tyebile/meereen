package com.tyebile.meereen.orm.core.meta;

import com.tyebile.meereen.orm.core.ObjectWrapperFactory;
import com.tyebile.meereen.orm.core.ValidatorFactory;

public interface DatabaseMetaData {

    ObjectWrapperFactory getObjectWrapperFactory();

    ValidatorFactory getValidatorFactory();

    <T extends TableMetaData> T getTableMetaData(String name);

}
