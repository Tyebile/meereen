package com.tyebile.meereen.orm.core;

import com.tyebile.meereen.orm.core.meta.TableMetaData;

public interface ValidatorFactory {
    Validator createValidator(TableMetaData tableMetaData);
}
